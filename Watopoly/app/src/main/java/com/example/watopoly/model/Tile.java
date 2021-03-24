package com.example.watopoly.model;

public abstract class Tile {
    protected String name;

    abstract void landOn(Player player);
}
