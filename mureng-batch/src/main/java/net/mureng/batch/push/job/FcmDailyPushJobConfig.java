package net.mureng.batch.push.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.config.AbstractJobConfig;
import net.mureng.batch.core.job.MurengJobLauncher;
import net.mureng.batch.push.service.FcmDailyPushService;
import net.mureng.batch.util.CronExpression;
import net.mureng.core.member.entity.FcmToken;
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
    public static final String FCM_DAILY_PUSH_JOB_JOB_CRON = CronExpression.EVERY_DAY_09_PM;

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
                .<FcmToken, FcmToken>chunk(1000)
                .reader(fcmDailyPushReader())
                .writer(todayQuestionRefreshWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<FcmToken> fcmDailyPushReader() {
        return new JpaPagingItemReaderBuilder<FcmToken>()
                .name("fcmDailyPushReader")
                .entityManagerFactory(emf)
                .pageSize(1000)
                .queryString("SELECT f FROM FcmToken f")
                .build();
    }

    private ItemWriter<FcmToken> todayQuestionRefreshWriter() {
        return fcmTokens -> {
            log.info(">>>>>>>>>>> Sending FCM");
            for (FcmToken fcmToken : fcmTokens) {
                fcmDailyPushService.push(fcmToken);
            }
        };
    }
}