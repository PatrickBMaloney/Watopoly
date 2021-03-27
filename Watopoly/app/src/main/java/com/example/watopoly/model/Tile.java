package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

import java.io.Serializable;

public abstract class Tile implements Serializable {
    protected String name;
    protected Coordinates coordinates = new Coordinates(0,0,0,0);
    protected int maxNumberOfPlayers;
    protected int currNumberOfPlayers = 0;
    protected TileDirection tileDirection;

    abstract void landOn(Player player);

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates c) {
        coordinates = c;
    }

    abstract public void drawOn(Canvas canvas);

}
