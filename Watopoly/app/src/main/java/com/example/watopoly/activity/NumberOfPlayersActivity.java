package com.example.watopoly.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.watopoly.R;

public class NumberOfPlayersActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_num_players);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button btnOnePlayer = (Button) findViewById(R.id.numPlayersButton1);
        Button btnTwoPlayers = (Button) findViewById(R.id.numPlayersButton2);
        Button btnThreePlayers = (Button) findViewById(R.id.numPlayersButton3);
        Button btnFourPlayers = (Button) findViewById(R.id.numPlayersButton4);
        btnOnePlayer.setOnClickListener(this);
        btnTwoPlayers.setOnClickListener(this);
        btnThreePlayers.setOnClickListener(this);
        btnFourPlayers.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, EnterPlayerInfoActivity.class);

        switch(view.getId()) {
            case R.id.numPlayersButton1:
                intent.putExtra("numberOfPlayers", 1);
                break;
            case R.id.numPlayersButton2:
                intent.putExtra("numberOfPlayers", 2);
                break;
            case R.id.numPlayersButton3:
                intent.putExtra("numberOfPlayers", 3);
                break;
            case R.id.numPlayersButton4:
                intent.putExtra("numberOfPlayers", 4);
                break;
            default:
        }
        startActivity(intent);
    }
}
