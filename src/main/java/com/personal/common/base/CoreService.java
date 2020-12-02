package com.personal.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 通用业务层实现
 */
public interface CoreService<T> extends IService<T> {

    List<T> findByKv(Object... param);

    T findOneByKv(Object... param);

    Map<String, Object> convertToMap(Object... param);

    QueryWrapper<T> convertToQueryWrapper(Object... params);

    T selectOne(Wrapper<T> t);
}
