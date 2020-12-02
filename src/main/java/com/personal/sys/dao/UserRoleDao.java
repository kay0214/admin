package com.personal.sys.dao;

import com.personal.common.base.BaseDao;
import com.personal.sys.domain.RoleDO;
import com.personal.sys.domain.UserRoleDO;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 用户与角色对应关系
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface UserRoleDao extends BaseDao<UserRoleDO> {

	List<Integer> listRoleId(Serializable userId);

	int removeByUserId(Serializable userId);

	int batchSave(List<UserRoleDO> list);

	int batchRemoveByUserId(Long[] ids);

    List<RoleDO> findListByUserId(Serializable id);
}
