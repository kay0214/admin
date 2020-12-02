package com.personal.sys.dao;

import com.personal.common.base.BaseDao;
import com.personal.sys.domain.MenuDO;

import java.util.List;

/**
 * <pre>
 * 菜单管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface MenuDao extends BaseDao<MenuDO> {
	
	List<MenuDO> listMenuByUserId(Integer id);
	
	List<String> listUserPerms(Integer id);
}
