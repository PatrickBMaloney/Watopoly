package com.example.watopoly.fragment;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.PropertyListTradeAdapter;
import com.example.watopoly.fragment.Dialog.InsufficientFundsRHS;
import com.example.watopoly.fragment.Dialog.InsufficientFundsYou;
import com.example.watopoly.fragment.Dialog.NoResourcesSelected;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TradeSellRequestSentFragment extends Fragment {
    private Game gameState = Game.getInstance();
    private Player selected;
    private String moneyGive;
    private String moneyTake;
    private ArrayList<Property> propGive;
    private ArrayList<Property> propTake;

    public TradeSellRequestSentFragment(Player selected, String moneyGive, String moneyTake,
                                        ArrayList<Property> propGive, ArrayList<Property> propTake) {
        this.selected = selected;
        this.moneyGive = moneyGive;
        this.moneyTake = moneyTake;
        this.propGive = propGive;
        this.propTake = propTake;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trade_sell_request_sent, container, false);

        Button rejectTrade = (Button) root.findViewById(R.id.playerRejectTrade);
        rejectTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(),R.style.Theme_Dialog);
                dialog.setContentView(R.layout.dialog_trade_rejected);
                TextView tradeRejected = (TextView) dialog.findViewById(R.id.tradeRejectedText);
                tradeRejected.setText("Trade Rejected");
                TextView returnToPlayer = (TextView) dialog.findViewById(R.id.returnToPlayerText);
                returnToPlayer.setText("Please return the phone to " + gameState.getCurrentPlayer().getName());
                Button continuePlayerButton = dialog.findViewById(R.id.continuePlayerButton);
                continuePlayerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });
                dialog.show();
            }
        });

        Button acceptTrade = (Button) root.findViewById(R.id.playerAcceptTrade);
        acceptTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext(),R.style.Theme_Dialog);
                dialog.setContentView(R.layout.dialog_trade_accepted);
                TextView tradeRejected = (TextView) dialog.findViewById(R.id.tradeAcceptedText);
                tradeRejected.setText("Trade Accepted!");
                TextView returnToPlayer = (TextView) dialog.findViewById(R.id.returnToPlayerText);
                returnToPlayer.setText("Please return the phone to " + gameState.getCurrentPlayer().getName());
                Button continuePlayerButton = dialog.findViewById(R.id.continuePlayerButton);
                continuePlayerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //remove properties from requester first
                        for(int i = 0; i < propGive.size(); i++) {
                            gameState.getCurrentPlayer().removeProperty(propGive.get(i));
                        }
                        //remove properties from selected
                        for(int j = 0; j < propTake.size(); j++) {
                            selected.removeProperty(propTake.get(j));
                        }

                        for(int k = 0; k < propTake.size(); k++) {
                            gameState.getCurrentPlayer().addProperty(propTake.get(k));
                            propTake.get(k).setOwner(gameState.getCurrentPlayer());
                        }

                        for(int l = 0; l < propGive.size(); l++) {
                            selected.addProperty(propGive.get(l));
                            propGive.get(l).setOwner(selected);
                        }

                        //remove money  from requester
                        gameState.getCurrentPlayer().payAmount(Double.parseDouble(moneyGive));
                        selected.receiveAmount(Double.parseDouble(moneyGive));

                        //remove money from selected
                        selected.payAmount(Double.parseDouble(moneyTake));
                        gameState.getCurrentPlayer().receiveAmount(Double.parseDouble(moneyTake));

                        dialog.dismiss();
                        getActivity().finish();
                    }
                });
                dialog.show();

            }
        });


        ImageView requesterImage = (ImageView) root.findViewById(R.id.playerIconImageView);
        requesterImage.setImageResource(gameState.getCurrentPlayer().getIcon());
        ImageViewCompat.setImageTintMode(requesterImage, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(requesterImage, ColorStateList.valueOf(Color.parseColor(gameState.getCurrentPlayer().getColour())));

        TextView requesterName = (TextView) root.findViewById(R.id.requesterName);
        requesterName.setText(gameState.getCurrentPlayer().getName());

        TextView requesterBalance = (TextView) root.findViewById(R.id.requesterBalance);
        requesterBalance.setText("Balance: $" + gameState.getCurrentPlayer().getMoney().toString());


        ImageView traderImage = (ImageView) root.findViewById(R.id.playerIconImageViewTrader);
        traderImage.setImageResource(selected.getIcon());
        ImageViewCompat.setImageTintMode(traderImage, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(traderImage, ColorStateList.valueOf(Color.parseColor(selected.getColour())));

        TextView traderName = (TextView) root.findViewById(R.id.traderName);
        traderName.setText(selected.getName());

        TextView traderBalance = (TextView) root.findViewById(R.id.traderBalance);
        traderBalance.setText("Balance: $" + selected.getMoney().toString());

        final TextView moneyOffer = (TextView) root.findViewById(R.id.dollarSign0);
        final TextView moneyWant = (TextView) root.findViewById(R.id.dollarSign1);

        moneyOffer.setText("$" + moneyGive);
        moneyWant.setText("$" + moneyTake);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.offerPropRecyclerView);
        PropertyListTradeAdapter adapter = new PropertyListTradeAdapter(getContext(), gameState.getCurrentPlayer().getProperties(), propGive);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        RecyclerView recyclerViewRHS = (RecyclerView) root.findViewById(R.id.takePropRecyclerView);
        PropertyListTradeAdapter adapterRHS = new PropertyListTradeAdapter(getContext(), selected.getProperties(), propTake);
        recyclerViewRHS.setAdapter(adapterRHS);
        recyclerViewRHS.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return root;
    }
}
