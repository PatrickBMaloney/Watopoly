package com.example.watopoly.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.watopoly.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button btnNewGame = findViewById(R.id.newGameButton);
        Button btnContinueGame = findViewById(R.id.continueGameButton);
        Button btnJoinGame = (Button) findViewById(R.id.joinGameButton);
        btnContinueGame.setVisibility(View.GONE);
        btnNewGame.setOnClickListener(this);
        btnContinueGame.setOnClickListener(this);
        btnJoinGame.setOnClickListener(this);

        try  {
            FileInputStream inputStream = openFileInput("savedGameState");
            btnContinueGame.setVisibility(View.VISIBLE);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.continueGameButton) {
            intent = new Intent(this, MainGameViewActivity.class);
            intent.putExtra("continue","");
        }  else if (view.getId() == R.id.joinGameButton) {
            intent = new Intent(this, ShowRoomsActivity.class);
        } else {
            intent = new Intent(this, CreateRoomActivity.class);
        }

        startActivity(intent);
    }
}