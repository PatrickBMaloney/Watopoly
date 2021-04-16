package com.example.watopoly.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.CellPropertyListAdapter;
import com.example.watopoly.fragment.Dialog.InsufficientFundsRHS;
import com.example.watopoly.fragment.Dialog.InsufficientFundsYou;
import com.example.watopoly.fragment.Dialog.NoResourcesSelected;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TradeSellStartFragment extends Fragment implements  AdapterView.OnItemSelectedListener {
    CellPropertyListAdapter currentPlayerAdapter;
    CellPropertyListAdapter otherPlayerAdapter;

    ArrayList<Property> otherPlayerProperties = new ArrayList<>();

    Spinner spinnerNames;

    private Game gameState = Game.getInstance();

    String [] playerNames;
    Player[] playerOrder;

    private View current;

    int playerPos = 0;

    ConstraintLayout parentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trade_resources_start, container, false);
        parentLayout = (ConstraintLayout) root.findViewById(R.id.trade_sell_start);
        current = root;
        ImageView requesterImage = (ImageView) root.findViewById(R.id.playerIconImageView);
        requesterImage.setImageResource(gameState.getCurrentPlayer().getIcon());
        ImageViewCompat.setImageTintMode(requesterImage, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(requesterImage, ColorStateList.valueOf(Color.parseColor(gameState.getCurrentPlayer().getColour())));

        TextView requesterName = (TextView) root.findViewById(R.id.requesterName);
        requesterName.setText(gameState.getCurrentPlayer().getName());

        TextView requesterBalance = (TextView) root.findViewById(R.id.requesterBalance);
        requesterBalance.setText("Balance: $" + gameState.getCurrentPlayer().getMoney().toString());

        Button return_to_board = (Button) root.findViewById(R.id.back_to_board);
        return_to_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //if player in jail they shouldn't be allowed to change
        int tradablePlayers = 0;
        for(int i = 0; i < gameState.getPlayers().size(); i++) {
            if(gameState.getPlayers().get(i) != gameState.getCurrentPlayer() &&
            !gameState.getPlayers().get(i).getJailed()) {
                tradablePlayers++;
            }
        }
        if(tradablePlayers >= 1) {
            int addPlayer = 0;
            playerNames = new String[tradablePlayers];
            playerOrder = new Player[tradablePlayers];
            for (int i = 0; i < gameState.getPlayers().size(); i++) {
                if (gameState.getPlayers().get(i) != gameState.getCurrentPlayer() &&
                        !gameState.getPlayers().get(i).getJailed()) {
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

        final EditText moneyOfferEditText = (EditText) root.findViewById(R.id.inputMoneyP0);
        final EditText moneyWantEditText = (EditText) root.findViewById(R.id.inputMoneyP1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.offerPropRecyclerView);
        currentPlayerAdapter= new CellPropertyListAdapter(gameState.getCurrentPlayer().getProperties(), false, null);
        recyclerView.setAdapter(currentPlayerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),  layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.empty_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        Button requestTrade = (Button) root.findViewById(R.id.playerRequestTrade);
        if(tradablePlayers == 0) {
            requestTrade.setEnabled(false);
        }
        requestTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: try catch?
                int moneyGive = 0;
                int moneyTake = 0;

                if (!moneyOfferEditText.getText().toString().equals("")) moneyGive = Integer.parseInt(moneyOfferEditText.getText().toString());
                if (!moneyWantEditText.getText().toString().equals("")) moneyTake = Integer.parseInt(moneyWantEditText.getText().toString());

                ArrayList<Property> propGive = new ArrayList<>();
                ArrayList<Property> propTake = new ArrayList<>();

                ArrayList<Property> currentPlayerProperties = Game.getInstance().getCurrentPlayer().getProperties();

                for (int x = 0; x < currentPlayerProperties.size(); x++) {
                    if (currentPlayerAdapter.getSelected().get(x)) propGive.add(currentPlayerProperties.get(x));
                }
                for (int x = 0; x < otherPlayerProperties.size(); x++) {
                    if (otherPlayerAdapter.getSelected().get(x)) propTake.add(otherPlayerProperties.get(x));
                }

                if(moneyGive > 0 && moneyGive > gameState.getCurrentPlayer().getMoney()) {
                    InsufficientFundsYou Ify = new InsufficientFundsYou();
                    Ify.show(getChildFragmentManager(), "InsufficientFundsYou");
                }
                else if(moneyTake > playerOrder[playerPos].getMoney()) {
                    InsufficientFundsRHS Ifo = new InsufficientFundsRHS(playerNames[playerPos]);
                    Ifo.show(getChildFragmentManager(), "InsufficientFundsOther");
                } else if (moneyGive == 0 && moneyTake == 0
                        && propGive.size() == 0 && propTake.size() == 0) {
                    NoResourcesSelected noResource = new NoResourcesSelected();
                    noResource.show(getChildFragmentManager(), "no Resources");
                }
                else {
                    //okay to move on
                    final Dialog dialog = new Dialog(getContext(),R.style.Theme_Dialog);
                    dialog.setContentView(R.layout.dialog_pass_to_requested_player);
                    TextView passToPlayer = (TextView) dialog.findViewById(R.id.passToPlayer);
                    passToPlayer.setText("Pass Phone to " + playerNames[playerPos]);
                    Button continuePlayerButton = dialog.findViewById(R.id.continuePlayerButton);
                    continuePlayerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                   TradeSellRequestSentFragment showFinalTrade = new TradeSellRequestSentFragment(playerOrder[playerPos],
                           moneyGive, moneyTake, propGive, propTake);
                   parentLayout.removeAllViews();
                   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.startTradeSellFragment, showFinalTrade).commitNow();
                }
            }
        });
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.playerNamesSpinner) {
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            
            RecyclerView recyclerViewRHS = current.findViewById(R.id.takePropRecyclerView);
            otherPlayerProperties = playerOrder[position].getProperties();
            otherPlayerAdapter = new CellPropertyListAdapter(otherPlayerProperties, false, null);
            recyclerViewRHS.setAdapter(otherPlayerAdapter);
            recyclerViewRHS.setLayoutManager(layoutManager2);

            DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerViewRHS.getContext(),  layoutManager2.getOrientation());
            dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.empty_divider));
            recyclerViewRHS.addItemDecoration(dividerItemDecoration2);

            TextView traderBalance = (TextView) current.findViewById(R.id.traderBalance);
            traderBalance.setText("Balance: $" + playerOrder[position].getMoney().toString());
            EditText moneyWant = (EditText) current.findViewById(R.id.inputMoneyP1);
            moneyWant.setText("");

            playerPos = position;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
