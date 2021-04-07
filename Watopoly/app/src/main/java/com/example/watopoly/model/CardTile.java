package com.example.watopoly.model;

import android.graphics.Canvas;

import com.example.watopoly.enums.TileDirection;

import java.util.ArrayList;
import java.util.Random;

public class CardTile extends Tile{

    public CardTile (String n, TileDirection d) {
        this.name = n;
        this.tileDirection = d;
        maxNumberOfPlayers = 2;
    }

    @Override
    public void drawOn(Canvas canvas) {

    }
}
