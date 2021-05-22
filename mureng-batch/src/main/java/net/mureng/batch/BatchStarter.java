package net.mureng.batch;

import lombok.RequiredArgsConstructor;
import net.mureng.batch.core.job.JobRequestService;
import net.mureng.batch.core.job.ScheduledJobFactory;
import net.mureng.batch.core.scheduler.ScheduleService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BatchStarter implements ApplicationListener<ContextRefreshedEvent> {
    private final ScheduleService scheduleService;
    private final ScheduledJobFactory jobFactory;
    private final List<JobRequestService> jobRequestServices;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (JobRequestService service : jobRequestServices) {
            scheduleService.addJob(
                    jobFactory.buildScheduledJob(service.create())
            );
        }
    }
}
