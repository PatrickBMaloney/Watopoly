package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.RollToGoFirstAdaptor;
import com.example.watopoly.fragment.DiceRollFragment;
import com.example.watopoly.fragment.FragmentCallbackListener;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;

import java.util.ArrayList;

public class RollToGoFirstActivity extends AppCompatActivity implements FragmentCallbackListener {
    private ArrayList<Integer> icons;
    private ArrayList<String> colours;
    private ArrayList<String> names;

    private ImageView playerIconImageView;
    private TextView diceRollResultTextView;
    private TextView playerRollingTextView;
    private Button nextPlayerDiceRollButton;
    private DiceRollFragment diceRollFragment;

    private int numberOfPlayers;
    private int currentTurn = 0;
    private ArrayList<Integer> rollValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_to_go_first);

        icons = getIntent().getIntegerArrayListExtra("pathSelected");
        colours = getIntent().getStringArrayListExtra("colourSelected");
        names = getIntent().getStringArrayListExtra("names");
        numberOfPlayers = names.size();

        linkView();
        playerDiceRoll();
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        playerIconImageView = findViewById(R.id.playerIconImageView);
        diceRollResultTextView = findViewById(R.id.diceRollResultTextView);
        nextPlayerDiceRollButton = findViewById(R.id.passToFirstPlayerBtn);
        playerRollingTextView = findViewById(R.id.playerRollingTextView);

        FragmentManager fm = getSupportFragmentManager();
        diceRollFragment= (DiceRollFragment) fm.findFragmentById(R.id.diceRollOrderFragment);
        diceRollFragment.setCallbackListener(this);

        nextPlayerDiceRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishRolling();
                diceRollFragment.setRollButtonHidden(false);
            }
        });
    }


    private void playerDiceRoll(){
        String passButtonText;
        if (currentTurn >= names.size()-1){
            passButtonText = "Continue";
        } else {
            passButtonText = "Pass to " + names.get(currentTurn+1);
        }
        nextPlayerDiceRollButton.setVisibility(View.GONE);
        nextPlayerDiceRollButton.setText(passButtonText);
        diceRollResultTextView.setText("?");
        playerRollingTextView.setText(names.get(currentTurn)+ " is rolling");
        playerIconImageView.setImageResource(icons.get(currentTurn));
        ImageViewCompat.setImageTintMode(playerIconImageView, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(playerIconImageView, ColorStateList.valueOf(Color.parseColor(colours.get(currentTurn))));
    }

    private void finishRolling(){
        if (currentTurn == numberOfPlayers-1) {
            Log.d("Rolls",rollValues.toString());
            Intent playerOrderIntent = new Intent(getApplicationContext(), DisplayPlayerOrderActivity.class);
            playerOrderIntent.putExtra("rolls", rollValues);
            playerOrderIntent.putExtra("names", names);
            playerOrderIntent.putExtra("icons", icons);
            playerOrderIntent.putExtra("colours", colours);
            startActivity(playerOrderIntent);
        } else {
            currentTurn++;
            playerDiceRoll();
        }
    }

    @Override
    public void onCallback() {
        int diceRollResult = diceRollFragment.getDiceRollResult();
        rollValues.add(diceRollResult);
        diceRollResultTextView.setText(""+diceRollResult);

        diceRollFragment.setRollButtonHidden(true);
        nextPlayerDiceRollButton.setVisibility(View.VISIBLE);
    }
}

