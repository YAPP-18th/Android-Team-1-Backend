package net.mureng.batch.core.job;

import org.quartz.InterruptableJob;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class MurengQuartzJob extends QuartzJobBean implements InterruptableJob {
}
