package com.personal.common.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.personal.common.constants.Constant;
import com.personal.common.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis-plus自动填充
 */
@Slf4j
@Component
public class MpMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(Constant.Column.COLUMN_CREATE_TIME, metaObject);
        if (createTime == null) {
            if(log.isDebugEnabled()){
                log.debug("set COLUMN_CREATE_TIME = " + LocalDateTime.now());
            }
            setFieldValByName(Constant.Column.COLUMN_CREATE_TIME, LocalDateTime.now(), metaObject);
        }

        Object createBy = getFieldValByName(Constant.Column.COLUMN_CREATE_BY, metaObject);
        if (createBy == null) {
            Integer userId = ShiroUtils.getUserId();
            if(log.isDebugEnabled()){
                log.debug("set COLUMN_CREATE_BY = " + userId);
            }
            setFieldValByName(Constant.Column.COLUMN_CREATE_BY, userId, metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName(Constant.Column.COLUMN_UPDATE_TIME, metaObject);
        if (updateTime == null) {
            if(log.isDebugEnabled()){
                log.debug("set COLUMN_UPDATE_AT = " + LocalDateTime.now());
            }
            setFieldValByName(Constant.Column.COLUMN_UPDATE_TIME, LocalDateTime.now(), metaObject);
        }

        Object updateBy = getFieldValByName(Constant.Column.COLUMN_UPDATE_BY, metaObject);
        if (updateBy == null) {
            Integer userId = ShiroUtils.getUserId();
            if(log.isDebugEnabled()){
                log.debug("set COLUMN_UPDATE_BY = " + userId);
            }
            setFieldValByName(Constant.Column.COLUMN_UPDATE_BY, userId, metaObject);
        }

    }
}
