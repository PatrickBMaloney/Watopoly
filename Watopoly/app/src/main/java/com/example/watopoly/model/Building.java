package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;
import com.example.watopoly.util.BoardTiles;

import java.util.List;

public class Building extends Property {
    private int numberOfHouses = 0;
    private boolean hasHotel = false;
    private String hexCode;
    private double housePrice;
    private boolean fullSetOwned = false;

    @Override
    public double getRentPrice() {
        return hasHotel ? getRentPriceWithHotel() : getRentPrice(numberOfHouses, fullSetOwned);
    }

    public double getHousePrice(){ return housePrice; }

    public double getRentPrice(int numOfHouses, boolean isFullSet) {
        if (isFullSet) {
            switch (numOfHouses) {
                case 0:
                    return Math.ceil((2 * baseRentPrice) / 10) * 10;
                case 1:
                    return Math.ceil((4.94 * baseRentPrice) / 10) * 10;
                case 2:
                    return Math.ceil((14.3 * baseRentPrice) / 10) * 10;
                case 3:
                    return Math.ceil((37.8 * baseRentPrice) / 10) * 10;
                case 4:
                    return Math.ceil((54.1 * baseRentPrice) / 10) * 10;
                default:
                    return 0;
            }
        } else {
            return baseRentPrice;
        }
    }

    public int getNumberOfHouses(){ return numberOfHouses; }

    public boolean isHasHotel(){ return hasHotel; }

    public double getRentPriceWithHotel() {
        return Math.ceil((69.8 * baseRentPrice) / 10) * 10;
    }

    public String getPropertyHex() {
        return hexCode;
    }

    public boolean isFullSetOwned() { return fullSetOwned; }

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

    public void buyHouses(int numHouses){
        Game game = Game.getInstance();
        game.getCurrentPlayer().payAmount(numHouses * housePrice);
        numberOfHouses += numHouses;
    }

    public void buyHotel(){
        Game game = Game.getInstance();
        game.getCurrentPlayer().payAmount(housePrice);
        hasHotel = true;
    }

    public void setFullColorSet(){
        List<Building> colorSet = BoardTiles.getTilesByColor(hexCode);
        for(Building property: colorSet){
            property.setFullSetOwned(true);
        }
    }

    private void setFullSetOwned(boolean fullSetOwned){
        this.fullSetOwned = fullSetOwned;
    }

    public Building(String name, TileDirection direction, double baseRentPrice, double purchasePrice, double housePrice, String hexCode) {
        super(name, direction, baseRentPrice, purchasePrice);
        this.hexCode = hexCode;
        this.housePrice = housePrice;
    }
}
