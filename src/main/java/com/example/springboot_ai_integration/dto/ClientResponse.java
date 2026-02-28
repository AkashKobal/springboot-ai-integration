package com.example.springboot_ai_integration.dto;

public class ClientResponse {

    private String response;

    public ClientResponse() {}

    public ClientResponse(String response) {
        this.response = response;
    }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
}