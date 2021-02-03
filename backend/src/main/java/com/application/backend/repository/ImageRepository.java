package com.application.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.backend.models.Images;
import com.application.backend.models.OriginalImageFile;
import com.application.backend.models.User;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {

	List<Images> findAllByUser(User user);

	Images findById(long id);

	Page<Images> findAllByUser(User user, Pageable pageable);

	Page<Images> findByTags_TagNameContainingAndUser(String tag, User user, Pageable pageable);

	Page<Images> findByUserAndTagsIsNull(User user, Pageable pageable);

	OriginalImageFile findOriginalImageFileById(long id);

	boolean existsByTagsId(long tagId);
	
	boolean existsByTags_tagName(String tagname);

	long countByUserId(long UserId);
}
