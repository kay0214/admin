package com.personal.job.service.impl;

import com.personal.common.base.CoreServiceImpl;
import com.personal.common.constants.Constant;
import com.personal.common.utils.ScheduleJobUtils;
import com.personal.job.dao.TaskDao;
import com.personal.job.domain.ScheduleJobDO;
import com.personal.job.domain.TaskDO;
import com.personal.job.quartz.QuartzManager;
import com.personal.job.service.JobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public class JobServiceImpl extends CoreServiceImpl<TaskDao, TaskDO> implements JobService {

    @Autowired
    QuartzManager quartzManager;


    @Override
    public boolean removeById(Serializable id) {
        try {
            TaskDO scheduleJob =getById(id);
            quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            return retBool(baseMapper.deleteById(id));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteBatchIds(List<? extends Serializable> ids) {
        for (Serializable id : ids) {
            try {
                TaskDO scheduleJob = getById(id);
                quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            } catch (SchedulerException e) {
                e.printStackTrace();
                return false;
            }
        }
        return retBool(baseMapper.deleteBatchIds(ids));
    }

    @Override
    public void initSchedule() {
        // 这里获取任务信息数据
        List<TaskDO> jobList = baseMapper.selectList(null);
        for (TaskDO scheduleJob : jobList) {
            if ("1".equals(scheduleJob.getJobStatus())) {
                ScheduleJobDO job = ScheduleJobUtils.entityToData(scheduleJob);
                quartzManager.addJob(job);
            }

        }
    }

    @Override
    public void changeStatus(Integer jobId, String cmd) throws SchedulerException {
        TaskDO scheduleJob = getById(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (Constant.Job.STATUS_RUNNING_STOP.equals(cmd)) {
            quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            scheduleJob.setJobStatus(ScheduleJobDO.STATUS_NOT_RUNNING);
        } else {
            if (!Constant.Job.STATUS_RUNNING_START.equals(cmd)) {
            } else {
                scheduleJob.setJobStatus(ScheduleJobDO.STATUS_RUNNING);
                quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
            }
        }
        updateById(scheduleJob);
    }

    @Override
    public void updateCron(Integer jobId) throws SchedulerException {
        TaskDO scheduleJob = getById(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (ScheduleJobDO.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
            quartzManager.updateJobCron(ScheduleJobUtils.entityToData(scheduleJob));
        }
        updateById(scheduleJob);
    }

    @Override
    public void runNowOnce(Integer jobId) throws SchedulerException {
        TaskDO scheduleJob = getById(jobId);
        if (scheduleJob == null) {
            return;
        }
        quartzManager.runAJobNow(ScheduleJobUtils.entityToData(scheduleJob));
    }

}
