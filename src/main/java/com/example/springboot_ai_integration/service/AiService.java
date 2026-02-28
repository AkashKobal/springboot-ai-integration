package com.example.springboot_ai_integration.service;

import com.example.springboot_ai_integration.dto.ApiResponse;
import com.example.springboot_ai_integration.dto.Message;
import com.example.springboot_ai_integration.dto.RequestPayload;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
public class AiService {

    private final RestTemplate restTemplate;

    @Value("${ai.api.url}")
    private String apiUrl;

    @Value("${ai.api.model}")
    private String model;

    public AiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateText(String prompt) {

        validatePrompt(prompt);

        Message message = new Message("user", prompt);

        RequestPayload payload =
                new RequestPayload(model, List.of(message));

        return execute(payload);
    }

    public String generateWithImage(
            String prompt,
            MultipartFile image
    ) {

        validatePrompt(prompt);

        try {

            String base64 =
                    Base64.getEncoder()
                            .encodeToString(image.getBytes());

            String imageData =
                    "data:" + image.getContentType() +
                            ";base64," + base64;

            // Build OpenAI Vision format manually
            String body = """
        {
          "model": "%s",
          "messages": [
            {
              "role": "user",
              "content": [
                { "type": "text", "text": "%s" },
                {
                  "type": "image_url",
                  "image_url": {
                    "url": "%s"
                  }
                }
              ]
            }
          ]
        }
        """.formatted(model, prompt, imageData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(System.getenv("OPENAI_API_KEY"));

            HttpEntity<String> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            apiUrl,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            return extractContent(response.getBody());

        } catch (Exception e) {
            throw new RuntimeException("Image processing failed");
        }
    }

    private String extractContent(String json) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(json);

        return root
                .get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText();
    }

    private String execute(RequestPayload payload) {

        try {

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
                throw new RuntimeException(
                        "Invalid AI response"
                );
            }

            return body.getMessage().getContent();

        } catch (RestClientException ex) {
            throw new RuntimeException(
                    "AI service unavailable"
            );
        }
    }

    private void validatePrompt(String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Prompt cannot be empty"
            );
        }
    }
}