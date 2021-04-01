package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class TaxTile extends Tile {
    private static final double taxAmount = 150;

    @Override
    public void landOn(Player player) {
        player.payAmount(taxAmount);
    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public TaxTile(TileDirection d, String name) {
        this.tileDirection = d;
        this.name = name;
    }
}
