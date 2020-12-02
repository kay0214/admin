package com.personal.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("sys_dict")
@Data
public class DictDO extends Model<DictDO> implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    public static final DictDO dao = new DictDO();

    // 编号
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    // 标签名
    private String name;
    // 数据值
    private String value;
    // 类型
    private String type;
    // 描述
    private String description;
    // 排序（升序）
    private Integer sort;
    // 父级编号
    private Integer parentId;
    // 备注信息
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
        return this.id;
    }
}
