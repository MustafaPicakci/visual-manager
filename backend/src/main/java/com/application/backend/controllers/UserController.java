package com.application.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.models.User;
import com.application.backend.repository.UserRepository;
import com.application.backend.security.services.UserDetailsServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired UserDetailsServiceImpl userDetailServiceImpl;

  @PostMapping("/change/username")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<User> changeUserName(User user) {
    try {
      userDetailServiceImpl.changeUserName(user.getId(), user.getUsername());

      return new ResponseEntity<>(userDetailServiceImpl.loadUserById(user.getId()), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
