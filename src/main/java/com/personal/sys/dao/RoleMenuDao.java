package com.personal.sys.dao;

import com.personal.common.base.BaseDao;
import com.personal.sys.domain.RoleMenuDO;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 角色与菜单对应关系
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface RoleMenuDao extends BaseDao<RoleMenuDO> {
	
	List<Integer> listMenuIdByRoleId(Serializable roleId);
	
	int removeByRoleId(Serializable roleId);
	
	int batchSave(List<RoleMenuDO> list);
}
