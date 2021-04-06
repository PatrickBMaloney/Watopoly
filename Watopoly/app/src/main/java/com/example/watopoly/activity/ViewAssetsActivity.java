package com.example.watopoly.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.watopoly.R;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.fragment.PropertyFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

public class ViewAssetsActivity extends AppCompatActivity {

    private Game gameState = Game.getInstance();
    private PlayerInfoHeaderFragment playerInfoHeaderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assets);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setButtons();
        linkView();
        Player myPlayer = gameState.getCurrentPlayer();
        playerInfoHeaderFragment.setPlayer(myPlayer);
    }

    private void setButtons() {

        Button return_to_board = (Button) findViewById(R.id.back_to_board);
        final ToggleButton myAssetsButton = (ToggleButton) findViewById(R.id.myAssetsButton);
        final ToggleButton allAssetsButton = (ToggleButton) findViewById(R.id.allAssetsButton);

        myAssetsButton.setChecked(true);
        allAssetsButton.setChecked(false);
        myAssetsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    allAssetsButton.setChecked(false);
                } else {
                    allAssetsButton.setChecked(true);
                }
            }
        });

        allAssetsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    myAssetsButton.setChecked(false);
                } else {
                    myAssetsButton.setChecked(true);
                }
            }
        });

        return_to_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void linkView() {
        FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragmentAssets);
    }

}