package com.cinema.models;

public class Seat {
    private String type;
    private double price;
    private String status;
    private int numberSeat;

    public Seat(String type, double price, int numberSeat) {
        this.type = type;
        this.price = price;
        this.status = "Available";
        this.numberSeat = numberSeat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberSeat() {
        return numberSeat;
    }
}