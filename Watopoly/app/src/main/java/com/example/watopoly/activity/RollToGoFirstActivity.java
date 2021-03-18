package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.RollToGoFirstAdaptor;

import java.util.ArrayList;

public class RollToGoFirstActivity extends AppCompatActivity {

//    Get info from "Enter Player Info"
    private ArrayList<Integer> icons;
    private ArrayList<String> colours;
    private ArrayList<String> names;

    private RollToGoFirstAdaptor shapeAdapter;
    private RecyclerView shapeRecyclerView;

    private ImageView playerIconImageView;
    private TextView diceRollResultTextView;
    private Button nextPlayerDiceRollButton;
    private Button rollToGoFirstBtn;
    private ImageView dice1;
    private ImageView dice2;

    private int numberOfPlayers;
    private int currentTurn = 0;
    private Integer roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_to_go_first);
        getSupportActionBar().hide();

        icons = getIntent().getIntegerArrayListExtra("pathSelected");
        colours = getIntent().getStringArrayListExtra("colourSelected");
        names = getIntent().getStringArrayListExtra("names");
        numberOfPlayers = names.size();

//        linkView();

        playerIconImageView = (ImageView) findViewById(R.id.playerIconImageView);
        diceRollResultTextView = (TextView) findViewById(R.id.diceRollResultTextView);
        rollToGoFirstBtn = (Button) findViewById(R.id.rollToGoFirstBtn);
        nextPlayerDiceRollButton = (Button) findViewById(R.id.nextPlayerDiceRollButton);
        dice1 = (ImageView) findViewById(R.id.dice1ImageView);
        dice2 = (ImageView) findViewById(R.id.dice2ImageView);

        playerDiceRoll();

        rollToGoFirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO move this to a global function to be accessed for any dice-rolling purpose
                int num1 = (int)(Math.random()*6+1);
                int num2 = (int)(Math.random()*6+1);
                roll = num1+num2;
                String rollValue = roll.toString();
                diceRollResultTextView.setText(rollValue);
//                shapeAdapter = new RollToGoFirstAdaptor(icons, colours, roll);
//                shapeRecyclerView.setAdapter(shapeAdapter);
                Drawable dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice4, null);
                Drawable dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice4, null);

                switch (num1) {
                    case 1:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice1, null);
                        break;
                    case 2:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice2, null);
                        break;
                    case 3:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice3, null);
                        break;
                    case 4:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice4, null);
                        break;
                    case 5:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice5, null);
                        break;
                    case 6:
                        dice1File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice6, null);
                        break;
                }

                switch (num2) {
                    case 1:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice1, null);
                        break;
                    case 2:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice2, null);
                        break;
                    case 3:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice3, null);
                        break;
                    case 4:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice3, null);
                        break;
                    case 5:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice5, null);
                        break;
                    case 6:
                        dice2File = ResourcesCompat.getDrawable(getResources(),R.drawable.dice6, null);
                        break;
                }
                dice1.setImageDrawable(dice1File);
                dice2.setImageDrawable(dice2File);
                nextPlayerDiceRollButton.setClickable(true);
                rollToGoFirstBtn.setVisibility(View.GONE);

            }
        });
        nextPlayerDiceRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishRolling();
                rollToGoFirstBtn.setVisibility(View.VISIBLE);
            }
        });

    }

//    private void linkView(){
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
//        shapeRecyclerView = findViewById(R.id.shapeOptionRecyclerView);
//        shapeRecyclerView.setLayoutManager(layoutManager1);
//        shapeAdapter = new RollToGoFirstAdaptor(icons, colours);
//        shapeRecyclerView.setAdapter(shapeAdapter);
//    }

    private void playerDiceRoll(){
        String passButtonText = "Pass to "+names.get(currentTurn);
        nextPlayerDiceRollButton.setText(passButtonText);
        nextPlayerDiceRollButton.setClickable(false);
        diceRollResultTextView.setText("?");
        playerIconImageView.setImageResource(icons.get(currentTurn));
        ImageViewCompat.setImageTintMode(playerIconImageView, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(playerIconImageView, ColorStateList.valueOf(Color.parseColor(colours.get(currentTurn))));
    }

    private void finishRolling(){
//        //save data
//        pathSelected.add(shapeAdapter.getPaths().get(shapeAdapter.getSelected()));
//        colourSelected.add(colourAdapter.getColours().get(colourAdapter.getSelected()));
//        names.add(nameEditText.getText().toString());
//
//        shapeAdapter.getPaths().remove(shapeAdapter.getSelected());
//        shapeAdapter.getColours().remove(shapeAdapter.getSelected());
//        colourAdapter.getPaths().remove(colourAdapter.getSelected());
//        colourAdapter.getColours().remove(colourAdapter.getSelected());
        if (currentTurn == numberOfPlayers) {
            //TODO GO TO NEXT INTENT
//            Intent diceRollIntent = new Intent(getApplicationContext(), RollToGoFirstActivity.class);
//            diceRollIntent.putExtra("pathSelected", icons);
//            diceRollIntent.putExtra("colourSelected", colours);
//            diceRollIntent.putExtra("names", names);
//            startActivity(diceRollIntent);
        } else {
            currentTurn ++;
            playerDiceRoll();
        }
    }
}

