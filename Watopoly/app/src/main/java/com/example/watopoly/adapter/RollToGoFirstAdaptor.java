package com.example.watopoly.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;

import java.util.ArrayList;

public class RollToGoFirstAdaptor extends RecyclerView.Adapter<RollToGoFirstAdaptor.PlayerDiceRollHolder> {
    private ArrayList<Integer> paths;
    private ArrayList<String> colours;
    private String diceRoll;


    public class PlayerDiceRollHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView diceRollValueTextView;
        PlayerDiceRollHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iconImageView);
            diceRollValueTextView = itemView.findViewById(R.id.diceRollValueTextView);
        }
    }

    public RollToGoFirstAdaptor(ArrayList<Integer> paths, ArrayList<String> colours) {
        this.paths = paths;
        this.colours = colours;
    }

    public RollToGoFirstAdaptor(ArrayList<Integer> paths, ArrayList<String> colours, Integer diceRoll) {
        this.paths = paths;
        this.colours = colours;
        this.diceRoll = diceRoll.toString();
    }

    @NonNull
    @Override
    public PlayerDiceRollHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new PlayerDiceRollHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerDiceRollHolder holder, int position) {
        holder.imageView.setImageResource(paths.get(position));
        ImageViewCompat.setImageTintMode(holder.imageView, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(holder.imageView, ColorStateList.valueOf(Color.parseColor(colours.get(position))));
        holder.diceRollValueTextView.setText(diceRoll);
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.row_dice_roll_info;
    }

    //Getters and Setters

    public ArrayList<Integer> getPaths() { return paths; }

    public ArrayList<String> getColours() { return colours; }
}
