package com.example.springboot_ai_integration.controller;

import com.example.springboot_ai_integration.dto.ClientResponse;
import com.example.springboot_ai_integration.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ClientResponse generate(
            @RequestParam String prompt,
            @RequestParam MultipartFile image
    ) {
        return new ClientResponse(
                imageService.generate(prompt, image)
        );
    }
}