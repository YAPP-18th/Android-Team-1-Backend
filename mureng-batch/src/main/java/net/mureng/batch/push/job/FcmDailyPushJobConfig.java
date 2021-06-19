package net.mureng.batch.push.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.config.AbstractJobConfig;
import net.mureng.batch.core.job.MurengJobLauncher;
import net.mureng.batch.push.service.FcmDailyPushService;
import net.mureng.core.member.entity.Member;
import net.mureng.push.dto.NotificationRequest;
import net.mureng.push.service.FcmService;
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
public class FcmDailyPushJobConfig extends AbstractJobConfig {
    public static final String FCM_DAILY_PUSH_JOB_NAME = "fcmDailyPushJob";
    public static final String FCM_DAILY_PUSH_JOB_LAUNCHER_NAME = FCM_DAILY_PUSH_JOB_NAME + "Launcher";
    public static final String FCM_DAILY_PUSH_JOB_JOB_CRON = "0 0 9 * * ?";

    private final FcmDailyPushService fcmDailyPushService;

    @Bean(name = FCM_DAILY_PUSH_JOB_LAUNCHER_NAME)
    public MurengJobLauncher fcmDailyPushJobLauncher(JobLauncher jobLauncher) {
        return new MurengJobLauncher(fcmDailyPushJob(), jobLauncher);
    }

    @Bean(name = FCM_DAILY_PUSH_JOB_NAME)
    public Job fcmDailyPushJob() {
        return jobBuilderFactory.get(FCM_DAILY_PUSH_JOB_NAME)
                .start(fcmDailyPushStep())
                .build();
    }

    @Bean
    @JobScope
    public Step fcmDailyPushStep() {
        return stepBuilderFactory.get("fcmDailyPushStep")
                .<Member, Member>chunk(1000)
                .reader(fcmDailyPushReader())
                .writer(todayQuestionRefreshWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Member> fcmDailyPushReader() {
        return new JpaPagingItemReaderBuilder<Member>()
                .name("fcmDailyPushReader")
                .entityManagerFactory(emf)
                .pageSize(1000)
                .queryString("SELECT m FROM Member m")
                .build();
    }

    private ItemWriter<Member> todayQuestionRefreshWriter() {
        return members -> {
            log.info(">>>>>>>>>>> Sending FCM");
            for (Member member : members) {
                fcmDailyPushService.pushToMember(member);
            }
        };
    }
}