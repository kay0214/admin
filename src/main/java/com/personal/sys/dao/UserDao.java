package com.personal.sys.dao;

import com.personal.common.base.BaseDao;
import com.personal.sys.domain.UserDO;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface UserDao extends BaseDao<UserDO> {

    Integer[] listAllDept();

}
