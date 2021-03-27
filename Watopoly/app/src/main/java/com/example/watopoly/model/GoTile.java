package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class GoTile extends Tile {
    @Override
    void landOn(Player player) {

    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public GoTile() {
        maxNumberOfPlayers = 4;
        tileDirection = TileDirection.CORNER;
    }

}
