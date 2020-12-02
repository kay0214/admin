package com.personal.content.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.utils.Result;
import com.personal.content.domain.ContentManageDO;
import com.personal.content.service.ContentManageService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 
 * <pre>
 * 留言表
 * </pre>
 * <small> 2020-01-13 16:14:28 | admin</small>
 */
@Controller
@RequestMapping("/content/contentManage")
public class ContentManageController extends BaseController {
	@Autowired
	private ContentManageService contentManageService;
	
	@GetMapping
	@RequiresPermissions("content:contentManage:contentManage")
	String ContentManage(){
	    return "content/contentManage/contentManage";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("content:contentManage:contentManage")
	public Result<Page<ContentManageDO>> list(ContentManageDO contentManageDTO){
        QueryWrapper<ContentManageDO> wrapper = new QueryWrapper<>();
		wrapper.lambda().select()
				.and(StringUtils.isNotEmpty(contentManageDTO.getMobile()), obj->obj.eq(ContentManageDO::getMobile,contentManageDTO.getMobile()))
				.and(StringUtils.isNotEmpty(contentManageDTO.getUserName()), userName->userName.eq(ContentManageDO::getMobile,contentManageDTO.getMobile()))
				.and(StringUtils.isNotEmpty(contentManageDTO.getAddress()), address->address.like(ContentManageDO::getAddress,contentManageDTO.getAddress()));
		//wrapper = contentManageService.convertToQueryWrapper("userName", contentManageDTO.getUserName(), "mobile",contentManageDTO.getMobile(),"address",contentManageDTO.getAddress());
        Page<ContentManageDO> page = contentManageService.page(getPage(ContentManageDO.class), wrapper);
        return Result.ok(page);
	}
	
	@GetMapping("/add")
	@RequiresPermissions("content:contentManage:add")
	String add(){
	    return "content/contentManage/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("content:contentManage:edit")
	String edit(@PathVariable("id") Integer id, Model model){
		ContentManageDO contentManage = contentManageService.getById(id);
		model.addAttribute("contentManage", contentManage);
	    return "content/contentManage/edit";
	}

    @ResponseBody
	@Log("添加留言表")
	@PostMapping("/save")
	@RequiresPermissions("content:contentManage:add")
	public Result<String> save( ContentManageDO contentManage){
		contentManageService.save(contentManage);
        return success();
	}

    @ResponseBody
	@Log("修改留言表")
	@RequestMapping("/update")
	@RequiresPermissions("content:contentManage:edit")
	public Result<String>  update( ContentManageDO contentManage){
		boolean update = contentManageService.updateById(contentManage);
		return update ? success() : fail();
	}

    @ResponseBody
	@Log("删除留言表")
	@PostMapping( "/remove")
	@RequiresPermissions("content:contentManage:remove")
	public Result<String> remove(Integer id){
		contentManageService.removeById(id);
        return success();
	}

    @ResponseBody
	@Log("批量删除留言表")
	@PostMapping( "/batchRemove")
	@RequiresPermissions("content:contentManage:batchRemove")
	public Result<String>  remove(@RequestParam("ids[]") Integer[] ids){
		contentManageService.removeByIds(Arrays.asList(ids));
		return success();
	}
	
}
