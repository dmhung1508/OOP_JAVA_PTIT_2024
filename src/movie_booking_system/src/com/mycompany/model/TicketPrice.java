package com.mycompany.model;

public abstract class TicketPrice 
{
    private int quantity;
    public TicketPrice(int quantity) {
        this.quantity = quantity;   
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public abstract int getPrice(); 
    public abstract int getTotalPrice();

}


