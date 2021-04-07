package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class Railway extends Property {
    private int icon;

    @Override
    public double getRentPrice() {
        return getRentPrice(this.owner.getNumRailways());
    }

    public double getRentPrice(int numRailwaysOwned) {
        return this.baseRentPrice * Math.pow(2,  numRailwaysOwned - 1);
    }

    public int getIcon() {
        return this.icon;
    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public Railway(String name, TileDirection direction, double baseRentPrice, double purchasePrice, int transportIcon) {
        super(name, direction, baseRentPrice, purchasePrice);
        icon = transportIcon;
    }

}
