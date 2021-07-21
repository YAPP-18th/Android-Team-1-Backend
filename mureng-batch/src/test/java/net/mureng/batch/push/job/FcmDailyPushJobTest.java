package net.mureng.batch.push.job;

import net.mureng.batch.AbstractJobTest;
import net.mureng.batch.core.config.TestBatchConfig;
import net.mureng.batch.push.service.FcmDailyPushService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {FcmDailyPushJobConductor.class, TestBatchConfig.class})
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