package com.example.springboot_ai_integration.controller;

import com.example.springboot_ai_integration.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/image")
public class ImagePageController {

    private final ImageService imageService;

    public ImagePageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public String page() {
        return "image";
    }

    @PostMapping
    public String submit(
            @RequestParam String prompt,
            @RequestParam MultipartFile image,
            Model model
    ) {
        model.addAttribute(
                "response",
                imageService.generate(prompt, image)
        );
        return "image";
    }
}