package com.personal.sys.dao;

import com.personal.common.base.BaseDao;
import com.personal.sys.domain.DeptDO;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface DeptDao extends BaseDao<DeptDO> {

    Integer[] listParentDept();
	
	int getDeptUserNumber(Integer deptId);
}
