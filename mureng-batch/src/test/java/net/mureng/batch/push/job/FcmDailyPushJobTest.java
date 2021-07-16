package net.mureng.batch.push.job;

import net.mureng.batch.AbstractJobTest;
import net.mureng.batch.core.config.TestBatchConfig;
import net.mureng.batch.push.service.FcmDailyPushService;
import net.mureng.batch.question.job.TodayQuestionRefreshJobConfig;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {FcmDailyPushJobConfig.class, TestBatchConfig.class})
class FcmDailyPushJobTest extends AbstractJobTest {
    @MockBean
    private FcmDailyPushService fcmDailyPushService;

    @BeforeEach
    public void clearMetadata() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void 오늘의_표현_새로고침_테스트() throws Exception {
        // given
        JobParameters jobParameters =
                jobLauncherTestUtils.getUniqueJobParameters();

        // when
        JobExecution jobExecution =
                jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(ExitStatus.COMPLETED,
                jobExecution.getExitStatus());
    }
}