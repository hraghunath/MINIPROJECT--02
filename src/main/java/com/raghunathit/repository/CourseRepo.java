package com.raghunathit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raghunathit.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity,Integer> {

}
