package net.mureng.batch.core.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.CronJobRequest;
import net.mureng.batch.core.job.JobRequest;
import net.mureng.batch.core.job.ScheduledJob;
import net.mureng.batch.core.trigger.TriggerProviderFactory;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import static org.quartz.CronExpression.isValidExpression;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledJobFactory {
    private final ApplicationContext context;
    private final TriggerProviderFactory triggerProviderFactory;

    public ScheduledJob buildScheduledJob(JobRequest jobRequest) {
        return ScheduledJob.builder()
                .jobName(jobRequest.getJobName())
                .jobGroup(jobRequest.getJobGroup())
                .jobClass(jobRequest.getJobClass())
                .jobDetail(createJobDetail(jobRequest))
                .trigger(createTrigger(jobRequest))
                .build();
    }

    private JobDetail createJobDetail(JobRequest jobRequest) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobRequest.getJobClass());
        factoryBean.setDurability(false);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobRequest.getJobName());
        factoryBean.setGroup(jobRequest.getJobGroup());

        if (jobRequest.getJobDataMap() != null) {
            factoryBean.setJobDataMap(jobRequest.getJobDataMap());
        }

        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    private Trigger createTrigger(JobRequest jobRequest) {
        return this.triggerProviderFactory.getInstance(jobRequest).getTrigger(jobRequest);
    }
}
