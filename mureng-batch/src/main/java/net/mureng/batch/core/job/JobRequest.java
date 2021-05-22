package net.mureng.batch.core.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@SuperBuilder
@Getter @Setter
public class JobRequest {
    @Builder.Default
    protected final String jobGroup = "DEFAULT";
    protected final String jobName;
    protected final Class<? extends Job> jobClass;
    protected final JobDataMap jobDataMap;

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startDateAt = LocalDateTime.now();
    private final long repeatIntervalInSeconds;
    private final int repeatCount = -1;
}
