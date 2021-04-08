package com.example.watopoly.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.PropertyListAdapter;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Property;

public class MyAssetsFragment extends Fragment implements PropertyListAdapter.onPropClickListener{

    private Game gameState = Game.getInstance();
    View largeProp;
    PropertyFragment propertyFragment;
    View buttons;
    private Property prev;
    private int [] ids = new int[26];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_assets, container, false);

        final FragmentManager fm = getChildFragmentManager();
        propertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyHouseHotels);
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
    public void onPropClick(int propNum) {
        final Property property = gameState.getCurrentPlayer().getProperties().get(propNum);
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

        Button buyHouseHotelBtn = buttons.findViewById(R.id.buyHouseButtonAssets);
        buyHouseHotelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(), R.style.Theme_Dialog);
                dialog.setContentView(R.layout.dialog_buy_house_hotel);

                final FragmentManager fm = getChildFragmentManager();
//                final PropertyFragment buyHousePropertyFragment = (PropertyFragment) fm.findFragmentById(R.id.propertyCardBuyHouseHotels);
                final Building currentTile = (Building) property;
                propertyFragment.setProperty(currentTile);
                final Button plusHouseButton = dialog.findViewById(R.id.plusBuyHouseButton);
                final Button minusHouseButton = dialog.findViewById(R.id.minusBuyHouseButton);
                final Button plusHotelButton = dialog.findViewById(R.id.plusBuyHotelButton);
                final Button minusHotelButton = dialog.findViewById(R.id.minusBuyHotelButton);
                final TextView currentHousesAndHotels = dialog.findViewById(R.id.currrentHousesAndHotels);
                Button confirmNumHouses = dialog.findViewById(R.id.confirmNumHouses);
                final double housePrice = currentTile.getHousePrice();
                final int currentNumHouses = currentTile.getNumberOfHouses();

                // if they already have all the houses don't show any incrementers
                if (currentTile.getNumberOfHouses() == 4 && currentTile.isHasHotel()){
                    dialog.findViewById(R.id.buyHouseIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.buyHotelIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.currrentHousesAndHotels).setVisibility(View.GONE);
                    TextView title = dialog.findViewById(R.id.buyPropertyDescriptionTextView);
                    title.setPadding(0, 100, 0, 0);
                    title.setTextSize(20);
                    title.setText("You already have 4 houses and a hotel!");
                }

                // if they don't have enough money for anything don't show any incrementers
                if (gameState.getCurrentPlayer().getMoney() < housePrice){
                    dialog.findViewById(R.id.buyHouseIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.buyHotelIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.currrentHousesAndHotels).setVisibility(View.GONE);
                    TextView title = dialog.findViewById(R.id.buyPropertyDescriptionTextView);
                    title.setPadding(0, 100, 0, 0);
                    title.setTextSize(20);
                    title.setLines(3);
                    title.setText("Insufficient funds. You are unable to purchase any properties");
                }

                // if they don't have a full set don't show the incrementers
                if (gameState.getCurrentPlayer().getMoney() < housePrice){
                    dialog.findViewById(R.id.buyHouseIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.buyHotelIncrementer).setVisibility(View.GONE);
                    dialog.findViewById(R.id.currrentHousesAndHotels).setVisibility(View.GONE);
                    TextView title = dialog.findViewById(R.id.buyPropertyDescriptionTextView);
                    title.setPadding(0, 100, 0, 0);
                    title.setTextSize(20);
                    title.setLines(3);
                    title.setText("You do not possess a full set. You are unable to purchase any properties.");
                }

                // if they can't afford a hotel, don't show the hotel incrementer
                if (currentNumHouses + gameState.getCurrentPlayer().getMoney()/housePrice < 5){
                    LinearLayout hotelIncrementer = dialog.findViewById(R.id.buyHotelIncrementer);
                    hotelIncrementer.setVisibility(View.INVISIBLE);
                }

                currentHousesAndHotels.setText(String.format("You currently have: %s houses, %s hotels",
                        currentTile.getNumberOfHouses(),
                        currentTile.isHasHotel() ? 1 : 0));

                plusHouseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView numHousesText = dialog.findViewById(R.id.numHouseButton);
                        int numHouses = Integer.parseInt(numHousesText.getText().toString()) + 1;

                        String numHousesAmount = String.valueOf(numHouses);
                        numHousesText.setText("" + numHousesAmount);
                        enableIncrementer(minusHouseButton);
                        // you can purchase as many houses as you can afford or the number of houses to get a full set
                        int maxNumberOfPurchasableHouses = Math.min((int)(gameState.getCurrentPlayer().getMoney()/housePrice), 4 - currentNumHouses);
                        if (numHouses == maxNumberOfPurchasableHouses){
                            disableIncrementer(plusHouseButton);
                        }

                        if (numHouses + currentNumHouses == 4){
                            enableIncrementer(plusHotelButton);
                        }
                    }
                });

                minusHouseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView numHousesText = dialog.findViewById(R.id.numHouseButton);
                        int numHouses = Integer.parseInt(numHousesText.getText().toString()) - 1;

                        String numHousesAmount = String.valueOf(numHouses);
                        numHousesText.setText("" + numHousesAmount);
                        enableIncrementer(plusHouseButton);

                        if (numHouses == 0){
                            disableIncrementer(minusHouseButton);
                        }

                        if (numHouses != 4){
                            disableIncrementer(minusHotelButton);
                            disableIncrementer(plusHotelButton);
                            TextView numHotels = dialog.findViewById(R.id.numHotelsButton);
                            numHotels.setText("0");
                        }
                    }
                });

                plusHotelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        disableIncrementer(plusHotelButton);
                        enableIncrementer(minusHotelButton);
                        TextView numHotels = dialog.findViewById(R.id.numHotelsButton);
                        numHotels.setText("1");
                    }
                });

                minusHotelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        disableIncrementer(minusHotelButton);
                        enableIncrementer(plusHotelButton);
                        TextView numHotels = dialog.findViewById(R.id.numHotelsButton);
                        numHotels.setText("0");
                    }
                });

                confirmNumHouses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView numHousesText = dialog.findViewById(R.id.numHouseButton);
                        int numHouses = Integer.parseInt(numHousesText.getText().toString());

                        TextView numHotelText = dialog.findViewById(R.id.numHotelsButton);
                        int numHotels = Integer.parseInt(numHotelText.getText().toString());

                        currentTile.buyHouses(numHouses);

                        if (numHotels == 1){
                            currentTile.buyHotel();
                        }

                        dialog.dismiss();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.remove(propertyFragment);
                        fragmentTransaction.commit();
                    }
                });

                dialog.show();
            }
        });

    }

    public void disableIncrementer(Button disabledButton){
        disabledButton.setTextColor(Color.parseColor("#000000"));
        disabledButton.setBackgroundColor(Color.parseColor("#d7d7d7"));
        disabledButton.setEnabled(false);
    }

    public void enableIncrementer(Button enabledButton){
        enabledButton.setTextColor(Color.parseColor("#ffffff"));
        enabledButton.setBackgroundColor(Color.parseColor("#4ac1f0"));
        enabledButton.setEnabled(true);
    }
}