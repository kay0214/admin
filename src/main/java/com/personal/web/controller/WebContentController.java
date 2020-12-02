package com.personal.web.controller;


import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.utils.Result;
import com.personal.content.domain.ContentManageDO;
import com.personal.content.service.ContentManageService;
import com.personal.web.request.ContentManageRequest;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * <pre>
 * 留言表
 * </pre>
 * <small> 2020-01-13 16:14:28 | admin</small>
 */
@RestController
@RequestMapping("/web/content")
public class WebContentController extends BaseController {
	@Autowired
	private ContentManageService contentManageService;
	
	@Log("添加留言表")
	@PostMapping("/save")
	public Result<String> save(@RequestBody ContentManageRequest contentManageRequest, HttpServletRequest request, HttpServletResponse response){
		if (!CaptchaUtil.ver(contentManageRequest.getVerCode(), request)) {
			CaptchaUtil.clear(request);  // 清除session中的验证码
			return Result.build(406,"验证码不正确");
		}
		ContentManageDO contentManage = new ContentManageDO();
		BeanUtils.copyProperties(contentManageRequest, contentManage);
		contentManageService.save(contentManage);
        return success();
	}

	
}
