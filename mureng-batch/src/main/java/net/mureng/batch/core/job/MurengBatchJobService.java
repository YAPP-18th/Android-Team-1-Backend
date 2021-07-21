package net.mureng.batch.core.job;

public interface MurengBatchJobService {
    MurengJobLauncherProvider getProvider();
    JobRequestService getJobRequestService();
}
