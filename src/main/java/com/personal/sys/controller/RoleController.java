package com.personal.sys.controller;

import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.utils.Result;
import com.personal.sys.domain.RoleDO;
import com.personal.sys.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController {
    private final String prefix = "sys/role";

    @Autowired
    RoleService roleService;

    @GetMapping
    @RequiresPermissions("sys:role:role")
    String role() {
        return prefix + "/role";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("sys:role:role")
    List<RoleDO> list() {
        return roleService.findAll();
    }

    @GetMapping("/add")
    @RequiresPermissions("sys:role:add")
    String add() {
        return prefix + "/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("sys:role:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        RoleDO roleDO = roleService.getById(id);
        model.addAttribute("role", roleDO);
        return prefix + "/edit";
    }

    @ResponseBody
    @Log("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:add")
    Result<String> save(RoleDO role) {
        roleService.save(role);
        return Result.ok();
    }

    @ResponseBody
    @Log("更新角色")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:edit")
    Result<String> update(RoleDO role) {
        roleService.updateById(role);
        return Result.ok();
    }

    @ResponseBody
    @Log("删除角色")
    @PostMapping("/remove")
    @RequiresPermissions("sys:role:remove")
    Result<String> save(Integer id) {
        roleService.removeById(id);
        return Result.ok();
    }

    @ResponseBody
    @Log("批量删除角色")
    @PostMapping("/batchRemove")
    @RequiresPermissions("sys:role:batchRemove")
    Result<String> batchRemove(@RequestParam("ids[]") Integer[] ids) {
        roleService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }
}
