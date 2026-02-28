package com.example.springboot_ai_integration.controller;

import com.example.springboot_ai_integration.dto.*;
import com.example.springboot_ai_integration.service.TextService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/text")
public class TextController {

    private final TextService textService;

    public TextController(TextService textService) {
        this.textService = textService;
    }

    @PostMapping
    public ClientResponse generate(
            @RequestBody ClientRequest request
    ) {
        return new ClientResponse(
                textService.generate(request.getPrompt())
        );
    }
}