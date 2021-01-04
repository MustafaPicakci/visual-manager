package com.application.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;

@Repository
public interface TagRepository extends JpaRepository<Tags, Long> {
  Tags findTagsByTagName(String tagName);

  Tags findTagsById(long tagId);

  public List<Tags> findTagsByimages(Images image);
  public List<Tags> findTagsByImages_User_Id(long Id);
}
