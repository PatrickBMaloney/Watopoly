package com.example.watopoly.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PropertyListTradeAdapter extends RecyclerView.Adapter<PropertyListTradeAdapter.viewHolder>{
    @NonNull
    @Override
    public PropertyListTradeAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyListTradeAdapter.viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
