package com.application.backend.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.models.User;
import com.application.backend.payload.response.JwtResponse;
import com.application.backend.security.jwt.JwtUtils;
import com.application.backend.security.services.UserDetailsImpl;
import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.ImageServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired UserDetailsServiceImpl userDetailServiceImpl;
  @Autowired ImageServiceImpl imageServiceImpl;
  @Autowired AuthenticationManager authenticationManager;
  @Autowired JwtUtils jwtUtils;

  @PostMapping("/change/username")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<JwtResponse> changeUserName(User user) {

    userDetailServiceImpl.changeUserName(user.getId(), user.getUsername());

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    Long expirationDate = jwtUtils.getExpitarionDate();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles =
        userDetails
            .getAuthorities()
            .stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(
        new JwtResponse(
            expirationDate,
            jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @GetMapping("/totalImages")
  public long totalImage(@RequestParam long userId) {
    return imageServiceImpl.totalImages(userId);
  }
}
