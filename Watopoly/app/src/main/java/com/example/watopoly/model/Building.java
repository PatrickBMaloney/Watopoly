package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class Building extends Property {
    private int numberOfHouses = 0;
    private int numberOfHotels = 0;
    private String hexCode;

    @Override
    double getRentPrice() {
        return 0;
    }

    @Override
    boolean purchase(Player buyer) {
        return false;
    }

    @Override
    void landOn(Player player) {

    }

    @Override
    public void drawOn(Canvas canvas) {
        Paint insideFill = new Paint();
        insideFill.setColor(Color.parseColor(hexCode));
        int fillOffset = 125;

        // offset for thickness of gameboard lines, which is currently 4
        int thicknessOffset = 2;

        if (tileDirection == TileDirection.LEFT) {
            canvas.drawRect(coordinates.left + fillOffset,
                    coordinates.top,
                    coordinates.right - thicknessOffset,
                    coordinates.bottom - thicknessOffset,
                    insideFill);
        } else if (tileDirection == TileDirection.TOP) {
            canvas.drawRect(coordinates.left + thicknessOffset,
                    coordinates.top + fillOffset,
                    coordinates.right - thicknessOffset,
                    coordinates.bottom - thicknessOffset,
                    insideFill);
        } else if (tileDirection == TileDirection.RIGHT) {
            canvas.drawRect(coordinates.left + thicknessOffset,
                    coordinates.top - thicknessOffset,
                    coordinates.right - fillOffset,
                    coordinates.bottom - thicknessOffset,
                    insideFill);
        } else if (tileDirection == TileDirection.BOTTOM) {
            canvas.drawRect(coordinates.left + thicknessOffset,
                    coordinates.top - fillOffset,
                    coordinates.right - thicknessOffset,
                    coordinates.bottom + thicknessOffset,
                    insideFill);
        }
    }

    //TODO: find what this needs
    public void upgrade() {

    }

    public Building(String name, TileDirection direction, double baseRentPrice, double purchasePrice, String hexCode) {
        super(name, direction, baseRentPrice, purchasePrice);
        this.hexCode = hexCode;
    }
}
