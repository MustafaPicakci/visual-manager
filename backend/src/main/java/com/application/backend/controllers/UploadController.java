package com.application.backend.controllers;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.Images;
import com.application.backend.models.Tags;
import com.application.backend.repository.ImageRepository;
import com.application.backend.repository.TagRepository;
import com.application.backend.services.ImageServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/image")
public class UploadController {

  @Autowired ImageServiceImpl imageServiceImpl;
  @Autowired ImageRepository imageRepository;
  @Autowired TagRepository tagRepository;
  

  @PostMapping("/newImages")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void UploadImage(MultipartFile file) {
	  imageServiceImpl.SaveImage(file);
	  System.out.println("calisti");
  };

  @GetMapping("/all")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public List<Images> getImages() {
	  return imageServiceImpl.FindImagesForUser();
  };
  
  @PostMapping("/setTag")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void setTag(long image_id,String tag_name) {
	 Images image=imageRepository.getOne(image_id);
	 //Tags tag=new Tags(tag_name);
	// Tags tag2=new Tags("Mahmut");
	 long id=1;
	Tags tag= tagRepository.getOne(id);
	 image.addTag(tag);
	// image.addTag(tag2);
	
	
	 imageRepository.save(image);
  };
}
