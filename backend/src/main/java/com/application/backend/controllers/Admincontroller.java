package com.application.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.application.backend.services.UserServiceImpl;
import com.application.backend.models.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class Admincontroller {

  @Autowired UserServiceImpl userServiceImpl;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/list/users")
  public List<User> listUsers() {
    return userServiceImpl.ListUsers();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/block/user")
  public ResponseEntity<User> blockUser(@RequestBody User user) {

    userServiceImpl.blockUser(user.getId());
    return new ResponseEntity<>(userServiceImpl.loadUserById(user.getId()), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/unblock/user")
  public ResponseEntity<User> unBlockUser(@RequestBody User user) {
    userServiceImpl.unBlockUser(user.getId());
    return new ResponseEntity<>(userServiceImpl.loadUserById(user.getId()), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/delete/user")
  public void delete(@RequestBody User user) {
    userServiceImpl.deleteById(user.getId());
  }
}
