package com.example.watopoly.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.PropertyListTradeAdapter;
import com.example.watopoly.fragment.Dialog.InsufficientFundsRHS;
import com.example.watopoly.fragment.Dialog.InsufficientFundsYou;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import java.util.ArrayList;

public class TradeSellStartFragment extends Fragment implements PropertyListTradeAdapter.onChoosePropListener, AdapterView.OnItemSelectedListener {

    //requester 0 = asking for request
    //requester 1 = person whom we are requesting trade
    ArrayList<Property> propGive = new ArrayList<>();
    ArrayList<Property> propTake = new ArrayList<>();

    String moneyGive = "0";
    String moneyTake = "0";

    Spinner spinnerNames;

    private Game gameState = Game.getInstance();

    String [] playerNames;
    Player[] playerOrder;

    private View current;

    int playerPos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trade_resources_start, container, false);
        current = root;
        int size = gameState.getPlayers().size();
        if(size > 1) {
            int addPlayer = 0;
            playerNames = new String[size - 1];
            playerOrder = new Player[size - 1];
            for (int i = 0; i < size; i++) {
                if (gameState.getPlayers().get(i) != gameState.getCurrentPlayer()) {
                    playerNames[addPlayer] = gameState.getPlayers().get(i).getName();
                    playerOrder[addPlayer] = gameState.getPlayers().get(i);
                    addPlayer++;
                }
            }
        } else {
            playerNames = new String[0];
            playerOrder = new Player[0];
        }
        spinnerNames = (Spinner) root.findViewById(R.id.playerNamesSpinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, playerNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNames.setAdapter(arrayAdapter);
        spinnerNames.setOnItemSelectedListener(this);


        final EditText moneyOffer = (EditText) root.findViewById(R.id.inputMoneyP0);
        final EditText moneyWant = (EditText) root.findViewById(R.id.inputMoneyP1);
        moneyOffer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                moneyGive = s.toString();
                if(moneyGive.isEmpty()) {
                    moneyGive = "0";
                }
                if(Integer.parseInt(moneyGive) > gameState.getCurrentPlayer().getMoney()) {
                    InsufficientFundsYou Ify = new InsufficientFundsYou();
                    Ify.show(getChildFragmentManager(), "InsufficientFundsYou");
                }
            }
        });
        moneyWant.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                moneyTake = s.toString();
                if(moneyTake.isEmpty()) {
                    moneyTake = "0";
                }
                if(Integer.parseInt(moneyTake) > playerOrder[playerPos].getMoney()) {
                    InsufficientFundsRHS Ifo = new InsufficientFundsRHS(playerNames[playerPos]);
                    Ifo.show(getChildFragmentManager(), "InsufficientFundsOther");
                }
            }
        }));

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.offerPropRecyclerView);
        PropertyListTradeAdapter adapter = new PropertyListTradeAdapter(getContext(), gameState.getCurrentPlayer().getProperties(), 0, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        if(size > 1) {
            RecyclerView recyclerViewRHS = (RecyclerView) root.findViewById(R.id.takePropRecyclerView);
            PropertyListTradeAdapter adapterRHS = new PropertyListTradeAdapter(getContext(), playerOrder[0].getProperties(), 1, this);
            recyclerViewRHS.setAdapter(adapterRHS);
            recyclerViewRHS.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        Button requestTrade = (Button) root.findViewById(R.id.playerRequestTrade);
        requestTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(moneyGive) > gameState.getCurrentPlayer().getMoney()) {
                    InsufficientFundsYou Ify = new InsufficientFundsYou();
                    Ify.show(getChildFragmentManager(), "InsufficientFundsYou");
                }
                else if(Integer.parseInt(moneyTake) > playerOrder[playerPos].getMoney()) {
                    InsufficientFundsRHS Ifo = new InsufficientFundsRHS(playerNames[playerPos]);
                    Ifo.show(getChildFragmentManager(), "InsufficientFundsOther");
                }
            }
        });
        return root;
    }

    @Override
    public void onChoosePropClick(int player, Property property, boolean addProp) {
        //player giving their property
        if(player == 0) {
            if(addProp) {
                propGive.add(property);
            } else {
                propGive.remove(property);
            }
        } else {
            if(addProp) {
                propTake.add(property);
            } else {
                propTake.remove(property);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.playerNamesSpinner) {
            RecyclerView recyclerViewRHS = (RecyclerView) current.findViewById(R.id.takePropRecyclerView);
            PropertyListTradeAdapter adapterRHS = new PropertyListTradeAdapter(getContext(), playerOrder[position].getProperties(), 1, this);
            recyclerViewRHS.setAdapter(adapterRHS);
            recyclerViewRHS.setLayoutManager(new GridLayoutManager(getContext(), 4));
            playerPos = position;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
