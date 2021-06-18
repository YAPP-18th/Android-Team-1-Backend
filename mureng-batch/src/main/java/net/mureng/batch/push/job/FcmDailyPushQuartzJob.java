package net.mureng.batch.push.job;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.MurengQuartzJob;
import net.mureng.batch.util.JobLauncherUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import static net.mureng.batch.push.job.FcmDailyPushJobConfig.FCM_DAILY_PUSH_JOB_LAUNCHER_NAME;
import static net.mureng.batch.question.job.TodayQuestionRefreshJobConfig.TODAY_QUESTION_REFRESH_JOB_LAUNCHER_NAME;

@Slf4j
@Getter @Setter
public class FcmDailyPushQuartzJob extends MurengQuartzJob {

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobLauncherUtil.getBeanName(FCM_DAILY_PUSH_JOB_LAUNCHER_NAME).execute();
    }
}
