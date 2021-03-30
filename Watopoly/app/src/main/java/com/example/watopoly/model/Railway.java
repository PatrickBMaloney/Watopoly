package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class Railway extends Property {

    @Override
    public double getRentPrice() {
        return this.baseRentPrice;
    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public Railway(String name, TileDirection direction, int baseRentPrice, int purchasePrice) {
        super(name, direction, baseRentPrice, purchasePrice);
    }

}
