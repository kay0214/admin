package com.personal.job.service;

import com.personal.common.base.CoreService;
import com.personal.job.domain.TaskDO;
import org.quartz.SchedulerException;


/**
 * <pre>
 * 定时任务
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
public interface JobService extends CoreService<TaskDO> {
	
	void initSchedule() throws SchedulerException;

	void changeStatus(Integer jobId, String cmd) throws SchedulerException;

	void updateCron(Integer jobId) throws SchedulerException;

	void runNowOnce(Integer jobId)  throws SchedulerException ;
}
