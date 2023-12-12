package com.example.repository;

import com.example.model.ModelOutputEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ModelOutputRepository extends JpaRepository<ModelOutputEntity, Long> {
}
