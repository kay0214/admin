package com.personal.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.domain.LogDO;
import com.personal.common.service.LogService;
import com.personal.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 日志
 */
@Controller
@RequestMapping("/common/log")
public class LogController extends BaseController {
    @Autowired
    private LogService logService;

    private final String prefix = "common/log";
    
    @GetMapping
    String log() {
        return prefix + "/log";
    }

    @ResponseBody
    @Log("查询留言列表")
    @GetMapping("/list")
    public Result<Page<LogDO>> list(LogDO logDTO) {
    	QueryWrapper<LogDO> wrapper = logService.convertToQueryWrapper("username", logDTO.getUsername());
    	wrapper.eq(logDTO.getUserId()!=null, "userId", logDTO.getUserId()).like(logDTO.getOperation() != null && logDTO.getOperation().length()>0, "operation", logDTO.getOperation());
        Page<LogDO> page = logService.page(getPage(LogDO.class), wrapper);
        return success(page);
    }

    @ResponseBody
    @Log("删除系统日志")
    @PostMapping("/remove")
    Result<String> remove(Integer id) {
        logService.removeById(id);
        return success();
    }

    @ResponseBody
    @Log("批量删除系统日志")
    @PostMapping("/batchRemove")
    Result<String> batchRemove(@RequestParam("ids[]") Integer[] ids) {
        logService.removeByIds(Arrays.asList(ids));
        return Result.fail();
    }
}
