package com.example.watopoly.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.watopoly.R;
import com.example.watopoly.fragment.DiceRollFragment;
import com.example.watopoly.fragment.FragmentCallbackListener;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Tile;
import com.example.watopoly.view.BoardView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
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
            new LoadData(this).execute();
            return;
        }

        //Maybe move this into one func?
        linkView();
        setup();
        startTurn();
    }

    private void startTurn() {
        new SaveData(this).execute();
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
        Button buyButton = findViewById(R.id.buyPropertyButton);
        buyButton.setVisibility(View.GONE);
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

    //FragmentCallbackListener diceRolled
    @Override
    public void onCallback() {
        //TODO: move the player
        int diceRollResult = diceRollFragment.getDiceRollResult();

        diceRollFragment.getView().setVisibility(View.GONE);
        actionLinearLayout.setVisibility(View.VISIBLE);
    }


    //Background tasks
    public static class SaveData extends AsyncTask<Void, Void, Void> {
        private WeakReference<MainGameViewActivity> activityWeakReference;
        SaveData(MainGameViewActivity mainGameViewActivity) {
            activityWeakReference = new WeakReference<>(mainGameViewActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainGameViewActivity mainGameViewActivity = activityWeakReference.get();
            if (mainGameViewActivity == null || mainGameViewActivity.isFinishing()) return null;

            try {
                FileOutputStream outputStream = mainGameViewActivity.openFileOutput("savedGameState", Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(Game.getInstance()); //May need thread safety
                objectOutputStream.close();
                Log.d("Save", "Game saved");
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class LoadData extends AsyncTask<Void, Void, Void> {
        private WeakReference<MainGameViewActivity> activityWeakReference;

        LoadData(MainGameViewActivity mainGameViewActivity){
            activityWeakReference = new WeakReference<>(mainGameViewActivity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                MainGameViewActivity mainGameViewActivity = activityWeakReference.get();
                if (mainGameViewActivity == null || mainGameViewActivity.isFinishing()) return null;

                FileInputStream inputStream = mainGameViewActivity.openFileInput("savedGameState");
                Game.loadGame(inputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainGameViewActivity mainActivity = activityWeakReference.get();
            if (mainActivity == null || mainActivity.isFinishing()) return;

            mainActivity.linkView();
            mainActivity.setup();
            mainActivity.startTurn();
        }
    }
}
