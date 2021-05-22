package net.mureng.batch.util;

import net.mureng.batch.core.job.MurengJobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class JobLauncherUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        // TODO Auto-generated method stub
        context = applicationContext;
    }

    public static MurengJobLauncher getBeanName(String beanName) {
        return context.getBean(beanName, MurengJobLauncher.class);
    }
}

