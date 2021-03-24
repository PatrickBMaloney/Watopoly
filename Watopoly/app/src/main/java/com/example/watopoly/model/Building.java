package com.example.watopoly.model;

public class Building extends Property {
    private int numberOfHouses = 0;
    private int numberOfHotels = 0;
    private int colour = 0; // TEMP

    //TODO:
    @Override
    double rentPrice() {
        return 0;
    }

    @Override
    boolean purchase(Player buyer) {
        return false;
    }

    @Override
    void landOn(Player player) {

    }

    //TODO: find what this needs
    public void upgrade() {

    }
}
