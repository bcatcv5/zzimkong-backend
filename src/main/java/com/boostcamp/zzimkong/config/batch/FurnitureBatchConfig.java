package com.boostcamp.zzimkong.config.batch;

import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
import com.boostcamp.zzimkong.repository.modelresult.FurnitureResultRepository;
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
public class FurnitureBatchConfig {

    private final FurnitureResultRepository furnitureResultRepository;
    private final PlatformTransactionManager transactionManager;
    private final EmailService emailService;

    @Bean
    public Job finishFurnitureJob(JobRepository jobRepository) {
        return new JobBuilder("finishFurnitureJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(finishFurnitureStep(jobRepository))
                .end()
                .build();
    }

    @Bean
    public Step finishFurnitureStep(JobRepository jobRepository) {
        return new StepBuilder("finishFurnitureStep", jobRepository)
                .<FurnitureModelResult, FurnitureModelResult> chunk(100, transactionManager)
                .reader(readerFurniture())
                .writer(writerFurniture())
                .build();
    }

    @Bean
    public ItemReader<FurnitureModelResult> readerFurniture() {
        RepositoryItemReader<FurnitureModelResult> reader = new RepositoryItemReader<>();
        reader.setRepository(furnitureResultRepository);
        reader.setMethodName("findByStatusPushed");
        reader.setArguments(Collections.singletonList(false));
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        reader.setPageSize(100);
        return reader;
    }

    @Bean
    public ItemWriter<FurnitureModelResult> writerFurniture() {
        return items -> {
            furnitureResultRepository.updateStatusPushed(false);
            for (FurnitureModelResult item : items) {
                System.out.println("Found: " + item);
                emailService.sendMail(
                        item.getUser().getEmail(),
                        item.getUploadFileName(),
                        item.getStatusMessage(),
                        item.getStatusCode().toString()
                );
            }
        };
    }
}
