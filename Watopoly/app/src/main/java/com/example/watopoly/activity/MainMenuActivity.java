package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.watopoly.R;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button btnNewGame = (Button) findViewById(R.id.newGameButton);
        Button btnContinueGame = (Button) findViewById(R.id.continueGameButton);
        btnNewGame.setOnClickListener(this);
        btnContinueGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO: figure out what happens when they press continue
        Intent intent = new Intent(this, NumberOfPlayersActivity.class);
        startActivity(intent);
    }
}