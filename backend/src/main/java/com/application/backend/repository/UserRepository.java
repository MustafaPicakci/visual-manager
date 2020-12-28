package com.application.backend.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.backend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  @Transactional
  @Modifying
  @Query("update User u set u.username = :username where u.id = :userId")
  void changeUserName(@Param("userId") long userId, @Param("username") String username);
  
  @Transactional
  @Modifying
  @Query("update User u set u.password = :password where u.id = :userId")
  void changePassword(@Param("userId") long userId, @Param("password") String password);

  @Transactional
  @Modifying
  @Query("update User u set u.profilePhoto = :profilePhoto where u.id = :userId")
  void changeProfilePhoto(@Param("userId") long userId, @Param("profilePhoto") byte[] profilePhoto);

  
}
