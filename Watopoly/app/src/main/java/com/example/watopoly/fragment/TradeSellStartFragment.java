package com.example.watopoly.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;

public class TradeSellStartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trade_resources_start, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.offerPropRecyclerView);
        return root;
    }
}
