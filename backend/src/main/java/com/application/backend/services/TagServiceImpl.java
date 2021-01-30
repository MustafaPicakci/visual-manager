package com.application.backend.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;
import com.application.backend.repository.TagRepository;
import com.application.backend.repository.UserRepository;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	TagRepository tagRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public void deleteTag(long tagId) {
		tagRepository.deleteById(tagId);
	}

	@Override
	public List<Tags> ListImageTags(Images image) {

		return tagRepository.findTagsByimages(image);
	}

	@Override
	public void deleteUserTag(long userId) {
		/*
		 * etiketi kullanan tek bir görsel var ise kullanıcı ile birlikte etiket de
		 * siliniyor
		 */
		boolean deletable = false;
		Set<Tags> userTags = tagRepository.findTagsByImages_User_Id(userId);
		long tagId = 0;
		for (Tags tag : userTags) {
			tagId = tag.getId();
			for (Images image : tag.getImages()) {
				if (image.getUser().getId() == userId) {
					deletable = true;
				} else {
					deletable = false;
					break;
				}
			}
			if (deletable) {
				tagRepository.deleteById(tagId);
			}
		}
	}
}
