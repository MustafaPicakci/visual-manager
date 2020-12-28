package com.application.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.repository.ImageRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("api/profile")
public class ProfileController {
	@Autowired ImageRepository a;
	
	@GetMapping("/totalImages")
	public long totalImage(long userId) {
	return a.countByUserId(userId);
	}
}
