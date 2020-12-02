package com.personal.sys.service;

import com.personal.common.base.CoreService;
import com.personal.common.domain.Tree;
import com.personal.sys.domain.DeptDO;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface DeptService extends CoreService<DeptDO> {
    
	Tree<DeptDO> getTree();
	
	boolean checkDeptHasUser(Integer deptId);
}
