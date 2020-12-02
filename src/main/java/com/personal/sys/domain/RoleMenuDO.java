package com.personal.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Data
@TableName("sys_role_menu")
public class RoleMenuDO implements Serializable {
    private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer  roleId;
	private Integer menuId;
}
