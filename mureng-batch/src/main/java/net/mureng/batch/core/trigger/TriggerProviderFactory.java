package net.mureng.batch.core.trigger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.CronJobRequest;
import net.mureng.batch.core.job.JobRequest;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import static org.quartz.CronExpression.isValidExpression;

@Slf4j
@Component
@RequiredArgsConstructor
public class TriggerProviderFactory {
    private final SimpleTriggerProvider simpleTriggerProvider;
    private final CronTriggerProvider cronTriggerProvider;

    public TriggerProvider getInstance(JobRequest jobRequest) {
        if (jobRequest instanceof CronJobRequest) {
            return this.cronTriggerProvider;
        }

        return this.simpleTriggerProvider;
    }
}
