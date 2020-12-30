package com.application.backend.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.backend.models.User;
import com.application.backend.payload.response.JwtResponse;
import com.application.backend.repository.UserRepository;
import com.application.backend.security.jwt.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired UserRepository userRepository;
  @Autowired PasswordEncoder encoder;
  @Autowired AuthenticationManager authenticationManager;
  @Autowired JwtUtils jwtUtils;

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

  public void changeProfilePhoto(User user) {

    userRepository.changeProfilePhoto(user.getId(), user.getProfilePhoto());
  }

  public void updateResetPasswordToken(String token, String email) throws Exception {
    User user = userRepository.findByEmail(email);
    if (user != null) {
      user.setResetPasswordToken(token);
      userRepository.save(user);
    } else {
      // throw new CustomerNotFoundException("Could not find any customer with the email " + email);

      throw new Exception("bu maile sahip bir kullanıcı bulunamadı");
    }
  }

  public User getByResetPasswordToken(String token) {
    return userRepository.findByResetPasswordToken(token);
  }

  public void updatePassword(User user, String newPassword) {
    String encodedPassword = encoder.encode(newPassword);

    user.setPassword(encodedPassword);

    user.setResetPasswordToken(null);
    userRepository.save(user);
  }

  public ResponseEntity<?> AuthenticateUser(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));

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
            userDetails.getProfilePhoto(),
            roles));
  }
}
