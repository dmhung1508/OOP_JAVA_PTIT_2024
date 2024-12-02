package com.mycompany.model;

import java.util.List;

public class Movie implements Comparable<Movie>
{
    private String id;
    private String title;
    private List<String> cinemas;
    private List<String> showDates;
    private String genre;
    private String imagePath;
    private String director;
    private String description;
    private int duration;
    private String releaseDate;
    private String mainActors;
    public Movie()
    {
        
    }
    public Movie(String id, String title, List<String> cinemas, List<String> showDates, String genre, String imagePath, String director, String description, int duration, String releaseDate, String mainActors) {
        this.id = id;
        this.title = title;
        this.cinemas = cinemas;
        this.showDates = showDates;
        this.genre = genre;
        this.imagePath = imagePath;
        this.director = director;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.mainActors = mainActors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<String> cinemas) {
        this.cinemas = cinemas;
    }

    public List<String> getShowDates() {
        return showDates;
    }

    public void setShowDates(List<String> showDates) {
        this.showDates = showDates;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMainActors() {
        return mainActors;
    }

    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }
    @Override
    public int compareTo(Movie o)
    {
        return this.id.compareTo(o.id);
    }
}