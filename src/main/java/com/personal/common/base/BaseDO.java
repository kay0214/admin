package com.personal.common.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务表基础公共字段
 *
 * delFlag 由 mybatis-plus.global-config.sql-injector:com.baomidou.mybatisplus.mapper.LogicSqlInjector 自动维护
 * version 由 MyBatisConfig.optimisticLockerInterceptor 自动维护
 *
 * 以下字段由 meta-object-handler: com.personal.common.mp.MpMetaObjectHandler 自动维护
 * createBy
 * createTime
 * updateBy
 * updateTime
 */
@Data
@SuppressWarnings("serial")
public class BaseDO implements Serializable {

    @TableLogic
    @ExcelIgnore
    private Integer delFlag;

/*    @Version
    @ExcelIgnore
    private Integer version;*/

    @TableField(fill = FieldFill.INSERT)
    @ExcelIgnore
    private Integer createBy;

    @TableField(fill = FieldFill.INSERT)
    @ExcelProperty(value = "创建时间",index = 5)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    @ExcelIgnore
    private Integer updateBy;

    @TableField(fill = FieldFill.UPDATE)
    @ExcelIgnore
    private LocalDateTime updateTime;
}
