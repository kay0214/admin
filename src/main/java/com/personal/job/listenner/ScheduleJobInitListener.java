package com.personal.job.listenner;

import com.personal.job.quartz.QuartzManager;
import com.personal.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    JobService scheduleJobService;

    @Autowired
    QuartzManager quartzManager;

    @Override
    public void run(String... arg0) throws Exception {
        try {
            scheduleJobService.initSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}