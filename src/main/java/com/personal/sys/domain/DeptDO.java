package com.personal.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Data
@TableName("sys_dept")
public class DeptDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    // 上级部门ID，一级部门为0
    private Integer parentId;
    // 部门名称
    private String name;
    // 排序
    private Integer orderNum;
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
}
