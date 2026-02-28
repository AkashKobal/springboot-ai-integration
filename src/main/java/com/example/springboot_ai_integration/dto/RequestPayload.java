package com.example.springboot_ai_integration.dto;

import java.util.List;

public class RequestPayload {

    private String model;
    private boolean stream;
    private List<Message> messages;

    public RequestPayload() {}

    public RequestPayload(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
        this.stream = false;
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public boolean isStream() { return stream; }
    public void setStream(boolean stream) { this.stream = stream; }

    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
}