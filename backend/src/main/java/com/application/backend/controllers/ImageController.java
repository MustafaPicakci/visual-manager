package com.application.backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;
import com.application.backend.models.User;
import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.ImageServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/images")
public class ImageController {

  @Autowired ImageServiceImpl imageServiceImpl;
  @Autowired UserDetailsServiceImpl userDetailsServiceImpl;

  @PostMapping("/add")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public Images add(MultipartFile file) {
    return imageServiceImpl.saveImage(file);
  };

  @GetMapping("/list")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public List<Images> listImages() {
    User user = userDetailsServiceImpl.loadUser();
    return imageServiceImpl.findImagesForUser(user);
  };

  @PostMapping("/set/tag")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public Images setTag(Tags data) {

   return imageServiceImpl.setTag(data.getImages().get(0).getId(), data.getTagName());
  };

  @PostMapping("/delete")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void deleteImage(long imageId) {
    imageServiceImpl.deleteImage(imageId);
  };

  @PostMapping("/unlinkTag")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void unlinkTag(long imageId, long tagId) {
    imageServiceImpl.unlikTag(imageId, tagId);
  };
}
