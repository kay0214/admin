package com.personal.web.request;


import com.personal.common.base.BaseDO;
import lombok.Data;

/**
 * 
 * <pre>
 * 留言表
 * </pre>
 * <small> 2020-01-13 16:14:28 | admin</small>
 */
@Data
@SuppressWarnings("serial")
public class ContentManageRequest extends BaseDO {

	private Integer id;

    /** 姓名 */
    private String userName;

    /** 手机号 */
    private String mobile;

    /** 省市 */
    private String address;

    /** 备注 */
    private String content;

    private String verCode;

}
