package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

public class GoToJail extends Tile {
    int jailLocation = 0;

    @Override
    public void landOn(Player player) {
        player.setPosition(jailLocation);
        player.setJailed(true);
    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public GoToJail() {
        maxNumberOfPlayers = 4;
        tileDirection = TileDirection.CORNER;
    }

    public void setJailLocation(int jailLocation) {
        this.jailLocation = jailLocation;
    }
}
