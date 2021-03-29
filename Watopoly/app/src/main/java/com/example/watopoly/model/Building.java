package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class Building extends Property {
    private int numberOfHouses = 0;
    private boolean hasHotel = false;
    private String hexCode;
    protected double oneHouseRentPrice;
    protected double twoHouseRentPrice;
    protected double threeHouseRentPrice;
    protected double fourHouseRentPrice;
    protected double hotelRentPrice;

    @Override
    public double getRentPrice() {
        return hasHotel ? getRentPriceWithHotel() : getRentPrice(numberOfHouses);
    }

    public double getRentPrice(int numOfHouses) {
        switch (numOfHouses) {
            case 0:
                return baseRentPrice;
            case 1:
                return oneHouseRentPrice;
            case 2:
                return twoHouseRentPrice;
            case 3:
                return threeHouseRentPrice;
            case 4:
                return fourHouseRentPrice;
            default:
                return 0;
        }
    }

    public double getRentPriceWithHotel() {
        return hotelRentPrice;
    }

    public String getPropertyHex() {
        return hexCode;
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

    public Building(String name, TileDirection direction, int baseRentPrice, int purchasePrice, String hexCode) {
        super(name, direction, baseRentPrice, purchasePrice);
        this.hexCode = hexCode;
        this.oneHouseRentPrice = Math.ceil((2.93 * baseRentPrice) / 10) * 10;
        this.twoHouseRentPrice = Math.ceil((2.89 * oneHouseRentPrice) / 10) * 10;
        this.threeHouseRentPrice = Math.ceil((2.65 * twoHouseRentPrice) / 10) * 10;
        this.fourHouseRentPrice = Math.ceil((1.43 * threeHouseRentPrice) / 10) * 10;
        this.hotelRentPrice = Math.ceil((1.29 * fourHouseRentPrice) / 10) * 10;
    }
}
