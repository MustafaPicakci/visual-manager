package com.application.backend.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.OriginalImageFile;
import com.application.backend.models.ImagePage;
import com.application.backend.models.Images;
import com.application.backend.models.User;
import com.application.backend.payload.request.setTagRequest;
import com.application.backend.security.services.UserDetailsServiceImpl;
import com.application.backend.services.ImageServiceImpl;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/images")
public class ImageController {

	@Autowired
	ImageServiceImpl imageServiceImpl;
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@PostMapping("/add")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Images add(MultipartFile file) {
		byte[] resizedImage = imageServiceImpl.resizeImage(file);
		return imageServiceImpl.saveImage(file, resizedImage);
	};

	@GetMapping("/list")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Page<Images>> listImages(@RequestParam int pageNumber, @RequestParam int pageSize) {

		try {
			ImagePage imagePage = new ImagePage();
			imagePage.setPageNumber(pageNumber);
			imagePage.setPageSize(pageSize);

			User user = userDetailsServiceImpl.loadUser();

			return new ResponseEntity<>(imageServiceImpl.findImagesForUserAndTagsIsNull(user, imagePage),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	};

	@GetMapping("/getAll")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Page<Images>> getAll(@RequestParam(required = false) String tag, @RequestParam int pageNumber,
			@RequestParam int pageSize) {

		try {
			ImagePage imagePage = new ImagePage();
			imagePage.setPageNumber(pageNumber);
			imagePage.setPageSize(pageSize);

			User user = userDetailsServiceImpl.loadUser();

			if (tag == null) {
				return new ResponseEntity<>(imageServiceImpl.findImagesForUser(user, imagePage), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(imageServiceImpl.findImagesForUserWithTag(tag, user, imagePage),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	};

	@PostMapping("/set/tag")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Images setTag(setTagRequest data) {
		return imageServiceImpl.setTag(data.getImageId(), data.getInsertedTagNames(), data.getDeletedTagNames());
	};

	@PostMapping("/delete")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public void deleteImage(Images data) {

		imageServiceImpl.deleteImage(data.getId());
	};

	@GetMapping("/downloadFile/{imageId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long imageId, HttpServletRequest request) {

		Images image = imageServiceImpl.findById(imageId);

		OriginalImageFile originalImageFile = imageServiceImpl.findOriginalFile(image.getoriginalImageFile().getId());

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(originalImageFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + originalImageFile.getFileName() + "\"")
				.body(new ByteArrayResource(originalImageFile.getData()));
	}
}
