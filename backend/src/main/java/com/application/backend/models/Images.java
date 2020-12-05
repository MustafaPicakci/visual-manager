package com.application.backend.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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

  public void setImage_id(long image_id) {
    this.image_id = image_id;
  }

  @Column(nullable = true)
  private String image_name;

  @Column(nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date upload_date;

  @Lob
  @Column(length = Integer.MAX_VALUE, nullable = true)
  private byte[] image;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "image_tags",
      joinColumns = @JoinColumn(name = "image_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private List<Tags> tags = new ArrayList<>();

  public List<Tags> getTags() {
    return tags;
  }

  public void setTags(List<Tags> tags) {
    this.tags = tags;
  }

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public long getImage_id() {
    return image_id;
  }

  public void addTag(Tags tag) {
    tags.add(tag);
    tag.getImages().add(this);
  }

  public void removeTag(Tags tag) {
    tags.remove(tag);
    tag.getImages().remove(this);
  }

  public Images() {}

  public Images(String image_name, Date upload_date, byte[] image, User user) {
    this.image_name = image_name;
    this.upload_date = upload_date;
    this.image = image;
    this.user = user;
  }

  public Images(String image_name, Date upload_date, byte[] image) {
    this.image_name = image_name;
    this.upload_date = upload_date;
    this.image = image;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(image);
    result = prime * result + (int) (image_id ^ (image_id >>> 32));
    result = prime * result + ((image_name == null) ? 0 : image_name.hashCode());
    result = prime * result + ((tags == null) ? 0 : tags.hashCode());
    result = prime * result + ((upload_date == null) ? 0 : upload_date.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Images other = (Images) obj;
    if (!Arrays.equals(image, other.image)) return false;
    if (image_id != other.image_id) return false;
    if (image_name == null) {
      if (other.image_name != null) return false;
    } else if (!image_name.equals(other.image_name)) return false;
    if (tags == null) {
      if (other.tags != null) return false;
    } else if (!tags.equals(other.tags)) return false;
    if (upload_date == null) {
      if (other.upload_date != null) return false;
    } else if (!upload_date.equals(other.upload_date)) return false;
    return true;
  }
}
