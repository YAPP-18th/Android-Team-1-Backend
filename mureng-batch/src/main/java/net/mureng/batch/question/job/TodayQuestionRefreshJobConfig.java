package net.mureng.batch.question.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.config.AbstractJobConfig;
import net.mureng.batch.core.job.MurengJobLauncher;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TodayQuestionRefreshJobConfig extends AbstractJobConfig {
    public static final String TODAY_QUESTION_REFRESH_JOB_NAME = "todayQuestionRefreshJob";
    public static final String TODAY_QUESTION_REFRESH_JOB_LAUNCHER_NAME = TODAY_QUESTION_REFRESH_JOB_NAME + "Launcher";
    public static final String TODAY_QUESTION_REFRESH_JOB_CRON = "0 0 0 ? * * *";

    private final TodayQuestionSelectionService todayQuestionSelectionService;

    @Bean(name = TODAY_QUESTION_REFRESH_JOB_LAUNCHER_NAME)
    public MurengJobLauncher todayQuestionRefreshJobLauncher(JobLauncher jobLauncher) {
        return new MurengJobLauncher(todayQuestionRefreshJob(), jobLauncher);
    }

    @Bean(name = TODAY_QUESTION_REFRESH_JOB_NAME)
    public Job todayQuestionRefreshJob() {
        return jobBuilderFactory.get(TODAY_QUESTION_REFRESH_JOB_NAME)
                .start(todayQuestionRefreshStep())
                .build();
    }

    @Bean
    @JobScope
    public Step todayQuestionRefreshStep() {
        return stepBuilderFactory.get("todayQuestionRefreshStep")
                .<Member, Member>chunk(1000)
                .reader(todayQuestionRefreshReader())
                .writer(todayQuestionRefreshWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Member> todayQuestionRefreshReader() {
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