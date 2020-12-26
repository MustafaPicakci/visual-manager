package com.application.backend.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.backend.models.User;
import com.application.backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    return UserDetailsImpl.build(user);
  }

  public User loadUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();
    return userRepository.findByUsername(userName);
  }

  public User loadUserById(long id) {

    return userRepository.findById(id).get();
  }

  @Transactional
  public void changeUserName(long userId, String username) {
    userRepository.changeUserName(userId, username);
  }
}
