package com.personal.job.jobs;

import com.personal.common.utils.DateUtils;
import com.personal.sys.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月22日 | Aron</small>
 */
@Component
public class TestJob implements Job {

    @Autowired
    private UserService userService;

    @Override
    public void execute(JobExecutionContext context){
        System.err.println("测试任务执行 | " + DateUtils.getLocalDateTime()
                + " | 定时统计人数：" + userService.list().size());
    }

}
