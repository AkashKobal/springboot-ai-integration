package com.example.springboot_ai_integration.dto;

public class ClientRequest {

    private String prompt;

    public ClientRequest() {}

    public ClientRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
}