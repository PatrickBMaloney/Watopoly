package com.example.watopoly.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String name;
    private Double money;
    private String colour;
    private int icon;

    private ArrayList<Property> properties = new ArrayList<>();
    private int jailFreeCards = 0;
    private int coordinates = 0;
    private int currPosition = 0;
    private int numRailways = 0;

    public Player(String name, Double money, String colour, int icon) {
        this.name = name;
        this.money = money;
        this.colour = colour;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Double getMoney() {
        return money;
    }

    public String getColour() {
        return colour;
    }

    public int getIcon() {
        return icon;
    }
}
