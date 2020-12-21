package com.application.backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.ImagePage;
import com.application.backend.models.Images;
import com.application.backend.models.Tags;
import com.application.backend.models.User;
import com.application.backend.repository.ImageRepository;
import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.ImageServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/images")
public class ImageController {

  @Autowired ImageServiceImpl imageServiceImpl;
  @Autowired UserDetailsServiceImpl userDetailsServiceImpl;
  @Autowired ImageRepository imageRepository;

  @PostMapping("/add")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public Images add(MultipartFile file) {
    return imageServiceImpl.saveImage(file);
  };

  /*@GetMapping("/list")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Images> listImages() {
      User user = userDetailsServiceImpl.loadUser();
      return imageServiceImpl.findImagesForUser(user);
    };
  */
  @GetMapping("/list")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Page<Images>> listImages(
      @RequestParam int pageNumber, @RequestParam int pageSize) {

    try {
      ImagePage imagePage = new ImagePage();
      imagePage.setPageNumber(pageNumber);
      imagePage.setPageSize(pageSize);
      System.out.println(pageNumber + "--" + pageSize);

      User user = userDetailsServiceImpl.loadUser();

      return new ResponseEntity<>(
          imageServiceImpl.findImagesForUserAndTagsIsNull(user, imagePage), HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  };

  @GetMapping("/getAll")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<Page<Images>> getAll(
      @RequestParam(required = false) String tag,
      @RequestParam int pageNumber,
      @RequestParam int pageSize) {

    try {
      ImagePage imagePage = new ImagePage();
      imagePage.setPageNumber(pageNumber);
      imagePage.setPageSize(pageSize);
      System.out.println(pageNumber + "--" + pageSize);

      User user = userDetailsServiceImpl.loadUser();

      if (tag == null) {
        return new ResponseEntity<>(
            imageServiceImpl.findImagesForUser(user, imagePage), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(
            imageServiceImpl.findImagesForUserWithTag(tag, user, imagePage), HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  };

  @PostMapping("/set/tag")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public Images setTag(Tags data) {

    return imageServiceImpl.setTag(data.getImages().get(0).getId(), data.getTagName());
  };

  @PostMapping("/delete")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void deleteImage(Images data) {

    imageServiceImpl.deleteImage(data.getId());
  };

  @PostMapping("/unlinkTag")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public Images unlinkTag(Tags data) {
    return imageServiceImpl.unlikTag(data.getImages().get(0).getId(), data.getId());
  };
}
