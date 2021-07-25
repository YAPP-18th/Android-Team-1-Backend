package net.mureng.batch.todayexpression.maintain.job;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.AbstractJobConductor;
import net.mureng.batch.todayexpression.maintain.service.TodayUsefulExpressionMaintainService;
import net.mureng.batch.util.CronExpression;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class TodayExpressionMaintainJobConductor extends AbstractJobConductor {
    public static final String TODAY_EXPRESSION_MAINTAIN_JOB_NAME = "todayExpressionMaintainJob";
    public static final String TODAY_EXPRESSION_MAINTAIN_JOB_LAUNCHER_NAME = TODAY_EXPRESSION_MAINTAIN_JOB_NAME + "Launcher";
    public static final String TODAY_EXPRESSION_MAINTAIN_JOB_CRON = CronExpression.EVERY_5_MINUTES;

    private TodayUsefulExpressionMaintainService todayUsefulExpressionMaintainService;

    @Autowired
    public void setTodayUsefulExpressionMaintainService(TodayUsefulExpressionMaintainService todayUsefulExpressionMaintainService) {
        this.todayUsefulExpressionMaintainService = todayUsefulExpressionMaintainService;
    }

    @Override
    public String getJobLauncherName() {
        return TODAY_EXPRESSION_MAINTAIN_JOB_LAUNCHER_NAME;
    }

    @Override
    protected String getJobName() {
        return TODAY_EXPRESSION_MAINTAIN_JOB_NAME;
    }

    @Override
    protected String getCronExpression() {
        return TODAY_EXPRESSION_MAINTAIN_JOB_CRON;
    }

    @Override
    protected Job getJob() {
        return jobBuilderFactory.get(TODAY_EXPRESSION_MAINTAIN_JOB_NAME)
                .start(todayExpressionMaintainStep())
                .build();
    }

    public Step todayExpressionMaintainStep() {
        return stepBuilderFactory.get("todayExpressionMaintainStep")
                .tasklet((contribution, context) -> {
                    log.info("test");
                    todayUsefulExpressionMaintainService.maintain();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
