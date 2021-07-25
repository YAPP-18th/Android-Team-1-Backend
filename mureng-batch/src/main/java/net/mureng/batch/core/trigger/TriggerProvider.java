package net.mureng.batch.core.trigger;

import net.mureng.batch.core.job.JobRequest;
import org.quartz.Trigger;

public interface TriggerProvider {
    Trigger getTrigger(JobRequest jobRequest);
}
