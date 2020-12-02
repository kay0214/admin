package com.personal.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.domain.DictDO;
import com.personal.common.domain.Tree;
import com.personal.common.service.DictService;
import com.personal.common.utils.Result;
import com.personal.sys.domain.DeptDO;
import com.personal.sys.domain.RoleDO;
import com.personal.sys.domain.UserDO;
import com.personal.sys.service.RoleService;
import com.personal.sys.service.UserService;
import com.personal.sys.vo.UserVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {
    private final String prefix = "sys/user";
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    DictService dictService;

    @GetMapping
    @RequiresPermissions("sys:user:user")
    String user(Model model) {
        return prefix + "/user";
    }

    @ResponseBody
    @GetMapping("/list")
    public Result<Page<UserDO>> list(UserDO userDTO) {
        // 查询列表数据
        Page<UserDO> page = userService.page(getPage(UserDO.class), userService.convertToQueryWrapper("name", userDTO.getName(), "deptId", userDTO.getDeptId()));
        return Result.ok(page);
    }

    @GetMapping("/add")
    @RequiresPermissions("sys:user:add")
    String add(Model model) {
        List<RoleDO> roles = roleService.list(null);
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("sys:user:edit")
    String edit(Model model, @PathVariable("id") Integer id) {
        UserDO userDO = userService.getById(id);
        model.addAttribute("user", userDO);
        List<RoleDO> roles = roleService.findListStatusByUserId(id);
        model.addAttribute("roles", roles);
        return prefix + "/edit";
    }

    @ResponseBody
    @Log("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:add")
    Result<String> save(UserDO user) {
        userService.save(user);
        return Result.ok();
    }

    @ResponseBody
    @Log("更新用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:edit")
    Result<String> update(UserDO user) {
        userService.updateById(user);
        return Result.ok();
    }

    @ResponseBody
    @Log("更新用户")
    @PostMapping("/updatePeronal")
    @RequiresPermissions("sys:user:edit")
    Result<String> updatePeronal(UserDO user) {
        userService.updatePersonal(user);
        return Result.ok();
    }

    @ResponseBody
    @Log("删除用户")
    @PostMapping("/remove")
    @RequiresPermissions("sys:user:remove")
    Result<String> remove(Integer id) {
        userService.removeById(id);
        return Result.ok();
    }

    @ResponseBody
    @Log("批量删除用户")
    @PostMapping("/batchRemove")
    @RequiresPermissions("sys:user:batchRemove")
    Result<String> batchRemove(@RequestParam("ids[]") Long[] userIds) {
        userService.removeByIds(Arrays.asList(userIds));
        return Result.ok();
    }

    @ResponseBody
    @PostMapping("/exist")
    boolean exist(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !userService.exist(params);
    }

    @GetMapping("/resetPwd/{id}")
    @RequiresPermissions("sys:user:resetPwd")
    String resetPwd(@PathVariable("id") Integer userId, Model model) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        model.addAttribute("user", userDO);
        return prefix + "/reset_pwd";
    }

    @ResponseBody
    @Log("提交更改用户密码")
    @PostMapping("/resetPwd")
    Result<String> resetPwd(UserVO userVO) {
        userService.resetPwd(userVO, getUser());
        return Result.ok();
    }

    @ResponseBody
    @Log("admin提交更改用户密码")
    @PostMapping("/adminResetPwd")
    @RequiresPermissions("sys:user:resetPwd")
    Result<String> adminResetPwd(UserVO userVO) {
        userService.adminResetPwd(userVO);
        return Result.ok();

    }

    @ResponseBody
    @GetMapping("/tree")
    public Tree<DeptDO> tree() {
        return userService.getTree();
    }
    
    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/userTree";
    }
    
    @GetMapping("/personal")
    String personal(Model model) {
        UserDO userDO = userService.getById(getUserId());
        model.addAttribute("user", userDO);
        List<DictDO> hobbyList = dictService.getHobbyList(userDO);
        model.addAttribute("hobbyList", hobbyList);
        List<DictDO> sexList = dictService.getSexList();
        model.addAttribute("sexList", sexList);
        return prefix + "/personal";
    }

    @ResponseBody
    @Log("上传头像")
    @PostMapping("/uploadImg")
    Result<?> uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request)
            throws Exception {
        Map<String, Object> result = new HashMap<>();
        result = userService.updatePersonalImg(file, avatar_data, getUserId());
        return Result.ok(result);
    }
}
