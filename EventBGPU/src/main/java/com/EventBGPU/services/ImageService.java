package com.EventBGPU.services;

import com.EventBGPU.models.Event;
import com.EventBGPU.models.Image;
import com.EventBGPU.repositories.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ImageService {
    private Logger log = LoggerFactory.getLogger(UserService.class);
    private final ImageRepository imageRepository;

    @Value("${images.path}")
    private String path;
    
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(MultipartFile file, Event event) throws IOException {

        Image image = new Image();

        if(file.getSize() != 0){
            image.setName(file.getOriginalFilename());
            image.setPath(path);
            image.setEvent(event);
            imageRepository.save(image);
            byte[] bytes = file.getBytes();
            Path writePath = Paths.get(image.getPath() + image.getName());
            Files.write(writePath, bytes);
        }

    }

    public Image findImageById (Long id){
        return imageRepository.findById(id).orElse(null);
    }
}
