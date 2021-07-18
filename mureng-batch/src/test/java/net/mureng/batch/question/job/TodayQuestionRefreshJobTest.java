package net.mureng.batch.question.job;

import net.mureng.CoreBasePackage;
import net.mureng.batch.AbstractJobTest;
import net.mureng.batch.core.config.TestBatchConfig;
import net.mureng.batch.push.service.FcmDailyPushService;
import net.mureng.core.question.service.TodayQuestionSelectionService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TodayQuestionRefreshJobConfig.class, TestBatchConfig.class})
class TodayQuestionRefreshJobTest extends AbstractJobTest {
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