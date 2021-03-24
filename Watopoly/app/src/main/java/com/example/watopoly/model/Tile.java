package com.example.watopoly.model;

import java.io.Serializable;

public abstract class Tile implements Serializable {
    protected String name;

    abstract void landOn(Player player);
}
