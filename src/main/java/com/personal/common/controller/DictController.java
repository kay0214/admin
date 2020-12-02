package com.personal.common.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.domain.DictDO;
import com.personal.common.service.DictService;
import com.personal.common.utils.JSONUtils;
import com.personal.common.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典
 */
@Controller
@RequestMapping("/common/sysDict")
public class DictController extends BaseController {

    @Autowired
    private DictService sysDictService;
    
    @GetMapping
    @RequiresPermissions("common:sysDict:sysDict")
    String sysDict() {
        return "common/sysDict/sysDict";
    }
    
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:sysDict:sysDict")
    public Result<Page<DictDO>> list(DictDO dictDTO) {
        Page<DictDO> page = sysDictService.page(getPage(DictDO.class), sysDictService.convertToQueryWrapper("name", dictDTO.getName(), "type", dictDTO.getType()));
        return success(page);
    }
    
    @GetMapping("/add")
    @RequiresPermissions("common:sysDict:add")
    String add() {
        return "common/sysDict/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:sysDict:edit")
    String edit(@PathVariable("id") Integer id, Model model) {
        DictDO sysDict = sysDictService.getById(id);
        model.addAttribute("sysDict", sysDict);
        return "common/sysDict/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @Log("添加数据字典")
    @PostMapping("/save")
    @RequiresPermissions("common:sysDict:add")
    public Result<String> save(DictDO sysDict) {
        sysDictService.save(sysDict);
        return success();
    }

    /**
     * 修改
     */
    @ResponseBody
    @Log("更新数据字典")
    @RequestMapping("/update")
    @RequiresPermissions("common:sysDict:edit")
    public Result<String> update(DictDO sysDict) {
        sysDictService.updateById(sysDict);
        return success();
    }

    /**
     * 删除
     */
    @ResponseBody
    @Log("删除数据字典")
    @PostMapping("/remove")
    @RequiresPermissions("common:sysDict:remove")
    public Result<String> remove(Integer id) {
        sysDictService.removeById(id);
        return success();
    }

    /**
     * 删除
     */
    @ResponseBody
    @Log("删除数据字典")
    @PostMapping("/batchRemove")
    @RequiresPermissions("common:sysDict:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        sysDictService.removeByIds(Arrays.asList(ids));
        return success();
    }

    @ResponseBody
    @GetMapping("/type")
    public List<DictDO> listType() {
        return sysDictService.listType();
    };

    // 类别已经指定增加
    @GetMapping("/add/{type}/{description}")
    @RequiresPermissions("common:sysDict:add")
    String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/sysDict/add";
    }
    
    @ResponseBody
    @GetMapping("/list/{type}")
    public List<DictDO> listByType(@PathVariable("type") String type) {
        // 查询列表数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("type", type);
        return sysDictService.listByMap(map);
    }
}
