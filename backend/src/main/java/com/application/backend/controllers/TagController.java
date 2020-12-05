package com.application.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.models.Tags;
import com.application.backend.repository.TagRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tag")
public class TagController {

  @Autowired TagRepository tagRepository;

  @PostMapping("/newTag")
  public void newTag(Tags tag) {
    tagRepository.save(tag);
  }

  @PostMapping("/getTags")
  public List<Tags> getTags() {
    return tagRepository.findAll();
  }
  @GetMapping("/deleteTag")
  public void deleteTag() {
    tagRepository.deleteAll();
  }
}
