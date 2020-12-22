package com.application.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.backend.models.OriginalImageFile;
import com.application.backend.models.Images;

@Repository
public interface OriginalImageFileRepopsitory extends JpaRepository<OriginalImageFile, String> {
  OriginalImageFile findByImage(Images image);
}
