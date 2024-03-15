package com.boostcamp.zzimkong.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MailScheduler {

    private final JobLauncher jobLauncher;
    private final Job finishFurnitureJob;
    private final Job finishSpaceJob;

    public MailScheduler(
            JobLauncher jobLauncher,
            @Qualifier("finishFurnitureJob") Job finishFurnitureJob,
            @Qualifier("finishSpaceJob") Job finishSpaceJob
    ) {
        this.jobLauncher = jobLauncher;
        this.finishFurnitureJob = finishFurnitureJob;
        this.finishSpaceJob = finishSpaceJob;
    }

    @Scheduled(fixedRate = 60000)
    public void runFurnitureJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(finishFurnitureJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 60000)
    public void runSpaceJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(finishSpaceJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
