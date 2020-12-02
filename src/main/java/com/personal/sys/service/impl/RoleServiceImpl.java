package com.personal.sys.service.impl;

import com.personal.common.base.CoreServiceImpl;
import com.personal.sys.dao.RoleDao;
import com.personal.sys.dao.RoleMenuDao;
import com.personal.sys.dao.UserDao;
import com.personal.sys.dao.UserRoleDao;
import com.personal.sys.domain.RoleDO;
import com.personal.sys.domain.RoleMenuDO;
import com.personal.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class RoleServiceImpl extends CoreServiceImpl<RoleDao, RoleDO> implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleMenuDao roleMenuMapper;
    @Autowired
    UserDao userMapper;
    @Autowired
    UserRoleDao userRoleMapper;

    @Cacheable(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Override
    public List<RoleDO> findAll() {
        List<RoleDO> roles = list(null);
        return roles;
    }

    @Override
    public List<RoleDO> findListStatusByUserId(Serializable userId) {
        List<Integer> rolesIds = userRoleMapper.listRoleId(userId);
        List<RoleDO> roles = list(null);
        for (RoleDO roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Integer roleId : rolesIds) {
                if (Objects.equals(roleDO.getId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public List<RoleDO> findListByUserId(Serializable id) {
        return userRoleMapper.findListByUserId(id);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Transactional
    @Override
    public boolean save(RoleDO role) {
        int count = baseMapper.insert(role);
        List<Integer> menuIds = role.getMenuIds();
        Integer roleId = role.getId();
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Integer menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuMapper.removeByRoleId(roleId);
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return retBool(count);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Transactional
    @Override
    public boolean removeById(Serializable id) {
        int count = baseMapper.deleteById(id);
        roleMenuMapper.removeByRoleId(id);
        return retBool(count);
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = ROLE_ALL_KEY)
    @Override
    public boolean updateById(RoleDO role) {
        int r = baseMapper.updateById(role);
        List<Integer> menuIds = role.getMenuIds();
        Integer roleId = role.getId();
        roleMenuMapper.removeByRoleId(roleId);
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Integer menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            roleMenuMapper.batchSave(rms);
        }
        return retBool(r);
    }

}
