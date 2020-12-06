package com.application.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.repository.TagRepository;
import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.TagServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tags")
public class TagController {

  @Autowired TagRepository tagRepository;
  @Autowired TagServiceImpl tagServiceImpl;
  @Autowired UserDetailsServiceImpl userDetailsServiceImpl;

  @PostMapping("/delete")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void deleteTag(long tagId) {
    tagServiceImpl.deleteTag(tagId);
  };
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
