package com.application.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.backend.models.Images;
import com.application.backend.models.User;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {

  List<Images> findAllByUser(User user);

  boolean existsByTagsId(long tagId);
}
