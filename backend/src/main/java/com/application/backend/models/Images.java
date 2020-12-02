package com.application.backend.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "images")
public class Images {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long image_id;

  @Column(nullable = true)
  private String image_name;

  @Column(nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date upload_date;

  @Lob
  @Column(length = Integer.MAX_VALUE, nullable = true)
  private byte[] image;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public long getImage_id() {
    return image_id;
  }

  public Images() {}

  public Images(long image_id, String image_name, Date upload_date, byte[] image, User user) {
    this.image_id = image_id;
    this.image_name = image_name;
    this.upload_date = upload_date;
    this.image = image;
    this.user = user;
  }

  public void setImage_id(int image_id) {
    this.image_id = image_id;
  }

  public String getImage_name() {
    return image_name;
  }

  public void setImage_name(String image_name) {
    this.image_name = image_name;
  }

  public Date getUpload_date() {
    return upload_date;
  }

  public void setUpload_date(Date upload_date) {
    this.upload_date = upload_date;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
