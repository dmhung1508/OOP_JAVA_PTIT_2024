
package com.mycompany.model;

public class Seats implements Comparable<Seats>{
    private String seat;
    private String status;

    public Seats(String seat, String status) {
        this.seat = seat;
        this.status = status;
    }

  
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public int compareTo(Seats o)
    {
        return this.seat.compareTo(o.seat);
    }
}