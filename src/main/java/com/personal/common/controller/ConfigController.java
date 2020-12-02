package com.personal.common.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.domain.ConfigDO;
import com.personal.common.service.ConfigService;
import com.personal.common.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 系统配置
 */
@Controller
@RequestMapping("/common/config")
public class ConfigController extends BaseController {
    @Autowired
    private ConfigService configService;
    
    @GetMapping
    @RequiresPermissions("common:config:config")
    String Config() {
        return "common/config/config";
    }
    
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:config:config")
    public Result<Page<ConfigDO>> list(ConfigDO configDTO) {
        Page<ConfigDO> page = configService.page(getPage(ConfigDO.class), configService.convertToQueryWrapper("k", configDTO.getK()));
        return success(page);
    }
    
    @GetMapping("/add")
    @RequiresPermissions("common:config:add")
    String add() {
        return "common/config/add";
    }
    
    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:config:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        ConfigDO config = configService.getById(id);
        model.addAttribute("config", config);
        return "common/config/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @Log("添加系统配置")
    @PostMapping("/save")
    @RequiresPermissions("common:config:add")
    public Result<String> save(ConfigDO config) {
        if (configService.save(config)) {
            return success();
        }
        return fail();
    }

    /**
     * 修改
     */
    @ResponseBody
    @Log("更新系统配置")
    @RequestMapping("/update")
    @RequiresPermissions("common:config:edit")
    public Result<String> update(ConfigDO config) {
        configService.updateById(config);
        return success();
    }

    /**
     * 删除
     */
    @ResponseBody
    @Log("删除系统配置")
    @PostMapping("/remove")
    @RequiresPermissions("common:config:remove")
    public Result<String> remove(Integer id) {
        configService.removeById(id);
        return success();
    }

    /**
     * 删除
     */
    @ResponseBody
    @Log("批量删除系统配置")
    @PostMapping("/batchRemove")
    @RequiresPermissions("common:config:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        configService.removeByIds(Arrays.asList(ids));
        return success();
    }

}
