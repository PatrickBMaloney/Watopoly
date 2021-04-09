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
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class TradeSellRequestSentFragment extends Fragment {
    private Game gameState = Game.getInstance();
    private Player otherPlayer;
    private double moneyGive;
    private double moneyTake;

    private ArrayList<Property> propGive;
    private ArrayList<Property> propTake;

    public TradeSellRequestSentFragment(Player otherPlayer, double moneyGive, double moneyTake,
                                        ArrayList<Property> propGive, ArrayList<Property> propTake) {
        this.otherPlayer = otherPlayer;
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
                        //handle prop
                        for (int x = 0; x < propGive.size(); x++) {
                            Property property = propGive.get(x);
                            gameState.getCurrentPlayer().removeProperty(property);
                            otherPlayer.addProperty(property);
                            property.setOwner(otherPlayer);
                        }

                        for (int x = 0; x < propTake.size(); x++) {
                            Property property = propTake.get(x);
                            gameState.getCurrentPlayer().addProperty(property);
                            otherPlayer.removeProperty(property);
                            property.setOwner(gameState.getCurrentPlayer());
                        }

                        //Handle money
                        gameState.getCurrentPlayer().payAmount(moneyGive);
                        otherPlayer.receiveAmount(moneyGive);

                        otherPlayer.payAmount(moneyTake);
                        gameState.getCurrentPlayer().receiveAmount(moneyTake);

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
        traderImage.setImageResource(otherPlayer.getIcon());
        ImageViewCompat.setImageTintMode(traderImage, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(traderImage, ColorStateList.valueOf(Color.parseColor(otherPlayer.getColour())));

        TextView traderName = (TextView) root.findViewById(R.id.traderName);
        traderName.setText(otherPlayer.getName());

        TextView traderBalance = (TextView) root.findViewById(R.id.traderBalance);
        traderBalance.setText("Balance: $" + otherPlayer.getMoney().toString());

        final TextView moneyOffer = (TextView) root.findViewById(R.id.dollarSign0);
        final TextView moneyWant = (TextView) root.findViewById(R.id.dollarSign1);

        moneyOffer.setText("$" + moneyGive);
        moneyWant.setText("$" + moneyTake);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = root.findViewById(R.id.offerPropRecyclerView);
        CellPropertyListAdapter adapter = new CellPropertyListAdapter(propGive, false, null);
        adapter.setShowSelected(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView recyclerViewRHS = root.findViewById(R.id.takePropRecyclerView);
        CellPropertyListAdapter adapterRHS = new CellPropertyListAdapter(propTake, false, null);
        adapterRHS.setShowSelected(false);
        recyclerViewRHS.setAdapter(adapterRHS);
        recyclerViewRHS.setLayoutManager(layoutManager2);

        //Divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),  layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.empty_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerViewRHS.getContext(),  layoutManager2.getOrientation());
        dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.empty_divider));
        recyclerView.addItemDecoration(dividerItemDecoration2);

        return root;
    }
}
