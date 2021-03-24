package com.example.watopoly.model;

public abstract class Property extends Tile{
    protected Player owner;
    protected double basePrice = 0;
    protected boolean isMortgaged = false;

    abstract double rentPrice();
    abstract boolean purchase(Player buyer);
}
