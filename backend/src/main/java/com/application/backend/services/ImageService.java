package com.application.backend.services;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.application.backend.models.Images;
import com.application.backend.models.User;

public interface ImageService {
  public abstract void saveImage(MultipartFile file);

  public abstract List<Images> findImagesForUser(User user);

  public abstract void setTag(long imageId, String tagName);

  public abstract void deleteImage(long imageId);

  public abstract void unlikTag(long imageId, long tagId);
}
