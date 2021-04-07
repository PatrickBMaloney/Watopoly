package com.example.watopoly.activity;

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
import androidx.fragment.app.FragmentManager;

import com.example.watopoly.R;
import com.example.watopoly.fragment.DiceRollFragment;
import com.example.watopoly.fragment.FragmentCallbackListener;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.model.ChanceCard;
import com.example.watopoly.model.ChanceCardStrategy.ChanceStrategy;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.util.ChanceCards;

public class JailOptionsActivity extends AppCompatActivity implements FragmentCallbackListener {
    private static final Double jailFine = 100.0;
    private static final String freeMessage ="You are Free!";

    private DiceRollFragment diceRollFragment;
    private TextView titleTextView;
    private Button rollDoubleButton;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jail_options);
        linkView();
    }

    private void linkView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        FragmentManager fm = getSupportFragmentManager();
        diceRollFragment = (DiceRollFragment) fm.findFragmentById(R.id.diceRollJailFragment);
        diceRollFragment.setCallbackListener(this);
        diceRollFragment.getView().setVisibility(View.GONE);
        final Game game = Game.getInstance();

        titleTextView = findViewById(R.id.jailOptionTitleTextView);
        linearLayout = findViewById(R.id.jailOptionLinearLayout);
        rollDoubleButton = findViewById(R.id.jailRollButton);
        Button payFineButton = findViewById(R.id.jailFineButton);
        Button jailFreeCardButton = findViewById(R.id.jailFreeCardButton);
        Button continueButton = findViewById(R.id.jailContinueButton);

        jailFreeCardButton.setVisibility(game.getCurrentPlayer().getNumberOfJailFreeCards() > 0 ? View.VISIBLE : View.GONE);
        payFineButton.setVisibility(game.getCurrentPlayer().getMoney() >= jailFine ? View.VISIBLE : View.GONE);

        jailFreeCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.getCurrentPlayer().useJailFreeCard();
                titleTextView.setText(freeMessage);
            }
        });

        payFineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.getCurrentPlayer().payAmount(jailFine);
                game.getCurrentPlayer().setJailed(false);
                titleTextView.setText(freeMessage);
            }
        });

        rollDoubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diceRollFragment.getView().setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onCallback() {
        Log.d("Rolled", ""+diceRollFragment.getIsDouble());
        diceRollFragment.setRollButtonVisibility(View.INVISIBLE);
        if (diceRollFragment.getIsDouble()) {
            Game game = Game.getInstance();
            game.getCurrentPlayer().setJailed(false);
            titleTextView.setText(freeMessage);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                diceRollFragment.getView().setVisibility(View.GONE);
                rollDoubleButton.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }, 1500);
    }
}

