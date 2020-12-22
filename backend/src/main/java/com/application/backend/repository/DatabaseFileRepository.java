package com.application.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.backend.models.DatabaseFile;
import com.application.backend.models.Images;

@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {
  DatabaseFile findByImage(Images image);
}
