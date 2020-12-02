package com.personal.web.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.personal.common.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * <pre>
 * 信息管理表
 * </pre>
 * <small> 2020-03-10 10:45:29 | admin</small>
 */
@Data
@SuppressWarnings("serial")
@TableName("tobacco_manage")
@EqualsAndHashCode(callSuper=true)
public class ManageRequest extends BaseDO {

    @TableId(value = "id", type = IdType.AUTO)
	private Integer id;

    /** 姓名 */
    private String name;

    /** 手机号 */
    private String tel;

    /** 省份 */
    private String province;

    /** 省份 */
    private String city;

    /** 省份 */
    private String district;

    /** 备注 */
    private String idcard;

}
