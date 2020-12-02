/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.personal.web.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangqingqing
 * @version CaptchaController, v0.1 2020/1/14 11:14
 */
@Controller
public class CaptchaController {

    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        // 设置位数
//        CaptchaUtil.out(5, request, response);
//        // 设置宽、高、位数
//        CaptchaUtil.out(130, 48, 5, request, response);
//
//        // 使用gif验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130,48);
        CaptchaUtil.out(captcha, request, response);
//        String captcha = (String)request.getSession().getAttribute("captcha");
       // CaptchaUtil.out(request, response);
    }
}