package com.example.watopoly.model;

public class Railway extends Property {
    @Override
    double rentPrice() {
        return this.basePrice;
    }

    //TODO:
    @Override
    boolean purchase(Player buyer) {
        return false;
    }

    @Override
    void landOn(Player player) {

    }
}
