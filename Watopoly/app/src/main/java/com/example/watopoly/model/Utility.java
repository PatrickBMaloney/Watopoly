package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class Utility extends Property {
    private Game game = Game.getInstance();

    @Override
    public double getRentPrice() {
        if (this.owner.getNumUtilities() == 2) {
            return 10 * game.getLastRoll();
        } else if (this.owner.getNumUtilities() == 1) {
            return 4 * game.getLastRoll();
        } else {
            return 0;
        }
    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    @Override
    public void purchase(Player buyer) {
        buyer.payAmount(purchasePrice);
        owner = buyer;
        buyer.addProperty(this);
    }

    public Utility(String name, TileDirection direction, double baseRentPrice, double purchasePrice) {
        super(name, direction, baseRentPrice, purchasePrice );
    }

}
