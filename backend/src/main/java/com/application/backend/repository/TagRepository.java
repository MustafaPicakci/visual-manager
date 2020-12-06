package com.application.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.backend.models.Tags;

@Repository
public interface TagRepository extends JpaRepository<Tags, Long> {
	Tags findTagsByTagName(String tagName);
}
