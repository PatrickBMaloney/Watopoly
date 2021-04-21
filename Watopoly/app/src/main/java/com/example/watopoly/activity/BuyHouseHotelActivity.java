package com.example.watopoly.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.watopoly.R;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.fragment.PropertyFragment;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;
import com.example.watopoly.util.BoardTiles;

public class BuyHouseHotelActivity extends AppCompatActivity {

    Building building;
    Game gameState;
    PlayerInfoHeaderFragment playerInfoHeaderFragment;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_house_hotel);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameState = Game.getInstance();
        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragmentAssets);
        Player myPlayer = gameState.getCurrentPlayer();
        playerInfoHeaderFragment.setPlayer(myPlayer);
        Intent intent = getIntent();
        building = myPlayer.getPropertyByName(intent.getStringExtra("property"));
        linkView();
    }

    private void linkView(){
        final FragmentManager fm = getSupportFragmentManager();
        final PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyHouseHotels);
        propertyFragment.setProperty(building);
        final Button plusHouseButton = findViewById(R.id.plusBuyHouseButton);
        final Button minusHouseButton = findViewById(R.id.minusBuyHouseButton);
        final Button plusHotelButton = findViewById(R.id.plusBuyHotelButton);
        final Button minusHotelButton = findViewById(R.id.minusBuyHotelButton);
        final TextView currentHousesAndHotels = findViewById(R.id.currrentHousesAndHotels);
        Button confirmNumHouses = findViewById(R.id.confirmNumHouses);
        final double housePrice = building.getHousePrice();
        final int currentNumHouses = building.getNumberOfHouses();
        final int currentNumHotels = building.isHasHotel() ? 1 : 0;


        // if they don't have a full set don't show the incrementers
        if (!gameState.getCurrentPlayer().ownsFullSet(building.getPropertyHex())){
            findViewById(R.id.buyHouseIncrementer).setVisibility(View.GONE);
            findViewById(R.id.buyHotelIncrementer).setVisibility(View.INVISIBLE);
            findViewById(R.id.currrentHousesAndHotels).setVisibility(View.GONE);
            TextView title = findViewById(R.id.buyPropertyDescriptionTextView);
            title.setPadding(0, 100, 0, 0);
            title.setTextSize(20);
            title.setLines(3);
            title.setText("You do not possess a full set. You are unable to purchase any properties.");
        }

        currentHousesAndHotels.setText(String.format("You currently have: %s houses, %s hotels",
                currentNumHouses,
                currentNumHotels));

        TextView numHousesText = findViewById(R.id.numHouseButton);
        TextView numHotelsButton = findViewById(R.id.numHotelsButton);
        numHousesText.setText("" + String.valueOf(currentNumHouses));
        numHotelsButton.setText("" + String.valueOf(currentNumHotels));

        if (currentNumHotels == 1) {
            enableIncrementer(minusHotelButton);
            enableIncrementer(minusHouseButton);
            disableIncrementer(plusHotelButton);
            disableIncrementer(plusHouseButton);
        } else {
            disableIncrementer(minusHotelButton);
            if (currentNumHouses == 4 && gameState.getCurrentPlayer().getMoney() >= housePrice) {
                enableIncrementer(plusHotelButton);
            } else {
                disableIncrementer(plusHotelButton);
            }
            int maxNumberOfPurchasableHouses = Math.min((int)(gameState.getCurrentPlayer().getMoney()/housePrice), 4);
            if (currentNumHouses >= maxNumberOfPurchasableHouses){
                disableIncrementer(plusHouseButton);
            } else {
                enableIncrementer(plusHouseButton);
            }
            if (currentNumHouses > 0) {
                enableIncrementer(minusHouseButton);
            } else {
                disableIncrementer(minusHouseButton);
            }
        }

        plusHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numHousesText = findViewById(R.id.numHouseButton);
                int numHouses = Integer.parseInt(numHousesText.getText().toString()) + 1;

                String numHousesAmount = String.valueOf(numHouses);
                numHousesText.setText("" + numHousesAmount);
                enableIncrementer(minusHouseButton);
                // you can purchase as many houses as you can afford or the number of houses to get a full set
                int maxNumberOfPurchasableHouses = Math.min((int)(gameState.getCurrentPlayer().getMoney()/housePrice), 4);
                if (numHouses >= maxNumberOfPurchasableHouses){
                    disableIncrementer(plusHouseButton);
                }

                if (numHouses == 4){
                    enableIncrementer(plusHotelButton);
                }
            }
        });

        minusHouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numHousesText = findViewById(R.id.numHouseButton);
                int numHouses = Integer.parseInt(numHousesText.getText().toString()) - 1;

                String numHousesAmount = String.valueOf(numHouses);
                numHousesText.setText("" + numHousesAmount);
                enableIncrementer(plusHouseButton);

                if (numHouses == 0){
                    disableIncrementer(minusHouseButton);
                }

                if (numHouses != 4){
                    disableIncrementer(minusHotelButton);
                    disableIncrementer(plusHotelButton);
                    TextView numHotels = findViewById(R.id.numHotelsButton);
                    numHotels.setText("0");
                }
            }
        });

        plusHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                disableIncrementer(plusHotelButton);
                enableIncrementer(minusHotelButton);
                TextView numHotels = findViewById(R.id.numHotelsButton);
                numHotels.setText("1");
            }
        });

        minusHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                disableIncrementer(minusHotelButton);
                enableIncrementer(plusHotelButton);
                TextView numHotels = findViewById(R.id.numHotelsButton);
                numHotels.setText("0");
            }
        });

        confirmNumHouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numHousesText = findViewById(R.id.numHouseButton);
                int numHouses = Integer.parseInt(numHousesText.getText().toString()) - currentNumHouses;

                TextView numHotelText = findViewById(R.id.numHotelsButton);
                int numHotels = Integer.parseInt(numHotelText.getText().toString()) - currentNumHotels;

                if (numHouses > 0) {
                    building.buyHouses(numHouses);
                } else if (numHouses < 0) {
                    building.sellHouses(-1 * numHouses);
                }

                if (numHotels == 1){
                    building.buyHotel();
                } else if (numHotels == -1) {
                    building.sellHotel();
                }
                finish();
            }
        });
    }

    public void disableIncrementer(Button disabledButton){
        disabledButton.setTextColor(Color.parseColor("#000000"));
        disabledButton.setBackgroundColor(Color.parseColor("#d7d7d7"));
        disabledButton.setEnabled(false);
    }

    public void enableIncrementer(Button enabledButton){
        enabledButton.setTextColor(Color.parseColor("#ffffff"));
        enabledButton.setBackgroundColor(Color.parseColor("#4ac1f0"));
        enabledButton.setEnabled(true);
    }
}
