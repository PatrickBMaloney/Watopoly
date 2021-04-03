package com.example.watopoly.model;

import com.example.watopoly.R;
import com.example.watopoly.enums.TileDirection;

public class TileFactory {
    public static class TileInitInfo {
        public TileType tileType;
        public String name;
        public TileDirection direction;
        public double baseRentPrice;
        public double purchasePrice;
        public String hexColour;
        public double housePrice;

        private TileInitInfo() {}

        public TileInitInfo(TileType tileType) {
            this.tileType = tileType;
        }

        public TileInitInfo(TileType tileType, TileDirection direction) {
            this.tileType = tileType;
            this.direction = direction;
        }

        public TileInitInfo(TileType tileType, String name, TileDirection direction) {
            this.tileType = tileType;
            this.name = name;
            this.direction = direction;
        }

        public TileInitInfo(TileType tileType, String name, TileDirection direction, double baseRentPrice, double purchasePrice) {
            this.tileType = tileType;
            this.name = name;
            this.direction = direction;
            this.baseRentPrice = baseRentPrice;
            this.purchasePrice = purchasePrice;
        }

        public TileInitInfo(TileType tileType, String name, TileDirection direction, double baseRentPrice, double purchasePrice, String hexColour) {
            this.tileType = tileType;
            this.name = name;
            this.direction = direction;
            this.baseRentPrice = baseRentPrice;
            this.purchasePrice = purchasePrice;
            this.hexColour = hexColour;
        }
    }

    public enum TileType {
        Chance,
        Go,
        GoToJail,
        Jail,
        Parking,
        Railway,
        Building,
        Utility,
        Tax,
    }

    public static Tile createTile(TileInitInfo tileInfo) {
        Tile tile = null;
        switch (tileInfo.tileType){
            case Chance:
                tile = new CardTile(tileInfo.name, tileInfo.direction);
                break;
            case Go:
                tile = new GoTile();
                break;
            case GoToJail:
                tile = new GoToJail();
                break;
            case Jail:
                tile = new Jail();
                break;
            case Parking:
                tile = new Parking();
                break;
            case Railway:
                int icon = 0;
                if (tileInfo.name.equals("GRT")) icon = R.drawable.grt;
                else if (tileInfo.name.equals("FedBus")) icon = R.drawable.fedbus;
                else if (tileInfo.name.equals("Ion Train")) icon = R.drawable.ion;
                else icon = R.drawable.uw_shuttle;

                tile = new Railway(tileInfo.name, tileInfo.direction, tileInfo.baseRentPrice, tileInfo.purchasePrice, icon);
                break;
            case Building:
                double housePrice = 0;
                if (tileInfo.direction == TileDirection.LEFT) housePrice = 50;
                else if (tileInfo.direction == TileDirection.TOP) housePrice = 100;
                else if (tileInfo.direction == TileDirection.RIGHT) housePrice = 150;
                else housePrice = 200;

                tile = new Building(tileInfo.name, tileInfo.direction, tileInfo.baseRentPrice, tileInfo.purchasePrice, housePrice, tileInfo.hexColour);
                break;
            case Utility:
                tile = new Utility(tileInfo.name, tileInfo.direction, tileInfo.baseRentPrice, tileInfo.purchasePrice);
                break;
            case Tax:
                tile = new TaxTile(tileInfo.direction);
                break;
        }
        return tile;
    }

}
