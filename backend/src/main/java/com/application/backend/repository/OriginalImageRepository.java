package com.application.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.backend.models.OriginalImages;

@Repository
public interface OriginalImageRepository extends JpaRepository<OriginalImages, Long> {}
