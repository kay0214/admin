package com.personal.sys.controller;

import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.constants.Constant;
import com.personal.common.domain.Tree;
import com.personal.common.enums.EnumErrorCode;
import com.personal.common.utils.Result;
import com.personal.sys.domain.DeptDO;
import com.personal.sys.service.DeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 部门管理
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/sys/dept")
public class DeptController extends BaseController {
    private final String prefix = "sys/dept";

    @Autowired
    private DeptService sysDeptService;

    @GetMapping
    @RequiresPermissions("system:sysDept:sysDept")
    String dept() {
        return prefix + "/dept";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("system:sysDept:sysDept")
    public List<DeptDO> list(DeptDO deptDTO) {
        return sysDeptService.findByKv("name", deptDTO.getName());
    }

    @GetMapping("/add/{pId}")
    @RequiresPermissions("system:sysDept:add")
    String add(@PathVariable("pId") Integer pId, Model model) {
        model.addAttribute("pId", pId);
        if (pId == 0) {
            model.addAttribute("pName", "总部门");
        } else {
            model.addAttribute("pName", sysDeptService.getById(pId).getName());
        }
        return prefix + "/add";
    }

    @GetMapping("/edit/{deptId}")
    @RequiresPermissions("system:sysDept:edit")
    String edit(@PathVariable("deptId") Integer deptId, Model model) {
        DeptDO sysDept = sysDeptService.getById(deptId);
        model.addAttribute("sysDept", sysDept);
        if (Constant.Sys.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
            model.addAttribute("parentDeptName", "无");
        } else {
            DeptDO parDept = sysDeptService.getById(sysDept.getParentId());
            model.addAttribute("parentDeptName", parDept.getName());
        }
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @Log("添加部门")
    @PostMapping("/save")
    @RequiresPermissions("system:sysDept:add")
    public Result<String> save(DeptDO sysDept) {
        sysDeptService.save(sysDept);
        return Result.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @Log("更新部门")
    @RequestMapping("/update")
    @RequiresPermissions("system:sysDept:edit")
    public Result<String> update(DeptDO sysDept) {
        sysDeptService.updateById(sysDept);
        return Result.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @Log("删除部门")
    @PostMapping("/remove")
    @RequiresPermissions("system:sysDept:remove")
    public Result<String> remove(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", id);
        int size = sysDeptService.listByMap(map).size();
        if (size > 0) {
            return Result.build(EnumErrorCode.deptUpdateErrorExistChilds.getCode(),
                    EnumErrorCode.deptUpdateErrorExistChilds.getMsg());
        }
        if (sysDeptService.checkDeptHasUser(id)) {
            sysDeptService.removeById(id);
            return Result.ok();
        } else {
            return Result.build(EnumErrorCode.deptDeleteErrorExistUsers.getCode(),
                    EnumErrorCode.deptDeleteErrorExistUsers.getMsg());
        }
    }

    /**
     * 删除
     */
    @ResponseBody
    @Log("删除部门")
    @PostMapping("/batchRemove")
    @RequiresPermissions("system:sysDept:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Integer[] deptIds) {
        sysDeptService.removeByIds(Arrays.asList(deptIds));
        return Result.ok();
    }

    @ResponseBody
    @GetMapping("/tree")
    public Tree<DeptDO> tree() {
        return sysDeptService.getTree();
    }

    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/deptTree";
    }

}
