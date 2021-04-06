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

public class myAssetsFragment extends Fragment {

    private Game gameState = Game.getInstance();
    View largeProp;
    View buttons;
    private Property prev;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_assets, container, false);
        setButtons(root);
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

    private void linkView(View root) {
        largeProp = (View) root.findViewById(R.id.propertyCardBuyFragmentAssets);
        buttons = (View) root.findViewById(R.id.actionsLinearLayoutAssets);
        //TO-DO: check for other properties (to display correctly)
        final FragmentManager fm = getChildFragmentManager();
        int size = gameState.getCurrentPlayer().getProperties().size(); //size of properties

        //will start setting all properties
        for(int i = 0; i < size; i++) {
            String frag = "propertyCardBuyFragment" + String.valueOf(i);
            int resourceViewID = getResources().getIdentifier(frag, "id", requireActivity().getPackageName());
            PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(resourceViewID);
            final Property property = gameState.getCurrentPlayer().getProperties().get(i); //get the current property
            propertyFragment.setProperty(property);
            View prop = root.findViewById(resourceViewID);
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
            int resourceViewID = getResources().getIdentifier(frag, "id", requireActivity().getPackageName());
            View hideFrag = root.findViewById(resourceViewID);
            hideFrag.setVisibility(View.GONE);
        }
        //hide large view
        largeProp.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
    }
}