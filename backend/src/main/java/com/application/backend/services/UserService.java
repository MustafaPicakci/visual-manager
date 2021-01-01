package com.application.backend.services;

import org.springframework.transaction.annotation.Transactional;

import com.application.backend.models.User;

public interface UserService {

  public abstract User loadUserById(long Id);

  public abstract void changeUserName(long userId, String username);

  @Transactional
  public abstract void changeProfilePhoto(User user);

  public abstract void updateResetPasswordToken(String token, String email) throws Exception;

  public abstract User getByResetPasswordToken(String token);

  public abstract void updatePassword(User user, String newPassword);
}
