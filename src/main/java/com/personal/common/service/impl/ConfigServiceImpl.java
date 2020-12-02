package com.personal.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.personal.common.base.CoreServiceImpl;
import com.personal.common.dao.ConfigDao;
import com.personal.common.domain.ConfigDO;
import com.personal.common.service.ConfigService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConfigServiceImpl extends CoreServiceImpl<ConfigDao, ConfigDO> implements ConfigService {
    
    @Override
    public ConfigDO getByKey(String k) {
        ConfigDO entity = new ConfigDO();
        entity.setK(k);
        return baseMapper.selectOne(new QueryWrapper<ConfigDO>().setEntity(entity));
    }
    
    @Override
    public String getValueByKey(String k) {
        ConfigDO bean = this.getByKey(k);
        return bean == null ? "" : bean.getV();
    }
    
    @Override
    public void updateKV(Map<String, String> kv) {
        for(Map.Entry<String, String> entry : kv.entrySet()){
            ConfigDO config = this.getByKey(entry.getKey());
            config.setV(entry.getValue());
            super.updateById(config);
        }
    }
    
    @Override
    public List<ConfigDO> findListByKvType(int kvType){
        ConfigDO configDO = new ConfigDO();
        configDO.setKvType(kvType);
        QueryWrapper<ConfigDO> ew = new QueryWrapper<>(configDO);
        List<ConfigDO> list = super.list(ew);
        return list;
    }
    
    
}
