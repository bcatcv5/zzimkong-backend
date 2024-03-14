package com.boostcamp.zzimkong.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MailScheduler {

    private final JobLauncher jobLauncher;
    private final Job finishJob;

    public MailScheduler(JobLauncher jobLauncher, Job finishJob) {
        this.jobLauncher = jobLauncher;
        this.finishJob = finishJob;
    }

    @Scheduled(fixedRate = 60000)
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(finishJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
