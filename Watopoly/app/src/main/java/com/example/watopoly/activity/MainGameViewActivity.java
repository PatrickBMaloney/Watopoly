package com.example.watopoly.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.watopoly.R;
import com.example.watopoly.fragment.DiceRollFragment;
import com.example.watopoly.fragment.FragmentCallbackListener;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Tile;
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
                Bitmap bitmapIcon = BitmapFactory.decodeResource(getResources(), icons.get(x));
                Player player = new Player(names.get(x), startingMoney, colours.get(x), icons.get(x), bitmapIcon);
                Game gameState = Game.getInstance();
                gameState.addPlayer(player);
            }
        }

        // board setup
        Game gameState = Game.getInstance();
        BoardView boardView = findViewById(R.id.board);
        gameState.setBoardInfo(boardView);
    }

    //FragmentCallbackListener diceRolled
    @Override
    public void onCallback() {
        int diceRollResult = diceRollFragment.getDiceRollResult();
        final Game game = Game.getInstance();

        Player currPlayer = game.getCurrentPlayer();

        Tile tile = game.moveCurrentPlayer(diceRollResult);

//        //move this after animating
//        final Dialog dialog = new Dialog(MainGameViewActivity.this, R.style.Theme_Dialog);
//        if (tile instanceof CardTile) {
//            Intent intent = new Intent(this, ChanceCardActivity.class);
//            intent.putExtra("drawnCard", (ChanceCard)((CardTile) tile).getLastDrawn());
//            startActivityForResult(intent, 1);
//        } else if (tile instanceof Property) {
//            final Property property = (Property) tile;
//            if (property.getOwner() == null) {
//                dialog.setContentView(R.layout.dialog_buy_house);
//                Button buyButton = dialog.findViewById(R.id.buyHouseButton);
//                Button cancelButton = dialog.findViewById(R.id.skipHouseButton);
//                TextView desTextView = dialog.findViewById(R.id.buyHouseDescriptionTextView);
//                String description = String.format("You landed on %s. Would you like to purchase for $%.2f?", property.getName(), property.getPurchasePrice());
//                if (property.getPurchasePrice() > game.getCurrentPlayer().getMoney()) {
//                    description = String.format("You landed on %s. Insufficient funds.", property.getName());
//                    cancelButton.setText("Continue");
//                    buyButton.setVisibility(View.GONE);
//                }
//
//
//                desTextView.setText(description);
//
//                if (tile instanceof Building) { // TODO: setProperty should accept all property types
//                    final FragmentManager fm = getSupportFragmentManager();
//                    PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyFragment);
//                    propertyFragment.setProperty((Building)tile);
//                }
//
//                buyButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        property.purchase(game.getCurrentPlayer());
//                        dialog.dismiss();
//                        destroyPropertyFragment(R.id.propertyCardBuyFragment);
//                        playerInfoHeaderFragment.refresh();
//                    }
//                });
//                cancelButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        destroyPropertyFragment(R.id.propertyCardBuyFragment);
//                    }
//                });
//                dialog.show();
//            } else if (property.getOwner() != game.getCurrentPlayer()) {
//                dialog.setContentView(R.layout.dialog_pay_rent);
//                Button continueButton = dialog.findViewById(R.id.payRentContinue);
//                TextView desTextView = dialog.findViewById(R.id.payRentDescriptionTextView);
//                TextView renterTextView = dialog.findViewById(R.id.renterTextView);
//                ImageView renterImageView = dialog.findViewById(R.id.renterImageView);
//                TextView ownerTextView = dialog.findViewById(R.id.ownerTextView);
//                ImageView ownerImageView = dialog.findViewById(R.id.ownerImageView);
//
//                if (tile instanceof Building) { // TODO: setProperty should accept all property types
//                    final FragmentManager fm = getSupportFragmentManager();
//                    PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardFragment);
//                    propertyFragment.setProperty((Building)tile);
//                }
//
//
//                String description = String.format("You landed on %s owned by %s", property.getName(), property.getOwner().getName());
//                renterTextView.setText(String.format("- $%.2f", property.getRentPrice()));
//                ownerTextView.setText(String.format("+ $%.2f", property.getRentPrice()));
//                desTextView.setText(description);
//
//                renterImageView.setImageResource(game.getCurrentPlayer().getIcon());
//                ImageViewCompat.setImageTintMode(renterImageView, PorterDuff.Mode.SRC_ATOP);
//                ImageViewCompat.setImageTintList(renterImageView, ColorStateList.valueOf(Color.parseColor(game.getCurrentPlayer().getColour())));
//
//                ownerImageView.setImageResource(property.getOwner().getIcon());
//                ImageViewCompat.setImageTintMode(ownerImageView, PorterDuff.Mode.SRC_ATOP);
//                ImageViewCompat.setImageTintList(ownerImageView, ColorStateList.valueOf(Color.parseColor(property.getOwner().getColour())));
//
//                continueButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        destroyPropertyFragment(R.id.propertyCardFragment);
//                        playerInfoHeaderFragment.refresh();
//                    }
//                });
//                dialog.show();
//            }
//        } else if (tile instanceof TaxTile) {
//            dialog.setContentView(R.layout.dialog_tax);
//            Button continueButton = dialog.findViewById(R.id.taxContinueButton);
//            continueButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    playerInfoHeaderFragment.refresh();
//                }
//            });
//            dialog.show();
//
//        } else if (tile instanceof Jail && game.getCurrentPlayer().getJailed()) {
//            dialog.setContentView(R.layout.dialog_jailed);
//            Button continueButton = dialog.findViewById(R.id.jailContinueButton);
//            continueButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//        }

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
}
