package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class GoToJail extends Tile {
    int jailLocation = 0;

    @Override
    void landOn(Canvas canvas, Player player, Paint mPaint) {

    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public GoToJail() {
        maxNumberOfPlayers = 4;
        tileDirection = TileDirection.CORNER;
    }

}
