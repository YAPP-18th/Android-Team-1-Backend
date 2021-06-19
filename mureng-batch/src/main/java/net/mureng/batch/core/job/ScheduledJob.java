package net.mureng.batch.core.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

@Builder @Getter
@AllArgsConstructor
public class ScheduledJob {
    @Builder.Default
    private final String jobGroup = "DEFAULT";
    private final String jobName;
    private final JobDetail jobDetail;
    private final Trigger trigger;
    private final Class<? extends Job> jobClass;
}
