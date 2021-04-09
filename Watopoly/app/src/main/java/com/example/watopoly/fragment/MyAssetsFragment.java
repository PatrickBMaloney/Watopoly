package com.example.watopoly.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.activity.MainGameViewActivity;
import com.example.watopoly.activity.TradeSellPropertiesActivity;
import com.example.watopoly.activity.ViewAssetsActivity;
import com.example.watopoly.activity.BuyHouseHotelActivity;
import com.example.watopoly.adapter.CellPropertyListAdapter;
import com.example.watopoly.adapter.PropertyListAdapter;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

public class MyAssetsFragment extends Fragment implements CellPropertyListAdapter.OnPropClickListener{

    private Game gameState = Game.getInstance();
    View largeProp;
    View buttons;
    private Property prev;
    View current;
    boolean refresh = false;

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(refresh) {
            setView(gameState.getCurrentPlayer());
            refresh = false;
        }
    }
    private int [] ids = new int[26];
    private FragmentCallbackListener callbackListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_assets, container, false);
        current = root;
        setButtons(root);
        setView(gameState.getCurrentPlayer());
        return root;
    }
    private void setView(Player player) {
        largeProp = (View) current.findViewById(R.id.propertyCardBuyFragmentAssets);
        buttons = (View) current.findViewById(R.id.actionsLinearLayoutAssets);
        largeProp.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);

        setButtons(root);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.propRecycleView);
        CellPropertyListAdapter adapter = new CellPropertyListAdapter(gameState.getCurrentPlayer().getProperties(), true, this);
        adapter.setShowSelected(false);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerView.getContext(),  layoutManager.getOrientation());
        dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.empty_divider));
        recyclerView.addItemDecoration(dividerItemDecoration2);

        return root;
    }

    private void setButtons(final View root) {
        Button return_to_board = (Button) root.findViewById(R.id.back_to_board);
        Button trade_sell = (Button) root.findViewById(R.id.tradeSellButton);
        if(gameState.getCurrentPlayer().getJailed()) {
            trade_sell.setEnabled(false);
        } else {
            trade_sell.setEnabled(true);
        }
        return_to_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        trade_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), TradeSellPropertiesActivity.class);
                startActivityForResult(intent, 999);
            }
        });
    }

    public void setCallbackListener(FragmentCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackListener != null) {
            callbackListener.onCallback();
        }
    }


    public void onPropClick(final Property property) {
        if(prev == null) {
            prev = property; //set prev to current if null
        }
        if (largeProp.getVisibility() == View.VISIBLE && prev == property) {
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

        Button buyHouseHotelBtn = buttons.findViewById(R.id.buyHouseButtonAssets);
        buyHouseHotelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAssetsFragment.this.getActivity(), BuyHouseHotelActivity.class);
                intent.putExtra("property", property.getName());
                startActivityForResult(intent, 0);
            }
        });
    }
}