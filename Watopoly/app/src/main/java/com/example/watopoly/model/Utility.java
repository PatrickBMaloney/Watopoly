package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class Utility extends Property {

    @Override
    double getRentPrice() {
        return 0;
    }

    //TODO:
    @Override
    boolean purchase(Player buyer) {
        return false;
    }

    @Override
    void landOn(Player player) {

    }

    @Override
    public void drawOn(Canvas canvas) {

    }


    public Utility(String name, TileDirection direction, double baseRentPrice, double purchasePrice) {
        super(name, direction, baseRentPrice, purchasePrice);
    }

}
