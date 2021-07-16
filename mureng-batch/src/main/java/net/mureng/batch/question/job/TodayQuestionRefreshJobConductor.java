package net.mureng.batch.question.job;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.*;
import net.mureng.batch.util.CronExpression;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class TodayQuestionRefreshJobConductor extends AbstractJobConductor {
    public static final String TODAY_QUESTION_REFRESH_JOB_NAME = "todayQuestionRefreshJob";
    public static final String TODAY_QUESTION_REFRESH_JOB_LAUNCHER_NAME = TODAY_QUESTION_REFRESH_JOB_NAME + "Launcher";
    public static final String TODAY_QUESTION_REFRESH_JOB_CRON = CronExpression.EVERY_DAY_00_AM;

    private TodayQuestionSelectionService todayQuestionSelectionService;

    @Autowired
    public void setTodayQuestionSelectionService(TodayQuestionSelectionService todayQuestionSelectionService) {
        this.todayQuestionSelectionService = todayQuestionSelectionService;
    }

    @Override
    public String getJobLauncherName() {
        return TODAY_QUESTION_REFRESH_JOB_LAUNCHER_NAME;
    }

    @Override
    protected String getJobName() {
        return TODAY_QUESTION_REFRESH_JOB_NAME;
    }

    @Override
    protected String getCronExpression() {
        return TODAY_QUESTION_REFRESH_JOB_CRON;
    }

    @Override
    protected Job getJob() {
        return jobBuilderFactory.get(getJobName())
                .start(todayQuestionRefreshStep())
                .build();
    }

    private Step todayQuestionRefreshStep() {
        return stepBuilderFactory.get("todayQuestionRefreshStep")
                .<Member, Member>chunk(1000)
                .reader(todayQuestionRefreshReader())
                .writer(todayQuestionRefreshWriter())
                .build();
    }

    private JpaPagingItemReader<Member> todayQuestionRefreshReader() {
        return new JpaPagingItemReaderBuilder<Member>()
                .name("todayQuestionRefreshReader")
                .entityManagerFactory(emf)
                .pageSize(1000)
                .queryString("SELECT m FROM Member m")
                .build();
    }

    private ItemWriter<Member> todayQuestionRefreshWriter() {
        return members -> {
            log.info(">>>>>>>>>>> Item Write");
            for (Member member : members) {
                todayQuestionSelectionService.reselectTodayQuestion(member);
            }
        };
    }
}
