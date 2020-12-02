package com.personal.sys.controller;

import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.utils.Result;
import com.personal.sys.domain.UserOnline;
import com.personal.sys.service.SessionService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/sys/online")
public class SessionController extends BaseController {
	@Autowired
	SessionService sessionService;
	
	@GetMapping
	public String online() {
		return "sys/online/online";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public List<UserOnline> list(@RequestParam(required=false) String name) {
		return sessionService.list(name);
	}

	@ResponseBody
	@Log("强踢用户下线")
	@RequestMapping("/forceLogout/{sessionId}")
	public Result<String> forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
			sessionService.forceLogout(sessionId);
			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail();
		}
	}
	
	@ResponseBody
	@RequestMapping("/sessionList")
	public Collection<Session> sessionList() {
		return sessionService.sessionList();
	}
}
