package com.example.watopoly.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;

import java.util.ArrayList;

public class PlayerInfoListAdapter extends RecyclerView.Adapter<PlayerInfoListAdapter.PlayerInfoHolder> {
    private ArrayList<Integer> paths;
    private ArrayList<String> colours;

    private Integer selected = -1;

    public class PlayerInfoHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RadioButton radioButton;
        PlayerInfoHolder (final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iconImageView);
            radioButton = itemView.findViewById(R.id.playerOptionRadioButton);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selected);
                    selected = getAdapterPosition();
                    notifyItemChanged(selected);
                }
            });
        }
    }

    public PlayerInfoListAdapter(ArrayList<Integer> paths, ArrayList<String> colours) {
        this.paths = paths;
        this.colours = colours;
    }

    @NonNull
    @Override
    public PlayerInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new PlayerInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerInfoHolder holder, int position) {
        holder.imageView.setImageResource(paths.get(position));
        ImageViewCompat.setImageTintMode(holder.imageView, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(holder.imageView, ColorStateList.valueOf(Color.parseColor(colours.get(position))));

        holder.radioButton.setChecked(selected == position);
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.row_player_info_option;
    }

    //Getters and Setters

    public int getSelected() { return selected; }

    public void setSelected(int selected) { this.selected = selected; }

    public ArrayList<Integer> getPaths() { return paths; }

    public ArrayList<String> getColours() { return colours; }
}
