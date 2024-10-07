package com.cinema.models;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Movie {
    private String name;
    private LocalDate date;
    private List<String> showtimes;
    private List<String> types;
    private String director;
    private String actor;
    private int duration;
    private String rated;
    private String description;
    private String language;

    public Movie(String name, LocalDate date, String director, String actor, int duration, String rated, String description, String language) {
        this.name = name;
        this.date = date;
        this.showtimes = new ArrayList<>();
        this.types = new ArrayList<>();
        this.director = director;
        this.actor = actor;
        this.duration = duration;
        this.rated = rated;
        this.description = description;
        this.language = language;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<String> getShowtimes() {
        return new ArrayList<>(showtimes);
    }

    public void addShowtime(String showtime) {
        this.showtimes.add(showtime);
    }

    public List<String> getTypes() {
        return new ArrayList<>(types);
    }

    public void addType(String type) {
        this.types.add(type);
    }

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }

    public int getDuration() {
        return duration;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }
}