package com.cinema.models;
import java.time.LocalDate;

public class Payment {
    private String status;
    private LocalDate date;
    private String method;
    private String transactionId;

    public Payment(String method, String transactionId) {
        this.status = "Pending";
        this.date = LocalDate.now();
        this.method = method;
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMethod() {
        return method;
    }

    public String getTransactionId() {
        return transactionId;
    }
}