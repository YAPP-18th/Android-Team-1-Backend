package net.mureng.batch.core.job;

import org.springframework.batch.core.Job;

/**
 * 이 인터페이스를 구현해서 bean으로 등록하면 자동으로 등록된다.
 * @see net.mureng.batch.core.config.BatchConfig
 */
public interface MurengJobLauncherProvider {
    /**
     * 이 이름으로 bean이 등록될 것임
     * @return 등록될 jobLauncher bean 이름
     */
    String getJobLauncherName();

    /**
     * 이 MurengJobJauncher가 bean으로 등록될 것임
     * @return 등록될 jobLauncher
     */
    MurengJobLauncher getLauncher();
}
