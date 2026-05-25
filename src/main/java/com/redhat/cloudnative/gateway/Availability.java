package com.redhat.cloudnative.gateway;

public class Availability {

    private int quantity;

    public Availability() {
    }

    public Availability(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
