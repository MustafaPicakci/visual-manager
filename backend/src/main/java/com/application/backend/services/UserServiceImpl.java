package com.application.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.backend.models.User;
import com.application.backend.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  @Autowired PasswordEncoder encoder;
  @Autowired UserRepository userRepository;

  @Override
  public User loadUserById(long Id) {
    return userRepository.findById(Id).get();
  }

  @Override
  public void changeUserName(long userId, String username) {
    userRepository.changeUserName(userId, username);
  }

  @Override
  public void changeProfilePhoto(User user) {
    userRepository.changeProfilePhoto(user.getId(), user.getProfilePhoto());
  }

  @Override
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

  @Override
  public User getByResetPasswordToken(String token) {
    return userRepository.findByResetPasswordToken(token);
  }

  @Override
  public void updatePassword(User user, String newPassword) {
    String encodedPassword = encoder.encode(newPassword);

    user.setPassword(encodedPassword);

    user.setResetPasswordToken(null);
    userRepository.save(user);
  }

  @Override
  public List<User> ListUsers() {
    return userRepository.findAll();
  }

  @Override
  public void blockUser(long id) {
    User user = userRepository.findById(id).get();
    user.setActive(false);
    userRepository.save(user);
  }

  @Override
  public void unBlockUser(long id) {
    User user = userRepository.findById(id).get();
    user.setActive(true);
    userRepository.save(user);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
