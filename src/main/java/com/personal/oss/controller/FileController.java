package com.personal.oss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.enums.EnumErrorCode;
import com.personal.common.utils.Result;
import com.personal.oss.domain.FileDO;
import com.personal.oss.service.FileService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * <pre>
 * 文件上传
 * </pre>
 * <p>
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/common/sysFile")
public class FileController extends BaseController {
    
    @Autowired
    private FileService sysFileService;
    
    @GetMapping
    @RequiresPermissions("oss:file:file")
    String sysFile(Model model) {
        return "common/file/file";
    }
    
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("oss:file:list")
    public Result<Page<FileDO>> list(FileDO fileDTO) {
        QueryWrapper<FileDO> wrapper = new QueryWrapper<FileDO>(fileDTO);
        Page<FileDO> page = sysFileService.page(getPage(FileDO.class), wrapper);
        return success(page);
    }
    
    @GetMapping("/add")
    @RequiresPermissions("oss:file:add")
    String add() {
        return "common/sysFile/add";
    }
    
    @GetMapping("/edit")
    @RequiresPermissions("oss:file:update")
    String edit(Integer id, Model model) {
        FileDO sysFile = sysFileService.getById(id);
        model.addAttribute("sysFile", sysFile);
        return "common/sysFile/edit";
    }
    
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("oss:file:info")
    public Result<FileDO> info(@PathVariable("id") Integer id) {
        FileDO sysFile = sysFileService.getById(id);
        return success(sysFile);
    }
    
    /**
     * 保存
     */
    @ResponseBody
    @Log("添加文件")
    @PostMapping("/save")
    @RequiresPermissions("oss:file:add")
    public Result<String> save(FileDO sysFile) {
        sysFileService.save(sysFile);
        return success();
    }
    
    /**
     * 修改
     */
    @Log("更新文件")
    @RequestMapping("/update")
    @RequiresPermissions("oss:file:update")
    public Result<String> update(@RequestBody FileDO sysFile) {
        sysFileService.updateById(sysFile);
        return success();
    }
    
    /**
     * 删除
     */
    @ResponseBody
    @Log("删除文件")
    @PostMapping("/remove")
    @RequiresPermissions("oss:file:remove")
    public Result<String> remove(Integer id) {
        sysFileService.removeById(id);
        return success();
    }
    
    /**
     * 删除
     */
    @ResponseBody
    @Log("批量删除文件")
    @PostMapping("/batchRemove")
    @RequiresPermissions("oss:file:remove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        sysFileService.removeByIds(Arrays.asList(ids));
        return success();
    }

    @ResponseBody
    @Log("上传文件")
    @PostMapping("/upload")
    @RequiresPermissions("oss:file:add")
    Result<String> upload(@RequestParam("file") MultipartFile file) {
        String url = "";
        try {
            url = sysFileService.upload(file.getBytes(), file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.build(EnumErrorCode.FileUploadGetBytesError.getCode(),
                    EnumErrorCode.FileUploadGetBytesError.getMsg());
        }
        return success(url);
    }
    
}
