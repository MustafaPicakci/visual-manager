package com.application.backend.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity()
@Table(name = "OriginalImages")
public class OriginalImages {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "originalImage", cascade = CascadeType.ALL)
  private Images image;

  @Lob
  @Column(length = Integer.MAX_VALUE, nullable = true)
  private byte[] OriginalImage;

  public byte[] getOriginalImage() {
    return OriginalImage;
  }

  public void setOriginalImage(byte[] originalImage) {
    OriginalImage = originalImage;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Images getImage() {
    return image;
  }

  public void setImage(Images image) {
    this.image = image;
  }
}
