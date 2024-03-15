package com.boostcamp.zzimkong.config.batch;

import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
import com.boostcamp.zzimkong.domain.space.SpaceModelResult;
import com.boostcamp.zzimkong.repository.modelresult.FurnitureResultRepository;
import com.boostcamp.zzimkong.repository.modelresult.SpaceResultRepository;
import com.boostcamp.zzimkong.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class SpaceBatchConfig {

    private final SpaceResultRepository spaceResultRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmailService emailService;

    @Bean
    public Job finishSpaceJob(JobRepository jobRepository) {
        return new JobBuilder("finishSpaceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(finishSpaceStep(jobRepository))
                .end()
                .build();
    }

    @Bean
    public Step finishSpaceStep(JobRepository jobRepository) {
        return new StepBuilder("finishSpaceStep", jobRepository)
                .<SpaceModelResult, SpaceModelResult> chunk(500, transactionManager)
                .reader(readerSpace())
                .writer(writerSpace())
                .build();
    }

    @Bean
    public ItemReader<SpaceModelResult> readerSpace() {
        RepositoryItemReader<SpaceModelResult> reader = new RepositoryItemReader<>();
        reader.setRepository(spaceResultRepository);
        reader.setMethodName("findByStatusPushed");
        reader.setArguments(Collections.singletonList(false));
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        reader.setPageSize(500);
        return reader;
    }

    @Bean
    public ItemWriter<SpaceModelResult> writerSpace() {
        return items -> {
            spaceResultRepository.updateStatusPushed(false);
            for (SpaceModelResult item : items) {
                System.out.println("Found: " + item);
                emailService.sendMail(
                        item.getEmail(),
                        item.getUploadFileName(),
                        item.getStatusMessage(),
                        item.getStatusCode().toString()
                );
            }
        };
    }
}
