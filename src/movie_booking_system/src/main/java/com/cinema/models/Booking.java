package com.cinema.models;

import java.time.LocalDateTime;

public class Booking {
    private String bookingNumber;
    private int numberSeats;
    private LocalDateTime createdOn;
    private String status;

    public Booking(String bookingNumber, int numberSeats) {
        this.bookingNumber = bookingNumber;
        this.numberSeats = numberSeats;
        this.createdOn = LocalDateTime.now();
        this.status = "Pending";
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}