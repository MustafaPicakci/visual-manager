package com.application.backend.models;

import java.util.ArrayList;
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
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tags")
public class Tags {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private long id;

  @Column(unique = true)
  private String tagName;

  @JsonIgnore
  @ManyToMany(mappedBy = "tags")
  private List<Images> images = new ArrayList<>();

  @PreRemove
  private void preRemove() {
    images.forEach(image -> image.setTags(null));
  }

  public Tags() {}

  public Tags(String tagName) {
    super();
    this.tagName = tagName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public List<Images> getImages() {
    return images;
  }

  public void setImages(List<Images> images) {
    this.images = images;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((images == null) ? 0 : images.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Tags other = (Tags) obj;
    if (images == null) {
      if (other.images != null) return false;
    } else if (!images.equals(other.images)) return false;
    if (id != other.id) return false;
    if (tagName == null) {
      if (other.tagName != null) return false;
    } else if (!tagName.equals(other.tagName)) return false;
    return true;
  }
}
