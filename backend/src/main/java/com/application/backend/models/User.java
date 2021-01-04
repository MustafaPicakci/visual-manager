package com.application.backend.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email")
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String email;

  private String password;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private ConfirmationToken confirmationToken;

  @Lob
  @Column(nullable = true)
  private byte[] profilePhoto;

  private boolean isActive = false;

  @Column(name = "reset_password_token")
  private String resetPasswordToken;

  @OneToMany(targetEntity = Images.class, mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Images> images = new HashSet<>();;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User() {}

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public String getResetPasswordToken() {
    return resetPasswordToken;
  }

  public void setResetPasswordToken(String resetPasswordToken) {
    this.resetPasswordToken = resetPasswordToken;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public Long getId() {
    return id;
  }

  public byte[] getProfilePhoto() {
    return profilePhoto;
  }

  public void setProfilePhoto(byte[] profilePhoto) {
    this.profilePhoto = profilePhoto;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((images == null) ? 0 : images.hashCode());
    result = prime * result + (isActive ? 1231 : 1237);
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + Arrays.hashCode(profilePhoto);
    result = prime * result + ((resetPasswordToken == null) ? 0 : resetPasswordToken.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    User other = (User) obj;
    if (email == null) {
      if (other.email != null) return false;
    } else if (!email.equals(other.email)) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (images == null) {
      if (other.images != null) return false;
    } else if (!images.equals(other.images)) return false;
    if (isActive != other.isActive) return false;
    if (password == null) {
      if (other.password != null) return false;
    } else if (!password.equals(other.password)) return false;
    if (!Arrays.equals(profilePhoto, other.profilePhoto)) return false;
    if (resetPasswordToken == null) {
      if (other.resetPasswordToken != null) return false;
    } else if (!resetPasswordToken.equals(other.resetPasswordToken)) return false;
    if (roles == null) {
      if (other.roles != null) return false;
    } else if (!roles.equals(other.roles)) return false;
    if (username == null) {
      if (other.username != null) return false;
    } else if (!username.equals(other.username)) return false;
    return true;
  }
}
