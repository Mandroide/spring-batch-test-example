package com.example.job;

import com.example.model.ModelInputEntity;
import com.example.model.ModelOutputEntity;
import com.example.repository.ModelInputRepository;
import com.example.repository.ModelOutputRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@SpringBatchTest
@SpringBootTest
@ActiveProfiles({"test"})
class ModelJobConfigurationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private Job modelJob;
    @MockBean
    private ModelInputRepository modelInputRepository;
    @MockBean
    private ModelOutputRepository modelOutputRepository;

    @BeforeEach
    void setUp() {
        jobLauncherTestUtils.setJob(modelJob);
    }

    @AfterEach
    void tearDown() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void runJob() throws Exception {
        Mockito.when(modelInputRepository.findAllByJobId(ArgumentMatchers.any(UUID.class),
                ArgumentMatchers.any(Pageable.class))).thenReturn(
                new PageImpl<>(List.of(
                        ModelInputEntity.builder().code("CODE1")
                                .build(),
                        ModelInputEntity.builder().code("CODE2")
                                .build()
                ))
        );

        Mockito.when(modelOutputRepository.saveAll(ArgumentMatchers.any())).thenReturn(List.of(ModelOutputEntity.builder().build()));

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParametersBuilder().toJobParameters());

        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}