package net.mureng.batch.todayexpression.refresh.job;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.AbstractJobConductor;
import net.mureng.batch.todayexpression.maintain.service.TodayUsefulExpressionMaintainService;
import net.mureng.batch.todayexpression.refresh.service.TodayUsefulExpressionRefreshService;
import net.mureng.batch.util.CronExpression;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class TodayExpressionRefreshJobConductor extends AbstractJobConductor {
    public static final String TODAY_EXPRESSION_REFRESH_JOB_NAME = "todayExpressionRefreshJob";
    public static final String TODAY_EXPRESSION_REFRESH_JOB_LAUNCHER_NAME = TODAY_EXPRESSION_REFRESH_JOB_NAME + "Launcher";
    public static final String TODAY_EXPRESSION_REFRESH_JOB_CRON = CronExpression.EVERY_DAY_00_AM;

    private TodayUsefulExpressionRefreshService todayUsefulExpressionRefreshService;

    @Autowired
    public void setTodayUsefulExpressionRefreshService(TodayUsefulExpressionRefreshService todayUsefulExpressionRefreshService) {
        this.todayUsefulExpressionRefreshService = todayUsefulExpressionRefreshService;
    }

    @Override
    public String getJobLauncherName() {
        return TODAY_EXPRESSION_REFRESH_JOB_LAUNCHER_NAME;
    }

    @Override
    protected String getJobName() {
        return TODAY_EXPRESSION_REFRESH_JOB_NAME;
    }

    @Override
    protected String getCronExpression() {
        return TODAY_EXPRESSION_REFRESH_JOB_CRON;
    }

    @Override
    protected Job getJob() {
        return jobBuilderFactory.get(TODAY_EXPRESSION_REFRESH_JOB_NAME)
                .start(todayExpressionRefreshStep())
                .build();
    }

    public Step todayExpressionRefreshStep() {
        return stepBuilderFactory.get("todayExpressionRefreshStep")
                .tasklet((contribution, context) -> {
                    todayUsefulExpressionRefreshService.reselectTodayUsefulExpression();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
