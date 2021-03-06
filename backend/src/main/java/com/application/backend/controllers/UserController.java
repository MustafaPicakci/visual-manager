package com.application.backend.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.Tags;
import com.application.backend.models.User;
import com.application.backend.repository.ConfirmationTokenRepository;
import com.application.backend.repository.TagRepository;
import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.ImageServiceImpl;
import com.application.backend.services.UserServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserDetailsServiceImpl userDetailServiceImpl;
	@Autowired
	ImageServiceImpl imageServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	TagRepository tagRepository;

	@PostMapping("/change/username")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> changeUserName(User user) {

		userServiceImpl.changeUserName(user.getId(), user.getUsername());

		return userDetailServiceImpl.AuthenticateUser(user.getUsername(), user.getPassword());
	}

	@GetMapping("/totalImages")
	public long totalImage(@RequestParam long userId) {
		return imageServiceImpl.totalImages(userId);
	}

	@PostMapping("/change/profilePhoto")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> changeProfilePhoto(User user, MultipartFile file) {
		byte[] profilePhoto = null;
		try {
			profilePhoto = file.getBytes();
			user.setProfilePhoto(profilePhoto);
		} catch (Exception e) {
			System.out.println(e);
		}
		userServiceImpl.changeProfilePhoto(user);

		return userDetailServiceImpl.AuthenticateUser(user.getUsername(), user.getPassword());
	};

	@PostMapping("/change/password")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> changePassword(User user) {

		String password = null;
		password = user.getPassword();
		user = userServiceImpl.loadUserById(user.getId());

		userServiceImpl.updatePassword(user, password);
		return userDetailServiceImpl.AuthenticateUser(user.getUsername(), password);
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/delete/account")
	public void delete(@RequestBody User user) {
		userServiceImpl.deleteById(user.getId());
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping("/tags")
	public Set<Tags> getUserTags(@RequestBody User user) {
		return tagRepository.findTagsByImages_User_Id(user.getId());
	}
}
