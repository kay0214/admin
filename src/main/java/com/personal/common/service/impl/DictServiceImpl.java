package com.personal.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.personal.common.base.CoreServiceImpl;
import com.personal.common.dao.DictDao;
import com.personal.common.domain.DictDO;
import com.personal.common.service.DictService;
import com.personal.sys.domain.UserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DictServiceImpl extends CoreServiceImpl<DictDao, DictDO> implements DictService {

    @Override
    public List<DictDO> listType() {
        return baseMapper.listType();
    }

    @Override
    public String getName(String type, String value) {
        QueryWrapper<DictDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictDO::getType,type).eq(DictDO::getValue,value);
        DictDO selectOne = DictDO.dao.selectOne(queryWrapper);
        return selectOne == null ? "" : selectOne.getName();
    }

    @Override
    public List<DictDO> getHobbyList(UserDO userDO) {
        QueryWrapper<DictDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictDO::getType,"hobby");
        List<DictDO> hobbyList = DictDO.dao.selectList(queryWrapper);

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (DictDO hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemark("true");
                    break;
                }
            }
        }

        return hobbyList;
    }

    @Override
    public List<DictDO> getSexList() {
        QueryWrapper<DictDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DictDO::getType,"sex");
        return DictDO.dao.selectList(queryWrapper);
    }

}
