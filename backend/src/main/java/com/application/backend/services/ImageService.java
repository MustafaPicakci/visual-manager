package com.application.backend.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.application.backend.models.Images;

public interface ImageService {
	public abstract void SaveImage(MultipartFile file);
	public abstract List<Images> FindImagesForUser();
}
