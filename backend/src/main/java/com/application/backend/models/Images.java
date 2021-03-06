package com.application.backend.models;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToOne;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "images", uniqueConstraints = { @UniqueConstraint(columnNames = { "imageName", "user_id" }) })
public class Images {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public void setImage_id(long id) {
		this.id = id;
	}

	@Column(nullable = true)
	private String imageName;

	@Column(nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;

	@Lob
	@Column(length = Integer.MAX_VALUE, nullable = true)
	private byte[] image;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "image_tags", joinColumns = @JoinColumn(name = "imageId"), inverseJoinColumns = @JoinColumn(name = "tagId"))
	private Set<Tags> tags = new HashSet<>();

	public Set<Tags> getTags() {
		return tags;
	}

	public void setTags(Set<Tags> tags) {
		this.tags = tags;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	@JoinColumn(name = "dbFileId", nullable = false)
	private OriginalImageFile originalImageFile;

	public OriginalImageFile getoriginalImageFile() {
		return originalImageFile;
	}

	public void setoriginalImageFile(OriginalImageFile originalImageFile) {
		this.originalImageFile = originalImageFile;
	}

	public void addTag(Tags tag) {
		tags.add(tag);
		tag.getImages().add(this);
	}

	public void removeTagFromImage(Tags tag) {
		tags.remove(tag);
		tag.getImages().remove(this);
	}

	public Images() {
	}

	public Images(String imageName, Date uploadDate, byte[] image, User user) {
		this.imageName = imageName;
		this.uploadDate = uploadDate;
		this.image = image;
		this.user = user;
	}

	public Images(String imageName, Date uploadDate, byte[] image) {
		this.imageName = imageName;
		this.uploadDate = uploadDate;
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
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
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + Arrays.hashCode(image);
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		result = prime * result + ((uploadDate == null) ? 0 : uploadDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Images other = (Images) obj;
		if (id != other.id)
			return false;
		if (!Arrays.equals(image, other.image))
			return false;
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;
		if (uploadDate == null) {
			if (other.uploadDate != null)
				return false;
		} else if (!uploadDate.equals(other.uploadDate))
			return false;
		return true;
	}
}
