package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class TaxTile extends Tile {
    @Override
    void landOn(Canvas canvas, Player player, Paint paint) {

    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public TaxTile(TileDirection d) {
        this.tileDirection = d;
    }
}
