package net.mureng.batch.core.job;

import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.util.JobLauncherUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;

/**
 * 배치 잡 등록을 간단하게 할 수 있는 유틸리티 클래스
 * 이 클래스를 상속 후 필요한 부분만 Override 만 한다면 간단하게 Job을 정의할 수 있다.
 * 주의할 점은 Job을 생성 및 실행하는 과정 때문에 인자가 없는 생성자가 포함되어야 하며,
 * 필요한 의존성은 Field 혹은 Setter 를 통해 주입되어야 함
 *
 * @see net.mureng.batch.core.config.AutowiringSpringBeanJobFactory
 */
@Slf4j
public abstract class AbstractJobConductor extends MurengQuartzJob
        implements JobRequestService, MurengJobLauncherProvider {
    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    @Autowired
    protected EntityManagerFactory emf;

    @Autowired
    protected JobLauncher jobLauncher;

    public abstract String getJobLauncherName();

    protected abstract Job getJob();

    protected abstract String getJobName();

    protected abstract String getCronExpression();

    @Override
    public MurengJobLauncher getLauncher() {
        return new MurengJobLauncher(getJob(), jobLauncher);
    }

    @Override
    public JobRequest create() {
        return CronJobRequest.builder()
                .jobName(getJobName())
                .jobClass(this.getClass())
                .jobDataMap(buildJobDataMap())
                .cronExpression(getCronExpression())
                .build();
    }

    private JobDataMap buildJobDataMap() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobName", getJobName());
        return jobDataMap;
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobLauncherUtil.getBeanName(getJobLauncherName()).execute();
    }
}
