package com.example.watopoly.model;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private static final int goAmount = 200;
    private String name;
    private Double money;
    private String colour;
    private int icon;
    private transient Bitmap bitmapIcon;

    private ArrayList<Property> properties = new ArrayList<>();
    private int jailFreeCards = 0;
    private int position = 0;
    private Boolean isJailed = false;
    private int numRailways = 0;

    public Player(String name, Double money, String colour, int icon, Bitmap bitmapIcon) {
        this.name = name;
        this.money = money;
        this.colour = colour;
        this.icon = icon;
        setBitmapIcon(bitmapIcon);
    }

    private void setBitmapIcon(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        src.getPixels(pixels, 0, width, 0, 0, width, height);

        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        // iteration through pixels
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                int index = y * width + x;
                if (pixels[index] < 0) {
                    pixels[index] = Color.parseColor(getColour());
                }
            }
        }
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        bitmapIcon = bmOut;
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

    public Bitmap getBitmapIcon() {
        return bitmapIcon;
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
