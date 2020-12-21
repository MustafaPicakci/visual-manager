package com.application.backend.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.ImagePage;
import com.application.backend.models.Images;
import com.application.backend.models.User;

public interface ImageService {
  public abstract Images saveImage(MultipartFile file, byte[] imageBytes);

  public abstract List<Images> findImagesForUser(User user);

  public abstract Page<Images> findImagesForUser(User user, ImagePage imagePage);

  public abstract Page<Images> findImagesForUserWithTag(String tag, User user, ImagePage imagePage);

  public abstract Page<Images> findImagesForUserAndTagsIsNull(User user, ImagePage imagePage);

  public abstract Images setTag(long imageId, String tagName);

  public abstract void deleteImage(long imageId);

  public abstract Images unlikTag(long imageId, long tagId);

  public byte[] resizeImage(MultipartFile file);
}
