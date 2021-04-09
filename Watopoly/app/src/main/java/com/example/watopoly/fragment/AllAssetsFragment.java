package com.example.watopoly.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.AllAssetsPlayerAdapter;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Property;


public class AllAssetsFragment extends Fragment {

    private Game gameState = Game.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_assets, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.playerRecycleView);
        setButtons(root);
        AllAssetsPlayerAdapter adapter = new AllAssetsPlayerAdapter(getContext(), gameState.getPlayers());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerView.getContext(),  layoutManager.getOrientation());
        dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.empty_divider));
        recyclerView.addItemDecoration(dividerItemDecoration2);
        return root;
    }
    private void setButtons(final View root) {
        Button return_to_board = root.findViewById(R.id.back_to_board);
        return_to_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
}