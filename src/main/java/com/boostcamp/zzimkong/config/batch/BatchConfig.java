package com.boostcamp.zzimkong.config.batch;

import com.boostcamp.zzimkong.domain.StatusCode;
import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
import com.boostcamp.zzimkong.repository.modelresult.FurnitureResultRepository;
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
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final FurnitureResultRepository furnitureResultRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job finishJob(JobRepository jobRepository) {
        return new JobBuilder("finishJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(finishStep(jobRepository))
                .end()
                .build();
    }

    @Bean
    public Step finishStep(JobRepository jobRepository) {
        return new StepBuilder("finishStep", jobRepository)
                .<FurnitureModelResult, FurnitureModelResult> chunk(10, transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<FurnitureModelResult> reader() {
        RepositoryItemReader<FurnitureModelResult> reader = new RepositoryItemReader<>();
        reader.setRepository(furnitureResultRepository);
        reader.setMethodName("findByStatusPushed");
        reader.setArguments(Collections.singletonList(false));
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        reader.setPageSize(10);
        return reader;
    }

    @Bean
    public ItemWriter<FurnitureModelResult> writer() {
        return items -> {
            furnitureResultRepository.updateStatusPushed(false);
            //TODO: item에서 유저의 이메일을 하나씩 꺼내서 메일 서비스로 보내고 메일 서비스는 이메일만 받아서 이멜 전송하기
            for (FurnitureModelResult item : items) {
                System.out.println("Found: " + item);
            }
        };
    }
}
