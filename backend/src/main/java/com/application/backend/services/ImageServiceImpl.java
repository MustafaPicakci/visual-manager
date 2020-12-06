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
import com.application.backend.models.User;
import com.application.backend.repository.ImageRepository;
import com.application.backend.repository.UserRepository;

@Service
public class ImageServiceImpl implements ImageService {
  @Autowired ImageRepository imageRepository;
  @Autowired UserRepository userRepository;

  @Override
  public void SaveImage(MultipartFile file) {
    try {
      //  System.out.println(file.getContentType());
      //  System.out.println(file.getSize());

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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public List<Images> FindImagesForUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    User user = userRepository.findByUsername(username);

    return imageRepository.findAllByUserId(user.getId());
  }
}
