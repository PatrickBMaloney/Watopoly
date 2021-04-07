 package com.example.watopoly.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.watopoly.R;
import com.example.watopoly.fragment.AllAssetsFragment;
import com.example.watopoly.fragment.PlayerInfoHeaderFragment;
import com.example.watopoly.fragment.MyAssetsFragment;
import com.example.watopoly.fragment.PropertyFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

public class ViewAssetsActivity extends AppCompatActivity {

    private Game gameState = Game.getInstance();
    private PlayerInfoHeaderFragment playerInfoHeaderFragment;
    View myAssets;
    View allAssets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assets);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        myAssets = (View) findViewById(R.id.myAssetsFragment);
        allAssets = (View) findViewById(R.id.allAssetsFragment);
        myAssets.setVisibility(View.VISIBLE);
        allAssets.setVisibility(View.GONE);
        setButtons();
        final FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragmentAssets);
        Player myPlayer = gameState.getCurrentPlayer();
        playerInfoHeaderFragment.setPlayer(myPlayer);
    }

    private void setButtons() {

        final ToggleButton myAssetsButton = (ToggleButton) findViewById(R.id.myAssetsButton);
        final ToggleButton allAssetsButton = (ToggleButton) findViewById(R.id.allAssetsButton);

        myAssetsButton.setChecked(true);
        allAssetsButton.setChecked(false);

        myAssetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAssetsButton.setChecked(true);
                allAssetsButton.setChecked(false);
                myAssets.setVisibility(View.VISIBLE);
                allAssets.setVisibility(View.GONE);
            }
        });
        allAssetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allAssetsButton.setChecked(true);
                myAssetsButton.setChecked(false);
                myAssets.setVisibility(View.GONE);
                allAssets.setVisibility(View.VISIBLE);
            }
        });
    }
}