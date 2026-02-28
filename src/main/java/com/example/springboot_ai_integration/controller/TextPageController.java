package com.example.springboot_ai_integration.controller;

import com.example.springboot_ai_integration.service.TextService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/text")
public class TextPageController {

    private final TextService textService;

    public TextPageController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping
    public String page() {
        return "text";
    }

    @PostMapping
    public String submit(
            @RequestParam String prompt,
            Model model
    ) {
        model.addAttribute(
                "response",
                textService.generate(prompt)
        );
        return "text";
    }
}