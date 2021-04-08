package com.example.watopoly.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.example.watopoly.R;
import com.example.watopoly.adapter.PropertyListAdapter;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import java.lang.reflect.Field;

public class MyAssetsFragment extends Fragment implements PropertyListAdapter.onPropClickListener{

    private Game gameState = Game.getInstance();
    View largeProp;
    View buttons;
    private Property prev;
    private int [] ids = new int[26];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_assets, container, false);

        largeProp = (View) root.findViewById(R.id.propertyCardBuyFragmentAssets);
        buttons = (View) root.findViewById(R.id.actionsLinearLayoutAssets);
        largeProp.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);
        setButtons(root);
        RecyclerView rv = (RecyclerView) root.findViewById(R.id.propRecycleView);
        PropertyListAdapter adapter = new PropertyListAdapter(getContext(),gameState.getCurrentPlayer().getProperties(), this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 6));
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

    @Override
    public void onPropClick(int propNum, int position) {
        Property property = gameState.getCurrentPlayer().getProperties().get(propNum);
        if(prev == null) {
            prev = property; //set prev to current if null
        }
        if (largeProp.getVisibility() == View.VISIBLE && prev==property) {
            largeProp.setVisibility(View.GONE);
            buttons.setVisibility(View.GONE);
        } else {
            FragmentManager fm = getChildFragmentManager();
            PropertyFragment propLarge = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyFragmentAssets); //get large prop
            propLarge.setProperty(property);
            largeProp.setVisibility(View.VISIBLE);
            buttons.setVisibility(View.VISIBLE);
            prev = property;
        }
    }
}