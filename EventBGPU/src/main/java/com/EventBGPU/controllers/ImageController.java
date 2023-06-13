package com.EventBGPU.controllers;

import com.EventBGPU.services.ImageService;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;

@Controller
public class ImageController {

    private final ImageService imageService;

    @Value("${images.path}")
    private String path;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) throws IOException {
        byte[] image;
        image = FileUtil.readAsByteArray(new File(path+filename));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
