package com.personal.content.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.personal.common.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * <pre>
 * 留言表
 * </pre>
 * <small> 2020-01-13 16:14:28 | admin</small>
 */
@Data
@SuppressWarnings("serial")
@TableName("zz_content_manage")
@EqualsAndHashCode(callSuper=true)
public class ContentManageDO extends BaseDO {

    @TableId(value = "id", type = IdType.AUTO)
	private Integer id;

    /** 姓名 */
    private String userName;

    /** 手机号 */
    private String mobile;

    /** 省市 */
    private String address;

    /** 备注 */
    private String content;

}
