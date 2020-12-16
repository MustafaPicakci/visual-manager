package com.application.backend.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;
import com.application.backend.repository.ImageRepository;
import com.application.backend.repository.TagRepository;
import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.TagServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tags")
public class TagController {
  @Autowired ImageRepository imageRepository;
  @Autowired TagRepository tagRepository;
  @Autowired TagServiceImpl tagServiceImpl;
  @Autowired UserDetailsServiceImpl userDetailsServiceImpl;

  /*@PostMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteTag(long tagId) {
      tagServiceImpl.deleteTag(tagId);
    };

  @PostMapping("/listImageTags")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public List<Tags> ListImageTags(Images image) {
    return tagServiceImpl.ListImageTags(image);
  };
  */
  /*
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @PostMapping("/list")
  public List<Tags> listTags() {
    return tagServiceImpl.findTagsForUser();
  }*/
  /*
   @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
   @GetMapping("/delete/all")
   public void deleteTag() {
  User user=userDetailsServiceImpl.loadUser();
     tagServiceImpl.deleteByUser(user);
   }*/
}
