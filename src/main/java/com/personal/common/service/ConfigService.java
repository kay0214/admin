package com.personal.common.service;

import com.personal.common.base.CoreService;
import com.personal.common.domain.ConfigDO;

import java.util.List;
import java.util.Map;

public interface ConfigService extends CoreService<ConfigDO> {
    ConfigDO getByKey(String k);

    String getValueByKey(String k);
    
    void updateKV(Map<String, String> kv);
    
    List<ConfigDO> findListByKvType(int kvType);
}
