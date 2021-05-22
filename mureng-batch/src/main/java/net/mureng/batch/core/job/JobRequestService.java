package net.mureng.batch.core.job;

import net.mureng.batch.core.job.JobRequest;

/**
 * 이 인터페이스를 구현하여 빈으로 등록해두면,
 * {@link net.mureng.batch.BatchStarter}에 의해 자동으로 등록됨
 *
 * @see net.mureng.batch.BatchStarter
 */
public interface JobRequestService {
    JobRequest create();
}
