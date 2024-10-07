package com.cinema.models;

import java.time.LocalDateTime;

public class Notification {
    private String content;
    private LocalDateTime createdOn;

    public Notification(String content) {
        this.content = content;
        this.createdOn = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}