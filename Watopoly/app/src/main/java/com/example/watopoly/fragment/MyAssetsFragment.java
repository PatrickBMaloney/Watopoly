package com.example.watopoly.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.activity.MainGameViewActivity;
import com.example.watopoly.activity.TradeSellPropertiesActivity;
import com.example.watopoly.activity.ViewAssetsActivity;
import com.example.watopoly.activity.BuyHouseHotelActivity;
import com.example.watopoly.adapter.PropertyListAdapter;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

public class MyAssetsFragment extends Fragment implements PropertyListAdapter.onPropClickListener{

    private Game gameState = Game.getInstance();
    View largeProp;
    View buttons;
    View buyHouseBtn;
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
        buyHouseBtn = (View) current.findViewById(R.id.buyHouseButtonAssets);
        largeProp.setVisibility(View.GONE);
        buttons.setVisibility(View.GONE);

        RecyclerView rv = (RecyclerView) current.findViewById(R.id.propRecycleView);
        rv.removeAllViews();
        PropertyListAdapter adapter = new PropertyListAdapter(getContext(),player.getProperties(), this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 6));
    }

    private void setButtons(final View root) {
        Button return_to_board = (Button) root.findViewById(R.id.back_to_board);
        Button trade_sell = (Button) root.findViewById(R.id.tradeSellButton);
        Button mortgage = (Button) root.findViewById(R.id.mortgageButtonAssets);
        Button buyHouseHotelBtn = (Button) root.findViewById(R.id.buyHouseButtonAssets);
        if(gameState.getCurrentPlayer().getJailed()) {
            trade_sell.setEnabled(false);
            mortgage.setEnabled(false);
            buyHouseHotelBtn.setEnabled(false);
        } else {
            trade_sell.setEnabled(true);
            mortgage.setEnabled(true);
            buyHouseHotelBtn.setEnabled(true);
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


    public void onPropClick(int propNum, int position) {
        final Property property = gameState.getCurrentPlayer().getProperties().get(propNum);
        final Player player = gameState.getCurrentPlayer();
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
            buyHouseBtn.setVisibility(View.VISIBLE);
            prev = property;
        }

        if (! (property instanceof Building)) {
            buyHouseBtn.setVisibility(View.GONE);
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

        final Button mortgage = buttons.findViewById(R.id.mortgageButtonAssets);
        if (property.getMortgaged()){
            mortgage.setText("Pay Off");
        } else {
            mortgage.setText("Mortgage");
        }

        mortgage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (property.getMortgaged()) {
                    property.unMortgage();
                    player.payAmount(property.getPurchasePrice() / 2);
                    mortgage.setText("Mortgage");
                } else {
                    property.mortgage();
                    player.receiveAmount(property.getPurchasePrice() / 2);
                    mortgage.setText("Pay Off");
                }
            }
        });
    }
}