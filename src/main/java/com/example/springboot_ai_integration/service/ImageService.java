package com.example.springboot_ai_integration.service;

import com.example.springboot_ai_integration.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
public class ImageService {

    private final RestTemplate restTemplate;

    @Value("${ai.api.url}")
    private String apiUrl;

    @Value("${ai.api.model}")
    private String model;

    public ImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generate(String prompt, MultipartFile image) {

        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("Prompt cannot be empty");
        }

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Image cannot be empty");
        }

        try {

            String base64 =
                    Base64.getEncoder()
                            .encodeToString(image.getBytes());

            Message message =
                    new Message("user", prompt, List.of(base64));

            RequestPayload payload =
                    new RequestPayload(model, List.of(message));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RequestPayload> entity =
                    new HttpEntity<>(payload, headers);

            ResponseEntity<ApiResponse> response =
                    restTemplate.exchange(
                            apiUrl,
                            HttpMethod.POST,
                            entity,
                            ApiResponse.class
                    );

            ApiResponse body = response.getBody();

            if (body == null || body.getMessage() == null) {
                throw new RuntimeException("Invalid AI response");
            }

            return body.getMessage().getContent();

        } catch (Exception e) {
            throw new RuntimeException("Image processing failed");
        }
    }
}