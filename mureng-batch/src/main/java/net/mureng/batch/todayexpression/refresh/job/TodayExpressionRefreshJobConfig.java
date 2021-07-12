package net.mureng.batch.todayexpression.refresh.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.config.AbstractJobConfig;
import net.mureng.batch.core.job.MurengJobLauncher;
import net.mureng.batch.todayexpression.refresh.service.TodayUsefulExpressionRefreshService;
import net.mureng.batch.util.CronExpression;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 오늘의 표현을 갱신한다.
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class TodayExpressionRefreshJobConfig extends AbstractJobConfig {
    public static final String TODAY_EXPRESSION_REFRESH_JOB_NAME = "todayExpressionRefreshJob";
    public static final String TODAY_EXPRESSION_REFRESH_JOB_LAUNCHER_NAME = TODAY_EXPRESSION_REFRESH_JOB_NAME + "Launcher";
    public static final String TODAY_EXPRESSION_REFRESH_JOB_CRON = CronExpression.EVERY_DAY_00_AM;

    private final TodayUsefulExpressionRefreshService todayUsefulExpressionRefreshService;

    @Bean(name = TODAY_EXPRESSION_REFRESH_JOB_LAUNCHER_NAME)
    public MurengJobLauncher todayExpressionRefreshJobLauncher(JobLauncher jobLauncher) {
        return new MurengJobLauncher(todayExpressionRefreshJob(), jobLauncher);
    }

    @Bean(name = TODAY_EXPRESSION_REFRESH_JOB_NAME)
    public Job todayExpressionRefreshJob() {
        return jobBuilderFactory.get(TODAY_EXPRESSION_REFRESH_JOB_NAME)
                .start(todayExpressionRefreshStep())
                .build();
    }

    @Bean
    @JobScope
    public Step todayExpressionRefreshStep() {
        return stepBuilderFactory.get("todayExpressionRefreshStep")
                .tasklet((contribution, context) -> {
                    todayUsefulExpressionRefreshService.reselectTodayUsefulExpression();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}