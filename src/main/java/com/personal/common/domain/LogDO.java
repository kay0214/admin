package com.personal.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("sys_log")
@Data
public class LogDO extends Model<LogDO> implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = -938654836571738415L;
    
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String username;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;
    // 删除标记
    private Integer delFlag;
    // 创建人
    private Integer createBy;
    // 创建时间
    private LocalDateTime createTime;
    // 更新人
    private Integer updateBy;
    // 更新时间
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}