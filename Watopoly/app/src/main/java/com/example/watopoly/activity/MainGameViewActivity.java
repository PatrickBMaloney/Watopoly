package com.example.watopoly.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.watopoly.R;
import com.example.watopoly.fragment.DiceRollFragment;
import com.example.watopoly.fragment.FragmentCallbackListener;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.fragment.PropertyFragment;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.CardTile;
import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Jail;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;
import com.example.watopoly.model.TaxTile;
import com.example.watopoly.model.Tile;
import com.example.watopoly.model.Utility;
import com.example.watopoly.util.GameSaveManager;
import com.example.watopoly.view.BoardView;

import java.util.ArrayList;

public class MainGameViewActivity extends AppCompatActivity implements FragmentCallbackListener {
    //TODO: move this somewhere else?
    private static final double startingMoney = 500;

    private PlayerInfoHeaderFragment playerInfoHeaderFragment;
    private DiceRollFragment diceRollFragment;
    private LinearLayout actionLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_view);

        Intent intent = getIntent();
        if (intent.hasExtra("continue")) {
            new GameSaveManager.LoadData(this).execute();
            return;
        }

        initGame();
    }

    public void initGame() {
        linkView();
        setup();
        startTurn();
    }

    private void startTurn() {
        new GameSaveManager.SaveData(this).execute();
        Game gameState = Game.getInstance();
        playerInfoHeaderFragment.setPlayer(gameState.nextTurn());
        diceRollFragment.getView().setVisibility(View.VISIBLE);
        actionLinearLayout.setVisibility(View.GONE);
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);
        diceRollFragment = (DiceRollFragment) fm.findFragmentById(R.id.rollToMoveFragment);
        diceRollFragment.setCallbackListener(this);

        Game gameState = Game.getInstance();
        BoardView boardView = findViewById(R.id.board);
        Pair<ArrayList<Tile>, Canvas> boardInfo = boardView.getBoardInfo();
        gameState.setBoardInfo(boardInfo);

        //TODO: bind button to the activity
        Button viewAssetButton = findViewById(R.id.viewAssetButton);
        Button tradeButton = findViewById(R.id.tradeButton);
        Button mortgageButton = findViewById(R.id.mortgageButton);
        Button endTurnButton = findViewById(R.id.endTurnButton);
        endTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTurn();
            }
        });

        actionLinearLayout = findViewById(R.id.actionsLinearLayout);
    }

    private void setup() {
        Intent intent = getIntent();
        if (!intent.hasExtra("continue")) {
            ArrayList<Integer> icons = intent.getIntegerArrayListExtra("icons");
            ArrayList<String> colours = intent.getStringArrayListExtra("colours");
            ArrayList<String> names = intent.getStringArrayListExtra("names");

            for (int x = 0; x < names.size(); x++) {
                Player player = new Player(names.get(x), startingMoney, colours.get(x), icons.get(x));
                Game gameState = Game.getInstance();
                gameState.addPlayer(player);
            }
        }

        //TODO: board setup
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);
        playerInfoHeaderFragment.refresh();

        final Game game = Game.getInstance();
        Tile tile = game.moveCurrentPlayer(0);
        showDialogByLandingTile(tile);
    }

    //FragmentCallbackListener diceRolled
    @Override
    public void onCallback() {
        int diceRollResult = diceRollFragment.getDiceRollResult();
        final Game game = Game.getInstance();
        game.setLastRoll(diceRollResult);
        Tile tile = game.moveCurrentPlayer(diceRollResult);

        if (tile instanceof CardTile) {
            Intent intent = new Intent(this, ChanceCardActivity.class);
            startActivityForResult(intent, 1);
        }
        else {
            showDialogByLandingTile(tile);
        }
        
        Log.d("Landed", tile.toString());
        Log.d("Landed", ""+game.getCurrentPlayer().getPosition());

        diceRollFragment.getView().setVisibility(View.GONE);
        actionLinearLayout.setVisibility(View.VISIBLE);
    }
    public void destroyPropertyFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(id);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
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

    public void showDialogByLandingTile(final Tile tile){
        final Dialog dialog = new Dialog(MainGameViewActivity.this, R.style.Theme_Dialog);
        final Game game = Game.getInstance();
        if (tile instanceof Property) {
            final Property property = (Property) tile;
            if (property.getOwner() == null) {
                dialog.setContentView(R.layout.dialog_buy_house);
                Button buyButton = dialog.findViewById(R.id.buyHouseButton);
                Button cancelButton = dialog.findViewById(R.id.skipHouseButton);
                TextView desTextView = dialog.findViewById(R.id.buyHouseDescriptionTextView);
                String description = String.format("You landed on %s. Would you like to purchase for $%.2f?", property.getName(), property.getPurchasePrice());
                if (property.getPurchasePrice() > game.getCurrentPlayer().getMoney()) {
                    description = String.format("You landed on %s. Insufficient funds.", property.getName());
                    cancelButton.setText("Continue");
                    buyButton.setVisibility(View.GONE);
                }


                desTextView.setText(description);

                final FragmentManager fm = getSupportFragmentManager();
                PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyFragment);
                propertyFragment.setProperty(property);

                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        property.purchase(game.getCurrentPlayer());
                        dialog.dismiss();
                        destroyPropertyFragment(R.id.propertyCardBuyFragment);
                        playerInfoHeaderFragment.refresh();
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        destroyPropertyFragment(R.id.propertyCardBuyFragment);
                    }
                });
                dialog.show();
            } else if (property.getOwner() != game.getCurrentPlayer()) {
                dialog.setContentView(R.layout.dialog_pay_rent);
                Button continueButton = dialog.findViewById(R.id.payRentContinue);
                TextView desTextView = dialog.findViewById(R.id.payRentDescriptionTextView);
                TextView renterTextView = dialog.findViewById(R.id.renterTextView);
                ImageView renterImageView = dialog.findViewById(R.id.renterImageView);
                TextView ownerTextView = dialog.findViewById(R.id.ownerTextView);
                ImageView ownerImageView = dialog.findViewById(R.id.ownerImageView);

                final FragmentManager fm = getSupportFragmentManager();
                PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardFragment);
                propertyFragment.setProperty(property);

                String description = String.format("You landed on %s owned by %s", property.getName(), property.getOwner().getName());

                renterTextView.setText(String.format("- $%.2f", property.getRentPrice()));
                ownerTextView.setText(String.format("+ $%.2f", property.getRentPrice()));
                desTextView.setText(description);

                renterImageView.setImageResource(game.getCurrentPlayer().getIcon());
                ImageViewCompat.setImageTintMode(renterImageView, PorterDuff.Mode.SRC_ATOP);
                ImageViewCompat.setImageTintList(renterImageView, ColorStateList.valueOf(Color.parseColor(game.getCurrentPlayer().getColour())));

                ownerImageView.setImageResource(property.getOwner().getIcon());
                ImageViewCompat.setImageTintMode(ownerImageView, PorterDuff.Mode.SRC_ATOP);
                ImageViewCompat.setImageTintList(ownerImageView, ColorStateList.valueOf(Color.parseColor(property.getOwner().getColour())));

                continueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        destroyPropertyFragment(R.id.propertyCardFragment);
                        playerInfoHeaderFragment.refresh();
                    }
                });
                dialog.show();
            }
            // TODO: move into view assets eventually
            else if (property.getOwner() == game.getCurrentPlayer()){
                dialog.setContentView(R.layout.dialog_buy_house_hotel);
                if (tile instanceof Building) {
                    final FragmentManager fm = getSupportFragmentManager();
                    PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyFragment);
                    propertyFragment.setProperty((Building)tile);
                }
                final Building currentTile = (Building) tile;
                final Button plusHouseButton = dialog.findViewById(R.id.plusBuyHouseButton);
                final Button minusHouseButton = dialog.findViewById(R.id.minusBuyHouseButton);
                final Button plusHotelButton = dialog.findViewById(R.id.plusBuyHotelButton);
                final Button minusHotelButton = dialog.findViewById(R.id.minusBuyHotelButton);
                final TextView currentHousesAndHotels = dialog.findViewById(R.id.currrentHousesAndHotels);
                Button confirmNumHouses = dialog.findViewById(R.id.confirmNumHouses);
                final double housePrice = ((Building) tile).getHousePrice();
                final int currentNumHouses = ((Building) tile).getNumberOfHouses();

                // if they already have all the houses don't show any incrementers
                if (((Building) tile).getNumberOfHouses() == 4 && ((Building) tile).isHasHotel()){
                    dialog.findViewById(R.id.buyHouseIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.buyHotelIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.currrentHousesAndHotels).setVisibility(View.GONE);
                    TextView title = dialog.findViewById(R.id.buyPropertyDescriptionTextView);
                    title.setPadding(0, 100, 0, 0);
                    title.setTextSize(20);
                    title.setText("You already have 4 houses and a hotel!");
                }

                // if they don't have enough money for anything don't show any incrementers
                if (game.getCurrentPlayer().getMoney() < housePrice){
                    dialog.findViewById(R.id.buyHouseIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.buyHotelIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.currrentHousesAndHotels).setVisibility(View.GONE);
                    TextView title = dialog.findViewById(R.id.buyPropertyDescriptionTextView);
                    title.setPadding(0, 100, 0, 0);
                    title.setTextSize(20);
                    title.setLines(3);
                    title.setText("Insufficient funds. You are unable to purchase any properties");
                }

                // if they don't have a full set don't show the incrementers
                if (game.getCurrentPlayer().getMoney() < housePrice){
                    dialog.findViewById(R.id.buyHouseIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.buyHotelIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.currrentHousesAndHotels).setVisibility(View.GONE);
                    TextView title = dialog.findViewById(R.id.buyPropertyDescriptionTextView);
                    title.setPadding(0, 100, 0, 0);
                    title.setTextSize(20);
                    title.setLines(3);
                    title.setText("You do not possess a full set. You are unable to purchase any properties.");
                }

                // if they can't afford a hotel, don't show the hotel incrementer
                if (currentNumHouses + game.getCurrentPlayer().getMoney()/housePrice < 5){
                    LinearLayout hotelIncrementer = dialog.findViewById(R.id.buyHotelIncrementer);
                    hotelIncrementer.setVisibility(View.INVISIBLE);
                }

                currentHousesAndHotels.setText(String.format("You currently have: %s houses, %s hotels",
                                                currentTile.getNumberOfHouses(),
                                                currentTile.isHasHotel() ? 1 : 0));

                plusHouseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView numHousesText = dialog.findViewById(R.id.numHouseButton);
                        int numHouses = Integer.parseInt(numHousesText.getText().toString()) + 1;

                        String numHousesAmount = String.valueOf(numHouses);
                        numHousesText.setText("" + numHousesAmount);
                        enableIncrementer(minusHouseButton);
                        // you can purchase as many houses as you can afford or the number of houses to get a full set
                        int maxNumberOfPurchasableHouses = Math.min((int)(game.getCurrentPlayer().getMoney()/housePrice), 4 - currentNumHouses);
                        if (numHouses == maxNumberOfPurchasableHouses){
                            disableIncrementer(plusHouseButton);
                        }

                        if (numHouses + currentNumHouses == 4){
                            enableIncrementer(plusHotelButton);
                        }
                    }
                });

                minusHouseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView numHousesText = dialog.findViewById(R.id.numHouseButton);
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
                            TextView numHotels = dialog.findViewById(R.id.numHotelsButton);
                            numHotels.setText("0");
                        }
                    }
                });

                plusHotelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        disableIncrementer(plusHotelButton);
                        enableIncrementer(minusHotelButton);
                        TextView numHotels = dialog.findViewById(R.id.numHotelsButton);
                        numHotels.setText("1");
                    }
                });

                minusHotelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        disableIncrementer(minusHotelButton);
                        enableIncrementer(plusHotelButton);
                        TextView numHotels = dialog.findViewById(R.id.numHotelsButton);
                        numHotels.setText("0");
                    }
                });

                confirmNumHouses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView numHousesText = dialog.findViewById(R.id.numHouseButton);
                        int numHouses = Integer.parseInt(numHousesText.getText().toString());

                        TextView numHotelText = dialog.findViewById(R.id.numHotelsButton);
                        int numHotels = Integer.parseInt(numHotelText.getText().toString());

                        ((Building) tile).buyHouses(numHouses);

                        if (numHotels == 1){
                            ((Building) tile).buyHotel();
                        }
                        
                        dialog.dismiss();
                        playerInfoHeaderFragment.refresh();
                        destroyPropertyFragment(R.id.propertyCardBuyFragment);
                    }
                });
                dialog.show();
            }
        } else if (tile instanceof TaxTile) {
            dialog.setContentView(R.layout.dialog_tax);
            Button continueButton = dialog.findViewById(R.id.taxContinueButton);
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    playerInfoHeaderFragment.refresh();
                }
            });
            dialog.show();

        } else if (tile instanceof Jail && game.getCurrentPlayer().getJailed()) {
            dialog.setContentView(R.layout.dialog_jailed);
            Button continueButton = dialog.findViewById(R.id.jailContinueButton);
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

}
