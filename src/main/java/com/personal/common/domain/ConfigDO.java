package com.personal.common.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("sys_config")
@Data
public class ConfigDO extends Model<ConfigDO> implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private String k;
    private String v;
    private Integer kvType;
    private String remark;
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
        return id;
    }
}
