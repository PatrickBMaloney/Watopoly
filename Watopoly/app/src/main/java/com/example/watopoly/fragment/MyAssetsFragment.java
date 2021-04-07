package com.example.watopoly.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.watopoly.R;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import java.lang.reflect.Field;

public class MyAssetsFragment extends Fragment {

    private Game gameState = Game.getInstance();
    View largeProp;
    View buttons;
    private Property prev;
    private int [] ids = new int[20];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_assets, container, false);
        setButtons(root);
        addIdsToArray();
        linkView(root);
        return root;
    }

    private void setButtons(final View root) {
        Button return_to_board = (Button) root.findViewById(R.id.back_to_board);
        return_to_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void addIdsToArray() {
        ids[0] = R.id.propertyCardBuyFragment0;
        ids[1] = R.id.propertyCardBuyFragment1;
        ids[2] = R.id.propertyCardBuyFragment2;
        ids[3] = R.id.propertyCardBuyFragment3;
        ids[4] = R.id.propertyCardBuyFragment4;
        ids[5] = R.id.propertyCardBuyFragment5;
        ids[6] = R.id.propertyCardBuyFragment6;
        ids[7] = R.id.propertyCardBuyFragment7;
        ids[8] = R.id.propertyCardBuyFragment8;
        ids[9] = R.id.propertyCardBuyFragment9;
        ids[10] = R.id.propertyCardBuyFragment10;
        ids[11] = R.id.propertyCardBuyFragment11;
        ids[12] = R.id.propertyCardBuyFragment12;
        ids[13] = R.id.propertyCardBuyFragment13;
        ids[14] = R.id.propertyCardBuyFragment14;
        ids[15] = R.id.propertyCardBuyFragment15;
        ids[16] = R.id.propertyCardBuyFragment16;
        ids[17] = R.id.propertyCardBuyFragment17;
        ids[18] = R.id.propertyCardBuyFragment18;
        ids[19] = R.id.propertyCardBuyFragment19;
    }
    private void linkView(View root) {
        largeProp = (View) root.findViewById(R.id.propertyCardBuyFragmentAssets);
        buttons = (View) root.findViewById(R.id.actionsLinearLayoutAssets);
        //TO-DO: check for other properties (to display correctly)
        final FragmentManager fm = getChildFragmentManager();
        int size = gameState.getCurrentPlayer().getProperties().size(); //size of properties

        //will start setting all properties
        for(int i = 0; i < size; i++) {
            PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(ids[i]);
            final Property property = gameState.getCurrentPlayer().getProperties().get(i); //get the current property
            propertyFragment.setProperty(property);
            View prop = root.findViewById(ids[i]);
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
        for(int j = size; j < 20; j++) {
            View hideFrag = root.findViewById(ids[j]);
            if(hideFrag != null) {
                hideFrag.setVisibility(View.GONE);
            }
        }
        //hide large view
        largeProp.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
    }
}