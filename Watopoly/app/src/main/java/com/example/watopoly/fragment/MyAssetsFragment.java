package com.example.watopoly.fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.watopoly.R;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Property;

public class MyAssetsFragment extends Fragment {

    private Game gameState = Game.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_my_assets, container, false);
        FragmentManager fm = getChildFragmentManager();
        Button temp = (Button) root.findViewById(R.id.buyHouseButtonAssets);
        ConstraintLayout assetsLayout = (ConstraintLayout) root.findViewById(R.id.constraintMyAssets);
        assetsLayout.setVisibility(View.GONE); //set it to gone because on entry we don't need it


        //DO NOT DELETE
//        PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyFragmentAssets);
//        final Property property = gameState.getCurrentPlayer().getProperties().get(0); //just the first for now
//        propertyFragment.setProperty(property);
        return root;
    }
}