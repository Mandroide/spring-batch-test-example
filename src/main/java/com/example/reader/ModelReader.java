package com.example.reader;

import com.example.model.ModelInputEntity;
import com.example.repository.ModelInputRepository;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModelReader extends RepositoryItemReader<ModelInputEntity> {

    public ModelReader(ModelInputRepository modelInputRepository, String jobId, int pageSize) {
        setRepository(modelInputRepository);
        setMethodName("findAllByJobId");
        setArguments(List.of(UUID.fromString(jobId)));
        setSort(Map.of("id", Sort.Direction.ASC));
        setPageSize(pageSize);
    }
}
