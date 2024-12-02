
package com.mycompany.model;

public class VIPTicket extends TicketPrice
{
    private int price = 60000;
    public VIPTicket(int quantity) {
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
