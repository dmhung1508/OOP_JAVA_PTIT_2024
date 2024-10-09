package com.cinema.models;

public class Feedback {
    private byte star;
    private String comments;
    public Feedback(byte star, String comments) {
        this.star = star;
        this.comments = comments;
    }
    public byte getStar() {
        return star;
    }
    public void setStar(byte star) {
        this.star = star;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}