package com.example.springboot_ai_integration.service;

import com.example.springboot_ai_integration.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TextService {

    private final RestTemplate restTemplate;

    @Value("${ai.api.url}")
    private String apiUrl;

    @Value("${ai.api.model}")
    private String model;

    public TextService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generate(String prompt) {

        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("Prompt cannot be empty");
        }

        Message message = new Message("user", prompt);

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
    }
}