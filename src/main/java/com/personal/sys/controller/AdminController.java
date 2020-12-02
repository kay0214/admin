package com.personal.sys.controller;

import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.domain.Tree;
import com.personal.common.enums.EnumErrorCode;
import com.personal.common.utils.Result;
import com.personal.oss.service.FileService;
import com.personal.sys.domain.MenuDO;
import com.personal.sys.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private FileService fileService;

    @GetMapping({"/",""})
    String welcome(Model model) {
        return "redirect:admin/login";
    }


    @GetMapping({ "/index" })
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        model.addAttribute("username", getUser().getUsername());
        // FileDO fileDO = fileService.getById(getUser().getPicId());
        String icon = getUser().getIcon();
        model.addAttribute("picUrl", icon == null ? "/img/photo_s.jpg" : icon);
       /* List<MpConfigDO> mpList = mpConfigService.list(null);
        model.addAttribute("mpList", mpList);*/
        return "admin/index_v1";
    }

    @GetMapping("/login")
    String login() {
        return "admin/login";
    }

    @Log("登录")
    @ResponseBody
    @PostMapping("/login")
    Result<String> ajaxLogin(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        token.setRememberMe(true);//记住我是可选项，但只有会话缓存到redis等持久存储才能真正记住
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return Result.ok();
        } catch (AuthenticationException e) {
            return Result.build(EnumErrorCode.userLoginFail.getCode(), EnumErrorCode.userLoginFail.getMsg());
        }
    }

    @GetMapping("/main")
    String main() {
        return "admin/main";
    }

    @GetMapping("/403")
    String error403() {
        return "admin/403";
    }
}
