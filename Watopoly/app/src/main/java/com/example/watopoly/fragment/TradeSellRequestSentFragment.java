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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.PropertyListTradeAdapter;
import com.example.watopoly.fragment.Dialog.InsufficientFundsRHS;
import com.example.watopoly.fragment.Dialog.InsufficientFundsYou;
import com.example.watopoly.fragment.Dialog.NoResourcesSelected;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;

import org.w3c.dom.Text;

public class TradeSellRequestSentFragment extends Fragment {
    private Game gameState = Game.getInstance();
    private Player selected;
    private String moneyGive;
    private String moneyTake;

    public TradeSellRequestSentFragment(Player selected, String moneyGive, String moneyTake) {
        this.selected = selected;
        this.moneyGive = moneyGive;
        this.moneyTake = moneyTake;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trade_sell_request_sent, container, false);

        ImageView requesterImage = (ImageView) root.findViewById(R.id.playerIconImageView);
        requesterImage.setImageResource(gameState.getCurrentPlayer().getIcon());
        ImageViewCompat.setImageTintMode(requesterImage, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(requesterImage, ColorStateList.valueOf(Color.parseColor(gameState.getCurrentPlayer().getColour())));

        TextView requesterName = (TextView) root.findViewById(R.id.requesterName);
        requesterName.setText(gameState.getCurrentPlayer().getName());

        TextView requesterMoney = (TextView) root.findViewById(R.id.requesterMoney);
        requesterMoney.setText(gameState.getCurrentPlayer().getMoney().toString());

        ImageView traderImage = (ImageView) root.findViewById(R.id.playerIconImageViewTrader);
        requesterImage.setImageResource(selected.getIcon());
        ImageViewCompat.setImageTintMode(traderImage, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(traderImage, ColorStateList.valueOf(Color.parseColor(selected.getColour())));

        TextView traderName = (TextView) root.findViewById(R.id.traderName);
        traderName.setText(selected.getName());

        TextView traderMoney = (TextView) root.findViewById(R.id.traderMoney);
        traderMoney.setText(selected.getMoney().toString());

        final TextView moneyOffer = (TextView) root.findViewById(R.id.dollarSign0);
        final TextView moneyWant = (TextView) root.findViewById(R.id.dollarSign1);

        moneyOffer.setText("$" + moneyGive);
        moneyWant.setText("$" + moneyTake);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.offerPropRecyclerView);
        PropertyListTradeAdapter adapter = new PropertyListTradeAdapter(getContext(), gameState.getCurrentPlayer().getProperties(), 0);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        RecyclerView recyclerViewRHS = (RecyclerView) root.findViewById(R.id.takePropRecyclerView);
        PropertyListTradeAdapter adapterRHS = new PropertyListTradeAdapter(getContext(), selected.getProperties(), 1);
        recyclerViewRHS.setAdapter(adapterRHS);
        recyclerViewRHS.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return root;
    }
}
