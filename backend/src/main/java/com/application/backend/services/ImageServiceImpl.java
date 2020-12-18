package com.application.backend.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.ImagePage;
import com.application.backend.models.Images;
import com.application.backend.models.Tags;
import com.application.backend.models.User;
import com.application.backend.repository.ImageRepository;
import com.application.backend.repository.TagRepository;
import com.application.backend.repository.UserRepository;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired ImageRepository imageRepository;
  @Autowired UserRepository userRepository;
  @Autowired TagRepository tagRepository;

  @Override
  public Images saveImage(MultipartFile file) {
    Images image = new Images();
    try {
      byte[] imageFile = file.getBytes();
      Date uploadDate = new Date();
      String imageName = file.getOriginalFilename();
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();

      User user = userRepository.findByUsername(username);

      image.setImage(imageFile);
      image.setImageName(imageName);
      image.setUploadDate(uploadDate);
      image.setUser(user);
      imageRepository.save(image);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return image;
  }

  @Override
  public List<Images> findImagesForUser(User user) {
    return imageRepository.findAllByUser(user);
  }

  @Override
  public Page<Images> findImagesForUser(User user, ImagePage imagePage) {
	  Sort sort=Sort.by(imagePage.getSortDirection(),imagePage.getSortBy());
			  Pageable pageable=PageRequest.of(imagePage.getPageNumber(), imagePage.getPageSize(),sort);
			  
    return imageRepository.findAllByUser(user, pageable);
  }

  @Override
  public Images setTag(long imageId, String tagName) {
    Images image = imageRepository.getOne(imageId);
    Tags tag = null;
    try {
      tag = tagRepository.findTagsByTagName(tagName);
    } catch (Exception e) {
      System.out.println("tagi bulma sorunlu");
    }
    if (tag == null) {
      tag = new Tags(tagName);
    }

    image.addTag(tag);

    return imageRepository.save(image);
  }

  @Transactional
  @Override
  public void deleteImage(long imageId) {
    // etiketleri başka görseller kullanmıyorsa, onları da siliyoruz
    Images image = imageRepository.getOne(imageId);
    List<Tags> tags = image.getTags();
    imageRepository.deleteById(imageId);
    for (Tags tag : tags) {
      if (!imageRepository.existsByTagsId(tag.getId())) {
        tagRepository.deleteById(tag.getId());
      }
    }
  }

  @Override
  public Images unlikTag(long imageId, long tagId) {
    Images image = imageRepository.getOne(imageId);
    Tags tag = tagRepository.findTagsById(tagId);
    image.removeTagFromImage(tag);

    image = imageRepository.save(image);
    if (!imageRepository.existsByTagsId(tagId)) {
      // bu etikete sahip başka bir görsel yosa etiketi sil !
      tagRepository.deleteById(tagId);
    }
    return image;
  }
}
