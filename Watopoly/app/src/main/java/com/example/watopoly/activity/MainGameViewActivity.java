package com.example.watopoly.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.example.watopoly.model.CardTile;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Jail;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;
import com.example.watopoly.model.TaxTile;
import com.example.watopoly.model.Tile;
import com.example.watopoly.util.GameSaveManager;
import com.example.watopoly.view.BoardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainGameViewActivity extends AppCompatActivity implements FragmentCallbackListener {
    //TODO: move this somewhere else?
    private static final double startingMoney = 0;

    private PlayerInfoHeaderFragment playerInfoHeaderFragment;
    private DiceRollFragment diceRollFragment;
    private LinearLayout actionLinearLayout;

    private Map<Tile, ArrayList<Player>> drawingState = new HashMap<>();
    private BoardView boardView;
    private Button viewAssetButton;
    private Button tradeButton;
    private Button mortgageButton;
    private Button endTurnButton;
    private Button forfeitButton;

    private static int CHANCE_REQUEST_CODE = 1;
    private static int JAIL_OPTION_REQUEST_CODE = 2;
    private static int MORTGAGE_REQUEST_CODE = 3;
    private static int MORTGAGE_TILE_REQUEST_CODE = 4;

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

    //Not Ideal but fixes the sync issue
    public void onSaveComplete() {
        Game gameState = Game.getInstance();
        playerInfoHeaderFragment.setPlayer(gameState.nextTurn());
        diceRollFragment.getView().setVisibility(View.VISIBLE);
        diceRollFragment.setRollButtonVisibility(View.VISIBLE);
        actionLinearLayout.setVisibility(View.GONE);

        if (gameState.getCurrentPlayer().getJailed()) {
            Intent intent = new Intent(this, JailOptionsActivity.class);
            startActivityForResult(intent, JAIL_OPTION_REQUEST_CODE);
        }
    }

    private void startTurn() {
        new GameSaveManager.SaveData(this).execute();
    }


    private void mortgage(int requestCode){
        Intent mortgageIntent = new Intent(getApplicationContext(), MortgageActivity.class);
        startActivityForResult(mortgageIntent,requestCode);
    }

    private void bankrupt(){
        final Game game = Game.getInstance();
        final Dialog dialog = new Dialog(MainGameViewActivity.this, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_bankruptcy);
        Button continueButton = dialog.findViewById(R.id.bankruptButton);
        TextView headerTextView = dialog.findViewById(R.id.headerBankruptTextView);
        TextView messageTextView = dialog.findViewById(R.id.bankruptMessageTextView);
        String header = String.format("%s is Bankrupt!", game.getCurrentPlayer().getName());
        String message = String.format("%s cannot afford to pay and is out of the game.  All assets owned by this player have been returned to the bank.", game.getCurrentPlayer().getName());
        messageTextView.setText(message);
        headerTextView.setText(header);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.removePlayer(game.getCurrentPlayer());
                dialog.dismiss();
                if (game.getPlayers().size() == 1){
                    endgame();
                } else{
                    startTurn();
                }
            }
        });
        dialog.show();
    }

    private void endgame(){
        final Game game = Game.getInstance();
        Player winner = game.getPlayers().get(0);
        final Dialog dialog = new Dialog(MainGameViewActivity.this, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_end_game);
        Button continueButton = dialog.findViewById(R.id.endgameButton);
        TextView headerTextView = dialog.findViewById(R.id.headerEndGameTextView);
        TextView messageTextView = dialog.findViewById(R.id.endgameMessageTextView);
        ImageView winnerImage = dialog.findViewById(R.id.winnerImageView);
        String header = String.format("%s is the Winner!", winner.getName());
        String message = String.format("Congratulations %s for winning this game of Watopoly!", winner.getName());
        messageTextView.setText(message);
        headerTextView.setText(header);
        winnerImage.setImageResource(winner.getIcon());
        ImageViewCompat.setImageTintMode(winnerImage, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(winnerImage, ColorStateList.valueOf(Color.parseColor(winner.getColour())));
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.resetGame();
                dialog.dismiss();
                Intent menuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(menuIntent);
            }
        });
        dialog.show();
    }

    private void linkView() {
        final Game game = Game.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);
        diceRollFragment = (DiceRollFragment) fm.findFragmentById(R.id.rollToMoveFragment);
        diceRollFragment.setCallbackListener(this);

        //TODO: bind button to the activity
        viewAssetButton = findViewById(R.id.viewAssetButton);
        Button viewAssetButton = findViewById(R.id.viewAssetButton);
        viewAssetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGameViewActivity.this, ViewAssetsActivity.class);
                startActivity(intent);
            }
        });
        tradeButton = findViewById(R.id.tradeButton);
        mortgageButton = findViewById(R.id.mortgageButton);
        endTurnButton = findViewById(R.id.endTurnButton);
        forfeitButton = findViewById(R.id.forfeitButton);

        endTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTurn();
            }
        });

        forfeitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankrupt();
            }
        });

        mortgageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mortgage(MORTGAGE_REQUEST_CODE);
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
                Drawable d = getResources().getDrawable(icons.get(x));
                Bitmap b = ((BitmapDrawable) d).getBitmap();

                Player player = new Player(names.get(x), startingMoney, colours.get(x), icons.get(x), b);
                Game gameState = Game.getInstance();
                gameState.addPlayer(player);
            }
        }

        // board setup
        boardView = findViewById(R.id.board);
        setupDrawingState();
    }

    private void drawPlayerOnTile(Tile newTile) {
        //removes players
        Game game = Game.getInstance();
        for (Tile tile: drawingState.keySet()) {
            ArrayList<Player> playerList = drawingState.get(tile);
            for (Iterator<Player> iterator = playerList.iterator(); iterator.hasNext(); ) {
                Player currPlayer = iterator.next();
                if (currPlayer.getName().equals(game.getCurrentPlayer().getName())) {
                    iterator.remove();
                    tile.decrementCurrNumberOfPlayers();
                }
            }
        }

        drawingState.get(newTile).add(game.getCurrentPlayer());
        boardView.drawDrawingState(drawingState);
    }

    private void onAnimationFinish(int diceRoll) {
        Game game = Game.getInstance();
        Tile tile = game.moveCurrentPlayer(diceRoll);
        drawPlayerOnTile(tile);

        if (tile instanceof CardTile) {
            Intent intent = new Intent(this, ChanceCardActivity.class);
            startActivityForResult(intent, CHANCE_REQUEST_CODE);
        }
        else {
            showDialogByLandingTile(tile);
        }

        Log.d("Landed", tile.toString());
        Log.d("Landed", ""+game.getCurrentPlayer().getPosition());

        diceRollFragment.getView().setVisibility(View.GONE);
        Boolean isJailed = game.getCurrentPlayer().getJailed();
        mortgageButton.setVisibility(isJailed ? View.GONE : View.VISIBLE);
        tradeButton.setVisibility(isJailed ? View.GONE : View.VISIBLE);
        actionLinearLayout.setVisibility(View.VISIBLE);
    }

    private void animateMove(final int steps) {
        int delay = 300;
        for (int x = 0; x <= steps; x++) {
            final int delta = x;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Game game = Game.getInstance();
                    int currPos = game.getCurrentPlayer().getPosition() + delta;
                    Tile tile = game.getBoardTiles().get(currPos % game.getBoardTiles().size());
                    drawPlayerOnTile(tile);

                    if (delta == steps) {
                        onAnimationFinish(steps);
                    }
                }
            }, delay*x + delay);
        }
    }

    private void setupDrawingState() {
        Game gameState = Game.getInstance();
        ArrayList<Tile> tiles = gameState.getBoardTiles();
        for (Tile t: tiles) {
            drawingState.put(t, new ArrayList<Player>());
        }

        for (Player p: gameState.getPlayers()) {
            drawingState.get(tiles.get(p.getPosition())).add(p);
            tiles.get(p.getPosition()).incrementCurrNumberOfPlayers();
        }

        boardView.drawDrawingState(drawingState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Game game = Game.getInstance();
        if (requestCode == CHANCE_REQUEST_CODE) {
            FragmentManager fm = getSupportFragmentManager();
            playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);
            playerInfoHeaderFragment.refresh();

            Tile tile = game.moveCurrentPlayer(0);
            drawPlayerOnTile(tile);
            showDialogByLandingTile(tile);
            Boolean isJailed = game.getCurrentPlayer().getJailed();
            mortgageButton.setVisibility(isJailed ? View.GONE : View.VISIBLE);
            tradeButton.setVisibility(isJailed ? View.GONE : View.VISIBLE);
        }
        else if (requestCode == JAIL_OPTION_REQUEST_CODE) {
            Boolean isJailed = game.getCurrentPlayer().getJailed();
            if (isJailed) {
                diceRollFragment.getView().setVisibility(View.GONE);
                mortgageButton.setVisibility(View.GONE);
                tradeButton.setVisibility(View.GONE);
                actionLinearLayout.setVisibility(View.VISIBLE);
            }
        }
        else if (requestCode == MORTGAGE_REQUEST_CODE) {
            FragmentManager fm = getSupportFragmentManager();
            playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragment);
            playerInfoHeaderFragment.refresh();
        }
        else if (requestCode == MORTGAGE_TILE_REQUEST_CODE) {
            Tile tile = game.moveCurrentPlayer(0);
            showDialogByLandingTile(tile);
            }
    }

    //FragmentCallbackListener diceRolled
    @Override
    public void onCallback() {
        int diceRollResult = diceRollFragment.getDiceRollResult();
        Game game = Game.getInstance();
        game.setLastRoll(diceRollResult);
        diceRollFragment.setRollButtonVisibility(View.INVISIBLE);
        animateMove(diceRollResult);
    }

    public void destroyPropertyFragment(int id) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(id);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    public void showDialogByLandingTile(Tile tile){
        final Dialog dialog = new Dialog(MainGameViewActivity.this, R.style.Theme_Dialog);
        final Game game = Game.getInstance();
        if (tile instanceof Property) {
            final Property property = (Property) tile;
            if (property.getOwner() == null) {
                final Button buyButton;
                final Button cancelButton;
                final Button mortgageButton;
                final TextView desTextView;
                String description;
                dialog.setContentView(R.layout.dialog_buy_house);
                 buyButton = dialog.findViewById(R.id.buyHouseButton);
                mortgageButton = dialog.findViewById(R.id.mortgageBuyPropertyButton);
                cancelButton = dialog.findViewById(R.id.skipHouseButton);
                 desTextView = dialog.findViewById(R.id.buyHouseDescriptionTextView);
                 description = String.format("You landed on %s. Would you like to purchase for $%.2f?", property.getName(), property.getPurchasePrice());
                if (property.getPurchasePrice() > game.getCurrentPlayer().getMoney()) {
                    description = String.format("You landed on %s. Insufficient funds to purchase for $%.2f.", property.getName(), property.getPurchasePrice());
                    cancelButton.setText("Continue");
                    mortgageButton.setVisibility(View.VISIBLE);
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
                mortgageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        destroyPropertyFragment(R.id.propertyCardBuyFragment);
                        mortgage(MORTGAGE_TILE_REQUEST_CODE);
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
            Button continueButton = dialog.findViewById(R.id.bankruptButton);
            continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            playerInfoHeaderFragment.refresh();
        }
        if (game.getCurrentPlayer().getAvailableFunds() < 0) {
            endTurnButton.setVisibility(View.GONE);
            forfeitButton.setVisibility(View.VISIBLE);
        }
    }

}
