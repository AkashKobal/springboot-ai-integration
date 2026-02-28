package com.example.springboot_ai_integration.dto;

public class ApiResponse {

    private String model;
    private Message message;
    private boolean done;

    public ApiResponse() {}

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Message getMessage() { return message; }
    public void setMessage(Message message) { this.message = message; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
}