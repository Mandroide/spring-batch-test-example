package com.example.writer;

import com.example.model.ModelOutputEntity;
import com.example.repository.ModelOutputRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.jpa.repository.JpaRepository;

public class ModelWriter extends RepositoryItemWriter<ModelOutputEntity> {

    public ModelWriter(ModelOutputRepository modelOutputRepository) {
        setRepository(modelOutputRepository);
    }
}
