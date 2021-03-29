package com.example.watopoly.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private static final int goAmount = 200;
    private String name;
    private Double money;
    private String colour;
    private int icon;

    private ArrayList<Property> properties = new ArrayList<>();
    private int jailFreeCards = 0;
    private int position = 0;
    private Boolean isJailed = false;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
    public void passedGo() {
        money += goAmount;
    }

    public void payAmount(double amount) { money -= amount; }

    public void receiveAmount(double amount) { money += amount;}

    public Boolean getJailed() {
        return isJailed;
    }

    public void setJailed(Boolean jailed) {
        isJailed = jailed;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }
}
