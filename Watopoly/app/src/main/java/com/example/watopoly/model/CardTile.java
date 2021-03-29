package com.example.watopoly.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.watopoly.enums.TileDirection;

import java.util.ArrayList;
import java.util.Random;

public class CardTile extends Tile{
    private ArrayList<ChanceCard> cards;
    private ChanceCard lastDrawn;

    public CardTile (String n, TileDirection d) {
        this.name = n;
        this.tileDirection = d;
        maxNumberOfPlayers = 2;
    }

    @Override
    public void landOn(Player player) {
        Random rand = new Random();
        lastDrawn = cards.get(rand.nextInt(cards.size()));
        //TODO: draw and apply cards
    }

    @Override
    public void drawOn(Canvas canvas) {

    }

    public void setCards(ArrayList<ChanceCard> cards) {
        this.cards = cards;
    }

    public ChanceCard getLastDrawn() {
        return lastDrawn;
    }
}
