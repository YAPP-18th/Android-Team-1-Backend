package net.mureng.batch.core.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@SuperBuilder
@Getter @Setter
public class CronJobRequest extends JobRequest {
    private final String cronExpression;
}
