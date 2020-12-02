package com.personal.common.dao;

import com.personal.common.base.BaseDao;
import com.personal.common.domain.DictDO;

import java.util.List;

/**
 * 字典表
 */
public interface DictDao extends BaseDao<DictDO>{
    List<DictDO> listType();
}
