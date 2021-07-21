package net.mureng.batch.core.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mureng.batch.core.job.JobRequestService;
import net.mureng.batch.core.job.MurengJobLauncher;
import net.mureng.batch.core.job.MurengJobLauncherProvider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfig {
    private final GenericApplicationContext applicationContext;
    private final List<MurengJobLauncherProvider> murengJobLauncherProviders;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @PostConstruct
    public void init() {
        for (MurengJobLauncherProvider provider : murengJobLauncherProviders) {
            applicationContext.registerBean(provider.getJobLauncherName(), MurengJobLauncher.class, provider::getLauncher);
            applicationContext.registerBean(provider.getLauncher().getJob().getName(), Job.class, () -> provider.getLauncher().getJob());
        }
    }

}