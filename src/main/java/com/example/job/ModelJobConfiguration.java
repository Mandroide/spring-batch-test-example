package com.example.job;

import com.example.model.ModelInputEntity;
import com.example.model.ModelOutputEntity;
import com.example.processor.ModelProcessor;
import com.example.reader.ModelReader;
import com.example.repository.ModelInputRepository;
import com.example.repository.ModelOutputRepository;
import com.example.writer.ModelWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;

@Configuration
public class ModelJobConfiguration {

    @Bean
    public Job modelJob(JobRepository jobRepository,
                        Step modelStep){
        return new JobBuilder("ModelJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(modelStep)
                .preventRestart()
                .build();
    }

    @JobScope
    @Bean
    public Step modelStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                          @Value("${batch.chunk-size}") int chunkSize,
                          ModelInputRepository modelInputRepository,
                          ModelOutputRepository modelOutputRepository) {
        return new StepBuilder("ModelStep1", jobRepository)
                .<ModelInputEntity, ModelOutputEntity>chunk(chunkSize, transactionManager)
                .reader(new ModelReader(modelInputRepository, UUID.randomUUID().toString(), chunkSize))
                .processor(new ModelProcessor())
                .writer(new ModelWriter(modelOutputRepository))
                .build();
    }
}
