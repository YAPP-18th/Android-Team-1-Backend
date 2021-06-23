package net.mureng.batch.core.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.CronJobRequest;
import net.mureng.batch.core.job.JobRequest;
import net.mureng.batch.core.job.ScheduledJob;
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
        if (jobRequest instanceof CronJobRequest) {
            CronJobRequest cronJobRequest = (CronJobRequest)jobRequest;
            if (! isValidExpression(cronJobRequest.getCronExpression())) {
                throw new IllegalArgumentException("Provided expression " + cronJobRequest.getCronExpression() +
                        " is not a valid cron expression");
            }
            return createCronTrigger(cronJobRequest);
        }

        return createSimpleTrigger(jobRequest);
    }

    private Trigger createCronTrigger(CronJobRequest jobRequest) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName(jobRequest.getJobName());
        factoryBean.setGroup(jobRequest.getJobGroup());
        factoryBean.setCronExpression(jobRequest.getCronExpression());
        factoryBean.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            log.warn("ParseException: ", e);
        }
        return factoryBean.getObject();
    }

    private Trigger createSimpleTrigger(JobRequest jobRequest) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setName(jobRequest.getJobName());
        factoryBean.setGroup(jobRequest.getJobGroup());
        factoryBean.setStartTime(Date.from(jobRequest.getStartDateAt().atZone(ZoneId.systemDefault()).toInstant()));
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        factoryBean.setRepeatInterval(jobRequest.getRepeatIntervalInSeconds() * 1000); //ms 단위임
        factoryBean.setRepeatCount(jobRequest.getRepeatCount());

        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
}
