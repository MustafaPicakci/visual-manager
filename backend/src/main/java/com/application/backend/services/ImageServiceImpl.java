package com.application.backend.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.OriginalImageFile;
import com.application.backend.models.ImagePage;
import com.application.backend.models.Images;

import com.application.backend.models.Tags;
import com.application.backend.models.User;
import com.application.backend.repository.OriginalImageFileRepopsitory;
import com.application.backend.repository.ImageRepository;

import com.application.backend.repository.TagRepository;
import com.application.backend.repository.UserRepository;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired ImageRepository imageRepository;
  @Autowired UserRepository userRepository;
  @Autowired TagRepository tagRepository;
  @Autowired OriginalImageFileRepopsitory databaseFileRepository;

  @Override
  public Images saveImage(MultipartFile file, byte[] imageBytes) {
    Images image = new Images();
    OriginalImageFile dbFile = new OriginalImageFile();
    byte[] imageFile = null;
    try {
      imageFile = file.getBytes();
    } catch (IOException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Date uploadDate = new Date();
    String imageName = file.getOriginalFilename();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    User user = userRepository.findByUsername(username);

    image.setImage(imageBytes);
    image.setImageName(imageName);
    image.setUploadDate(uploadDate);
    image.setUser(user);

    dbFile.setData(imageFile);
    dbFile.setFileName(file.getOriginalFilename());
    dbFile.setFileType(file.getContentType());
    image.setoriginalImageFile(dbFile);
    dbFile.setImage(image);
    imageRepository.save(image);

    return image;
  }

  @Override
  public OriginalImageFile findByImage(Images image) {
    return databaseFileRepository.findByImage(image);
  }

  @Override
  public List<Images> findImagesForUser(User user) {
    return imageRepository.findAllByUser(user);
  }

  @Override
  public Page<Images> findImagesForUser(User user, ImagePage imagePage) {
    Sort sort = Sort.by(imagePage.getSortDirection(), imagePage.getSortBy());
    Pageable pageable = PageRequest.of(imagePage.getPageNumber(), imagePage.getPageSize(), sort);

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
    Set<Tags> tags = image.getTags();
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

  @Override
  public Page<Images> findImagesForUserWithTag(String tag, User user, ImagePage imagePage) {

    Sort sort = Sort.by(imagePage.getSortDirection(), imagePage.getSortBy());
    Pageable pageable = PageRequest.of(imagePage.getPageNumber(), imagePage.getPageSize(), sort);
    return imageRepository.findByTags_TagNameContainingAndUser(tag, user, pageable);
  }

  @Override
  public Page<Images> findImagesForUserAndTagsIsNull(User user, ImagePage imagePage) {
    Sort sort = Sort.by(imagePage.getSortDirection(), imagePage.getSortBy());
    Pageable pageable = PageRequest.of(imagePage.getPageNumber(), imagePage.getPageSize(), sort);
    return imageRepository.findByUserAndTagsIsNull(user, pageable);
  }

  @Override
  public byte[] resizeImage(MultipartFile file) {
    String fileName = file.getOriginalFilename();
    String fileFormat = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

    byte[] imageBytes = null;
    try {
      imageBytes = file.getBytes();

      // convert byte[] back to a BufferedImage
      InputStream is = new ByteArrayInputStream(imageBytes);

      BufferedImage image = ImageIO.read(is);

      image = Scalr.resize(image, 900, 900);

      // convert a BufferedImage to byte[]

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(image, fileFormat, baos);
      imageBytes = baos.toByteArray();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return imageBytes;
  }

  @Override
  public Images findById(long id) {
    return imageRepository.findById(id).get();
  }

  @Override
  public long totalImages(long userId) {
    return imageRepository.countByUserId(userId);
  }
}
