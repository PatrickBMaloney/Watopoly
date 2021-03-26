package com.example.watopoly.model;

import com.example.watopoly.enums.TileDirection;

public abstract class Property extends Tile {
    protected Player owner = null;
    protected double baseRentPrice;
    protected double purchasePrice;
    protected boolean isMortgaged = false;

    abstract double getRentPrice();
    public double getPurchasePrice(){ return purchasePrice; }

    abstract boolean purchase(Player buyer);

    protected Property(String name, TileDirection direction, double baseRentPrice, double purchasePrice) {
        this.tileDirection = direction;
        this.baseRentPrice = baseRentPrice;
        this.name = name;
        this.purchasePrice = purchasePrice;
        maxNumberOfPlayers = 2;
    }
}

