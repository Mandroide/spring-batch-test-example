package com.example.processor;

import com.example.model.ModelInputEntity;
import com.example.model.ModelOutputEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;

public class ModelProcessor implements ItemProcessor<ModelInputEntity, ModelOutputEntity> {
    @Override
    public ModelOutputEntity process(@NonNull ModelInputEntity item) {
        ModelOutputEntity build = ModelOutputEntity.builder().jobId(item.getJobId()).data(item.getCode() + "DATA").build();
        return build;
    }
}
