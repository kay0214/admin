package com.personal.common.shiro.controller;

import com.personal.common.base.BaseController;
import com.personal.common.enums.EnumErrorCode;
import com.personal.common.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年5月1日 | Aron</small>
 */
@RestController
@RequestMapping("/shiro")
public class ShiroController extends BaseController {

    @RequestMapping("/405")
    public Result<String> http405() {
        return Result.build(EnumErrorCode.apiAuthorizationInvalid.getCode(), EnumErrorCode.apiAuthorizationInvalid.getMsg());
    }
    
    @RequestMapping("/500")
    public Result<String> http500() {
        return Result.build(EnumErrorCode.unknowFail.getCode(), EnumErrorCode.unknowFail.getMsg());
    }
}