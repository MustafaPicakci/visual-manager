package com.application.backend.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;
import com.application.backend.models.User;
import com.application.backend.repository.ImageRepository;
import com.application.backend.repository.TagRepository;
import com.application.backend.repository.UserRepository;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired ImageRepository imageRepository;
  @Autowired UserRepository userRepository;
  @Autowired TagRepository tagRepository;

  @Override
  public void saveImage(MultipartFile file) {
    try {
      byte[] imageFile = file.getBytes();
      Date uploadDate = new Date();
      String imageName = file.getOriginalFilename();
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();

      User user = userRepository.findByUsername(username);

      Images image = new Images();
      image.setImage(imageFile);
      image.setImageName(imageName);
      image.setUploadDate(uploadDate);
      image.setUser(user);
      imageRepository.save(image);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Images> findImagesForUser(User user) {
    return imageRepository.findAllByUser(user);
  }

  @Override
  public void setTag(long imageId, String tagName) {
    Images image = imageRepository.getOne(imageId);
    Tags tag = null;
    try {
      tag = tagRepository.findTagsByTagName(tagName);
    } catch (Exception e) {
      System.out.println("tagi bulma sorunlu");
    }
    if (tag == null) {
      tag = new Tags(tagName);
    }

    image.addTag(tag);

    imageRepository.save(image);
  }

  @Override
  public void deleteImage(long imageId) {
    imageRepository.deleteById(imageId);
  }

  @Override
  public void unlikTag(long imageId, long tagId) {
    Images image = imageRepository.getOne(imageId);
    Tags tag = tagRepository.findTagsById(tagId);
    image.removeTagFromImage(tag);
    imageRepository.save(image);
  }
}
