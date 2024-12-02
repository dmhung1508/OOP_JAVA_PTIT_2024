
package com.mycompany.model;

public class Transaction {
    private String username;
    private String amount;
    private String timestamp;
    private String seats;
    private String movieTitle;
    private String movieDate;
    private Boolean isPaid;

    public Transaction(String username, String amount, String timestamp, String seats, String movieTitle, String movieDate, Boolean isPaid) {
        this.username = username;
        this.amount = amount;
        this.timestamp = timestamp;
        this.seats = seats;
        this.movieTitle = movieTitle;
        this.movieDate = movieDate;
        this.isPaid = isPaid;
    }
    public String getUsername() { return username; };
    public String getAmount() { return amount; };
    public String getTimestamp() { return timestamp; };
    public String getSeats() { return seats; };
    public String getMovieTitle() { return movieTitle; };
    public String getMovieDate() { return movieDate; };
    public Boolean getIsPaid() { return isPaid; };
    
}