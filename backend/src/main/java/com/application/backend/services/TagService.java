package com.application.backend.services;

import java.util.List;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;

public interface TagService {
	public abstract void deleteTag(long tagId);

	public abstract List<Tags> ListImageTags(Images image);

	public void deleteUserTag(long userId);

}
