package net.mureng.batch.push.job;

import lombok.RequiredArgsConstructor;
import net.mureng.batch.core.job.CronJobRequest;
import net.mureng.batch.core.job.JobRequest;
import net.mureng.batch.core.job.JobRequestService;
import net.mureng.batch.question.job.TodayQuestionRefreshQuartzJob;
import org.quartz.JobDataMap;
import org.springframework.stereotype.Service;

import static net.mureng.batch.push.job.FcmDailyPushJobConfig.FCM_DAILY_PUSH_JOB_JOB_CRON;
import static net.mureng.batch.push.job.FcmDailyPushJobConfig.FCM_DAILY_PUSH_JOB_NAME;
import static net.mureng.batch.question.job.TodayQuestionRefreshJobConfig.TODAY_QUESTION_REFRESH_JOB_CRON;
import static net.mureng.batch.question.job.TodayQuestionRefreshJobConfig.TODAY_QUESTION_REFRESH_JOB_NAME;

@Service
@RequiredArgsConstructor
public class FcmDailyPushJobRequestService implements JobRequestService {
    @Override
    public JobRequest create() {
        return CronJobRequest.builder()
                .jobName(FCM_DAILY_PUSH_JOB_NAME)
                .jobClass(FcmDailyPushQuartzJob.class)
                .jobDataMap(buildJobDataMap())
                .cronExpression(FCM_DAILY_PUSH_JOB_JOB_CRON)
                .build();
    }

    private JobDataMap buildJobDataMap() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", FCM_DAILY_PUSH_JOB_NAME);
        return jobDataMap;
    }
}
