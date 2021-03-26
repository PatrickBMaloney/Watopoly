package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class CardTile extends Tile{

    //TODO: draw and apply cards
    @Override
    void landOn(Canvas canvas, Player player, Paint paint) {

    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public CardTile (String n, TileDirection d) {
        this.name = n;
        this.tileDirection = d;
        maxNumberOfPlayers = 2;
    }
}
