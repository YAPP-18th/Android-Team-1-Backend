package net.mureng.batch.todayexpression.maintain.job;

import lombok.RequiredArgsConstructor;
import net.mureng.batch.core.job.CronJobRequest;
import net.mureng.batch.core.job.JobRequest;
import net.mureng.batch.core.job.JobRequestService;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Service;

import static net.mureng.batch.todayexpression.maintain.job.TodayExpressionMaintainJobConfig.TODAY_EXPRESSION_MAINTAIN_JOB_CRON;
import static net.mureng.batch.todayexpression.maintain.job.TodayExpressionMaintainJobConfig.TODAY_EXPRESSION_MAINTAIN_JOB_NAME;

@Service
@RequiredArgsConstructor
public class TodayExpressionMaintainJobRequestService implements JobRequestService {
    @Override
    public JobRequest create() {
        return CronJobRequest.builder()
                .jobName(TODAY_EXPRESSION_MAINTAIN_JOB_NAME)
                .jobClass(TodayExpressionMaintainQuartzJob.class)
                .jobDataMap(buildJobDataMap())
                .cronExpression(TODAY_EXPRESSION_MAINTAIN_JOB_CRON)
                .build();
    }

    private JobDataMap buildJobDataMap() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", TODAY_EXPRESSION_MAINTAIN_JOB_NAME);
        return jobDataMap;
    }
}
