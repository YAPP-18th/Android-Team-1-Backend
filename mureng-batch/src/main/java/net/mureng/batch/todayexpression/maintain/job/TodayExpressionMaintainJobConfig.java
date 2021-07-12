package net.mureng.batch.todayexpression.maintain.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.config.AbstractJobConfig;
import net.mureng.batch.core.job.MurengJobLauncher;
import net.mureng.batch.todayexpression.maintain.service.TodayUsefulExpressionMaintainService;
import net.mureng.batch.util.CronExpression;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 오늘의 표현 개수를 두 개로 유지한다.
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class TodayExpressionMaintainJobConfig extends AbstractJobConfig {
    public static final String TODAY_EXPRESSION_MAINTAIN_JOB_NAME = "todayExpressionMaintainJob";
    public static final String TODAY_EXPRESSION_MAINTAIN_JOB_LAUNCHER_NAME = TODAY_EXPRESSION_MAINTAIN_JOB_NAME + "Launcher";
    public static final String TODAY_EXPRESSION_MAINTAIN_JOB_CRON = CronExpression.EVERY_10_MINUTES;

    private final TodayUsefulExpressionMaintainService todayUsefulExpressionMaintainService;

    @Bean(name = TODAY_EXPRESSION_MAINTAIN_JOB_LAUNCHER_NAME)
    public MurengJobLauncher todayExpressionMaintainJobLauncher(JobLauncher jobLauncher) {
        return new MurengJobLauncher(todayExpressionMaintainJob(), jobLauncher);
    }

    @Bean(name = TODAY_EXPRESSION_MAINTAIN_JOB_NAME)
    public Job todayExpressionMaintainJob() {
        return jobBuilderFactory.get(TODAY_EXPRESSION_MAINTAIN_JOB_NAME)
                .start(todayExpressionMaintainStep())
                .build();
    }

    @Bean
    @JobScope
    public Step todayExpressionMaintainStep() {
        return stepBuilderFactory.get("todayExpressionMaintainStep")
                .tasklet((contribution, context) -> {
                    todayUsefulExpressionMaintainService.maintain();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}