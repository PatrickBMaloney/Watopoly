package com.example.watopoly.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.watopoly.R;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;

public class ChanceCardActivity extends AppCompatActivity {

    private ImageView chanceCardImg;
    private TextView chanceDescription;
    private TextView buttonTextView;
    private TextView chanceCardTitle;
    private Game gameState = Game.getInstance();
    private PlayerInfoHeaderFragment playerInfoHeaderFragment;
    private boolean continueToBoard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.land_on_chance_card);
        linkView();
        setup();
        Player myPlayer = gameState.getCurrentPlayer();
        playerInfoHeaderFragment.setPlayer(myPlayer);
    }

    private void setup() {
        Intent intent = getIntent();
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragmentChance);
        Button drawCard = findViewById(R.id.drawCard);
        chanceCardImg = findViewById(R.id.imageChanceCard);
        chanceDescription = findViewById(R.id.chanceTitle);
        buttonTextView = findViewById(R.id.drawCard);
        chanceCardTitle = findViewById(R.id.chanceCardAction);
        drawCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(continueToBoard){
                    // Todo: execute card action
                    Intent intent = new Intent(getApplicationContext(), MainGameViewActivity.class);
                    intent.putExtra("continue", true);
                    startActivity(intent);
                }
                else {
                    ChanceCard drawnCard = drawRandomChanceCard();
                    chanceCardImg.setImageResource(R.drawable.chance_front);
                    String chanceDescription = drawnCard.getDescription();
                    String chanceTitle = drawnCard.getTitle();
                    String buttonText = "CONTINUE";
                    ChanceCardActivity.this.chanceDescription.setText(chanceDescription);
                    buttonTextView.setText(buttonText);
                    chanceCardTitle.setText(chanceTitle);
                    continueToBoard = true;
                }
            }
        });
    }

    private ChanceCard drawRandomChanceCard(){
        // Todo: Write the different chance cards
        return new ChanceCard("Card title", "Description");
    }
}
