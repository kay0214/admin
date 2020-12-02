package com.personal.job.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.common.annotation.Log;
import com.personal.common.base.BaseController;
import com.personal.common.utils.Result;
import com.personal.job.domain.TaskDO;
import com.personal.job.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <pre>
 * 定时任务
 * </pre>
 *
 * <small> 2018年3月23日 | Aron</small>
 */
@Slf4j
@Controller
@RequestMapping("/common/job")
public class JobController extends BaseController {
    @Autowired
    private JobService taskScheduleJobService;

    @GetMapping
    String taskScheduleJob() {
        return "common/job/job";
    }

    @ResponseBody
    @GetMapping("/list")
    public Result<Page<TaskDO>> list(TaskDO taskDTO) {
        QueryWrapper<TaskDO> wrapper = new QueryWrapper<>(taskDTO);
        Page<TaskDO> page = taskScheduleJobService.page(getPage(TaskDO.class), wrapper);
        return success(page);
    }

    @GetMapping("/add")
    String add() {
        return "common/job/add";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        TaskDO job = taskScheduleJobService.getById(id);
        model.addAttribute("job", job);
        return "common/job/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @Log("添加定时任务")
    @PostMapping("/save")
    public Result<String> save(TaskDO taskScheduleJob) {
        taskScheduleJobService.save(taskScheduleJob);
        return success();
    }

    /**
     * 修改
     */
    @ResponseBody
    @Log("更新定时任务")
    @PostMapping("/update")
    public Result<String> update(TaskDO taskScheduleJob) {
        taskScheduleJobService.updateById(taskScheduleJob);
        return success();
    }

    /**
     * 删除
     */
    @ResponseBody
    @Log("删除定时任务")
    @PostMapping("/remove")
    public Result<String> remove(Integer id) {
        taskScheduleJobService.removeById(id);
        return success();
    }

    /**
     * 删除
     */
    @ResponseBody
    @Log("批量删除定时任务")
    @PostMapping("/batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        taskScheduleJobService.removeByIds(Arrays.asList(ids));
        return success();
    }

    @ResponseBody
    @Log("启停定时任务")
    @PostMapping(value = "/changeJobStatus")
    public Result<String> changeJobStatus(Integer id, String cmd) {
        String label = "停止";
        if ("start".equals(cmd)) {
            label = "启动";
        } else {
            label = "停止";
        }
        try {
            taskScheduleJobService.changeStatus(id, cmd);
            return success("任务" + label + "成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success("任务" + label + "失败");
    }

    @ResponseBody
    @Log("立即执行一次任务")
    @PostMapping(value = "/runNowOnce")
    public Result<String> runNowOnce(Integer id) {
        try {
            taskScheduleJobService.runNowOnce(id);
            return Result.ok("任务执行成功");
        } catch (Exception e) {
           log.error("执行任务失败",e);
        }
        return success("任务执行失败");
    }

}
