package net.mureng.batch.core.scheduler.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.scheduler.JobRequest;
import net.mureng.batch.core.scheduler.service.ScheduleService;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final ScheduleService scheduleService;

    @Value("${collecter.scheduler.cron}")
    private String collectorSchedulerCronExpression;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // CronJob 생성
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobId", "123456789");
        JobRequest jobRequest = new JobRequest();
        jobRequest.setJobName("postCollectorJob");
        jobRequest.setCronExpression(collectorSchedulerCronExpression); //every min
        jobRequest.setJobDataMap(jobDataMap);
    }
}
