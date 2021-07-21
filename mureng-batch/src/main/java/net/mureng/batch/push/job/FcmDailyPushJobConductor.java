package net.mureng.batch.push.job;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.AbstractJobConductor;
import net.mureng.batch.push.service.FcmDailyPushService;
import net.mureng.batch.util.CronExpression;
import net.mureng.core.member.entity.FcmToken;
import net.mureng.core.member.entity.Member;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@NoArgsConstructor
public class FcmDailyPushJobConductor extends AbstractJobConductor {
    public static final String FCM_DAILY_PUSH_JOB_NAME = "fcmDailyPushJob";
    public static final String FCM_DAILY_PUSH_JOB_LAUNCHER_NAME = FCM_DAILY_PUSH_JOB_NAME + "Launcher";
    public static final String FCM_DAILY_PUSH_JOB_JOB_CRON = CronExpression.EVERY_DAY_09_PM;

    private FcmDailyPushService fcmDailyPushService;

    @Autowired
    public void setFcmDailyPushService(FcmDailyPushService fcmDailyPushService) {
        this.fcmDailyPushService = fcmDailyPushService;
    }

    @Override
    public String getJobLauncherName() {
        return FCM_DAILY_PUSH_JOB_LAUNCHER_NAME;
    }

    @Override
    protected String getJobName() {
        return FCM_DAILY_PUSH_JOB_LAUNCHER_NAME;
    }

    @Override
    protected String getCronExpression() {
        return FCM_DAILY_PUSH_JOB_JOB_CRON;
    }

    @Override
    protected Job getJob() {
        return jobBuilderFactory.get(FCM_DAILY_PUSH_JOB_NAME)
                .start(fcmDailyPushStep())
                .build();
    }

    public Step fcmDailyPushStep() {
        return stepBuilderFactory.get("fcmDailyPushStep")
                .<FcmToken, FcmToken>chunk(1000)
                .reader(fcmDailyPushReader())
                .writer(todayQuestionRefreshWriter())
                .build();
    }

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
