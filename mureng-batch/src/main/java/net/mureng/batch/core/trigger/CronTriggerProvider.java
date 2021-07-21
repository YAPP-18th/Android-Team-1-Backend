package net.mureng.batch.core.trigger;

import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.CronJobRequest;
import net.mureng.batch.core.job.JobRequest;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.TimeZone;

import static org.quartz.CronExpression.isValidExpression;

@Slf4j
@Component
public class CronTriggerProvider implements TriggerProvider {
    @Override
    public Trigger getTrigger(JobRequest jobRequest) {
        CronJobRequest cronJobRequest = (CronJobRequest)jobRequest;
        checkValidExpression(cronJobRequest.getCronExpression());

        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName(cronJobRequest.getJobName());
        factoryBean.setGroup(cronJobRequest.getJobGroup());
        factoryBean.setCronExpression(cronJobRequest.getCronExpression());
        factoryBean.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            log.warn("ParseException: ", e);
        }
        return factoryBean.getObject();
    }

    private void checkValidExpression(String cronExpression) {
        if (! isValidExpression(cronExpression)) {
            throw new IllegalArgumentException("Provided expression " + cronExpression +
                    " is not a valid cron expression");
        }
    }
}
