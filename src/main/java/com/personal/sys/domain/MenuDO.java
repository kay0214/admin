package com.personal.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Data
@TableName("sys_menu")
public class MenuDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    // 父菜单ID，一级菜单为0
    private Integer parentId;
    // 菜单名称
    private String name;
    // 菜单URL
    private String url;
    // 授权(多个用逗号分隔，如：user:list,user:create)
    private String perms;
    // 类型 0：目录 1：菜单 2：按钮
    private Integer type;
    // 菜单图标
    private String icon;
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
