package com.example.watopoly.model;

import com.example.watopoly.enums.TileDirection;

public abstract class Property extends Tile {
    protected Player owner = null;
    protected double baseRentPrice;
    protected double purchasePrice;
    protected boolean isMortgaged = false;

    public abstract double getRentPrice();
    public double getPurchasePrice(){ return purchasePrice; }
    public String getName(){ return name; }

    public void purchase(Player buyer) {
        buyer.payAmount(purchasePrice);
        owner = buyer;
        buyer.addProperty(this);
    }

    protected Property(String name, TileDirection direction, double baseRentPrice, double purchasePrice) {
        this.tileDirection = direction;
        this.baseRentPrice = baseRentPrice;
        this.name = name;
        this.purchasePrice = purchasePrice;
        maxNumberOfPlayers = 2;
    }

    @Override
    public void landOn(Player player) {
        if (owner != null && owner != player && !isMortgaged && !owner.getJailed()) {
            player.payAmount(getRentPrice());
            owner.receiveAmount(getRentPrice());
        }
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public boolean getMortgaged() { return isMortgaged;}

    public void mortgage() {
        isMortgaged = true;
    }
    public void unMortgage() {
        isMortgaged = false;
    }

    public void reset() {
        owner = null;
        isMortgaged = false;
    }
}

