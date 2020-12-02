package com.personal.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Data
@TableName("sys_user")
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    // 用户名
    private String username;
    // salt
    private String salt;
    // 密码
    private String password;
    // 部门
    private Integer deptId;
    @TableField(exist = false)
    private String deptName;
    // 用户真实姓名
    private String name;
    // 邮箱
    private String email;
    // 手机号
    private String mobile;
    // 状态 0:禁用，1:正常
    private Integer status;
    //角色
    @TableField(exist = false)
    private List<Integer> roleIds;
    //性别
    private Integer sex;
    //出身日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    //图片url
    private String icon;
    //现居住地
    private String liveAddress;
    //爱好
    private String hobby;
    //省份
    private String province;
    //所在城市
    private String city;
    //所在地区
    private String district;
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
