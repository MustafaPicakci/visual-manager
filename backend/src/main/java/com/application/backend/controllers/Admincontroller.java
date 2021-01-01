package com.application.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.services.UserServiceImpl;
import com.application.backend.models.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class Admincontroller {

  @Autowired UserServiceImpl userServiceImpl;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("list/users")
  public List<User> listUsers() {
    return userServiceImpl.ListUsers();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("block/user")
  public void blockUser(long Id) {
    userServiceImpl.blockUser(Id);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("unblock/user")
  public void unBlockUser(@RequestParam long Id) {
    userServiceImpl.unBlockUser(Id);
  }
}
