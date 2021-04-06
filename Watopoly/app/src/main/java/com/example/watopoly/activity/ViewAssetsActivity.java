package com.example.watopoly.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
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
    View largeProp;
    View buttons;
    private Property prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assets);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        largeProp = (View) findViewById(R.id.propertyCardBuyFragmentAssets);
        buttons = (View) findViewById(R.id.actionsLinearLayoutAssets);
//        setButtons();
//        linkView();
        final FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragmentAssets);
        Player myPlayer = gameState.getCurrentPlayer();
        playerInfoHeaderFragment.setPlayer(myPlayer);
    }

    private void setButtons() {

        Button return_to_board = (Button) findViewById(R.id.back_to_board);
        final ToggleButton myAssetsButton = (ToggleButton) findViewById(R.id.myAssetsButton);
        final ToggleButton allAssetsButton = (ToggleButton) findViewById(R.id.allAssetsButton);

        myAssetsButton.setChecked(true);
        allAssetsButton.setChecked(false);
        myAssetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    myAssetsButton.setChecked(true);
                    allAssetsButton.setChecked(false);
                    //only call if switching from allAssets to your assets
                    if(!myAssetsButton.isChecked()) {
                        linkView();
                    }
            }
        });

        allAssetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allAssetsButton.setChecked(true);
                myAssetsButton.setChecked(false);
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
        //TO-DO: check for other properties (to display correctly)
        final FragmentManager fm = getSupportFragmentManager();
        playerInfoHeaderFragment = (PlayerInfoHeaderFragment) fm.findFragmentById(R.id.playerInfoHeaderFragmentAssets);
        int size = gameState.getCurrentPlayer().getProperties().size(); //size of properties

        //will start setting all properties
        for(int i = 0; i < size; i++) {
            String frag = "propertyCardBuyFragment" + String.valueOf(i);
            int resourceViewID = getResources().getIdentifier(frag, "id", getPackageName());
            PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(resourceViewID);
            final Property property = gameState.getCurrentPlayer().getProperties().get(i); //get the current property
            propertyFragment.setProperty(property);
            View prop = findViewById(resourceViewID);
            prop.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //populate large version
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            if(prev == null) {
                                prev = property; //set prev to current if null
                            }
                            if (largeProp.getVisibility() == View.VISIBLE && prev==property) {
                                largeProp.setVisibility(View.GONE);
                                buttons.setVisibility(View.GONE);
                            } else {
                                PropertyFragment propLarge = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyFragmentAssets); //get large prop
                                propLarge.setProperty(property);
                                largeProp.setVisibility(View.VISIBLE);
                                buttons.setVisibility(View.VISIBLE);
                                prev = property;
                            }
                        }
                        default:
                    }
                    return true;
                }
            });
        }

        //set View to none for rest
        for(int j = size; j < 28; j++) {
            String frag = "propertyCardBuyFragment" + String.valueOf(j);
            int resourceViewID = getResources().getIdentifier(frag, "id", getPackageName());
            View hideFrag = findViewById(resourceViewID);
            hideFrag.setVisibility(View.GONE);
        }
        //hide large view
        largeProp.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
    }

}