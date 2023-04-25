package com.raghunathit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raghunathit.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

	
}
