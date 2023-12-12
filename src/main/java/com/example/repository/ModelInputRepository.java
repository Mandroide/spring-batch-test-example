package com.example.repository;

import com.example.model.ModelInputEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModelInputRepository extends JpaRepository<ModelInputEntity, Long> {
    Page<ModelInputEntity> findAllByJobId(UUID jobId, Pageable pageable);
}
