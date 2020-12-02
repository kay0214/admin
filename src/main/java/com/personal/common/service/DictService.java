package com.personal.common.service;

import com.personal.common.base.CoreService;
import com.personal.common.domain.DictDO;
import com.personal.sys.domain.UserDO;

import java.util.List;

/**
 * 数据字典
 */
public interface DictService extends CoreService<DictDO> {
    
    List<DictDO> listType();

    String getName(String type, String value);

    /**
     * 获取爱好列表
     */
    List<DictDO> getHobbyList(UserDO userDO);

    /**
     * 获取性别列表
     */
    List<DictDO> getSexList();
}
