package com.personal.sys.controller;

import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.domain.Tree;
import com.personal.common.utils.Result;
import com.personal.sys.domain.MenuDO;
import com.personal.sys.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {
    private final String prefix = "sys/menu";

    @Autowired
    MenuService menuService;

    @GetMapping
    @RequiresPermissions("sys:menu:menu")
    String menu(Model model) {
        return prefix + "/menu";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:menu")
    List<MenuDO> list() {
        return menuService.list(null);
    }

    @GetMapping("/add/{pId}")
    @RequiresPermissions("sys:menu:add")
    String add(Model model, @PathVariable("pId") Integer pId) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.getById(pId).getName());
        }
        return prefix + "/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("sys:menu:edit")
    String edit(Model model, @PathVariable("id") Integer id) {
        MenuDO mdo = menuService.getById(id);
        Integer pId = mdo.getParentId();
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.getById(pId).getName());
        }
        model.addAttribute("menu", mdo);
        return prefix + "/edit";
    }

    @ResponseBody
    @Log("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:add")
    Result<String> save(MenuDO menu) {
        menuService.save(menu);
        return Result.ok();
    }

    @ResponseBody
    @Log("更新菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:edit")
    Result<String> update(MenuDO menu) {
        menuService.updateById(menu);
        return Result.ok();
    }

    @ResponseBody
    @Log("删除菜单")
    @PostMapping("/remove")
    @RequiresPermissions("sys:menu:remove")
    Result<String> remove(Integer id) {
        menuService.removeById(id);
        return Result.ok();
    }

    @ResponseBody
    @GetMapping("/tree")
    @Log("查询菜单树形数据")
    Tree<MenuDO> tree() {
        return menuService.getTree();
    }

    @ResponseBody
    @GetMapping("/tree/{roleId}")
    Tree<MenuDO> tree(@PathVariable("roleId") Integer roleId) {
        return menuService.getTree(roleId);
    }
}
