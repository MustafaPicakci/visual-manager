package com.application.backend.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;

@Repository
public interface TagRepository extends JpaRepository<Tags, Long> {
	Tags findTagsByTagName(String tagName);

	Tags findTagsById(long tagId);

	void deleteByTagName(String tagName);

	List<Tags> findTagsByimages(Images image);

	Set<Tags> findTagsByImages_User_Id(long Id);
}
