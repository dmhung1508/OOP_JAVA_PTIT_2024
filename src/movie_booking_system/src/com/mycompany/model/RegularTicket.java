package com.mycompany.model;

public class RegularTicket extends TicketPrice {
    private int price = 50000;

    public RegularTicket(int quantity) {
        super(quantity);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public int getTotalPrice() {
        return super.getQuantity() * this.getPrice();
    }
}
