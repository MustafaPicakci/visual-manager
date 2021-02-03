package com.application.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.repository.ImageRepository;
import com.application.backend.repository.TagRepository;
import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.TagServiceImpl;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tags")
public class TagController {
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	TagServiceImpl tagServiceImpl;
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

}
