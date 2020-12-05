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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tags")
public class Tags {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private long tag_id;

  @Column(unique = true)
  private String tag_name;

  @JsonIgnore
  @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
  private List<Images> images = new ArrayList<>();

  public Tags() {}

  public Tags(String tag_name) {
    super();
    this.tag_name = tag_name;
  }

  public long getTag_id() {
    return tag_id;
  }

  public void setTag_id(long tag_id) {
    this.tag_id = tag_id;
  }

  public String getTag_name() {
    return tag_name;
  }

  public void setTag_name(String tag_name) {
    this.tag_name = tag_name;
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
    result = prime * result + (int) (tag_id ^ (tag_id >>> 32));
    result = prime * result + ((tag_name == null) ? 0 : tag_name.hashCode());
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
    if (tag_id != other.tag_id) return false;
    if (tag_name == null) {
      if (other.tag_name != null) return false;
    } else if (!tag_name.equals(other.tag_name)) return false;
    return true;
  }
}
