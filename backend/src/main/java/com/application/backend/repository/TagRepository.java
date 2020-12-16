package com.application.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;

@Repository
public interface TagRepository extends JpaRepository<Tags, Long> {
  Tags findTagsByTagName(String tagName);

  Tags findTagsById(long tagId);
  public List<Tags> findTagsByimages(Images image);
  
  /*
  @Query("select count tag_id from image_tags where tag_id ")
  public boolean isUsed(
		  @Param("product_id") int product_id)
  /* List<Tags> findAllByUser(User user);

  void deleteByUser(User user);*/
}
