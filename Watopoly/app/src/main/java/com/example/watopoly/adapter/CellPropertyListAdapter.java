package com.example.watopoly.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CellPropertyListAdapter extends RecyclerView.Adapter<CellPropertyListAdapter.PropertyInfoHolder> {
    private ArrayList<Property> properties;
    private List<Boolean> selected;

    public class PropertyInfoHolder extends RecyclerView.ViewHolder {
        ImageView propertyImageView;
        TextView buildingNameTextView;
        TextView propertyNameTextView;
        TextView unSelectTextView;
        TextView selectedTextView;
        PropertyInfoHolder (final View itemView) {
            super(itemView);
            propertyImageView = itemView.findViewById(R.id.cellPropertyImageView);
            buildingNameTextView = itemView.findViewById(R.id.cellBuildNameTextView);
            propertyNameTextView = itemView.findViewById(R.id.cellPropertyNameTextView);
            unSelectTextView = itemView.findViewById(R.id.cardBorder);
            selectedTextView = itemView.findViewById(R.id.selectedBorder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    selected.set(pos, selected.get(pos) ? false : true);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public CellPropertyListAdapter(ArrayList<Property> properties) {
        this.properties = properties;
        this.selected = new ArrayList<>(Collections.nCopies(properties.size(), false));
    }

    @NonNull
    @Override
    public CellPropertyListAdapter.PropertyInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new CellPropertyListAdapter.PropertyInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CellPropertyListAdapter.PropertyInfoHolder holder, int position) {
        Property property = properties.get(position);

        if (property instanceof Building) {
            Building building = (Building)property;
            holder.buildingNameTextView.setText(property.getName());
            holder.buildingNameTextView.setBackgroundColor(256);
            holder.buildingNameTextView.setBackgroundColor(Color.parseColor(building.getPropertyHex()));

            holder.buildingNameTextView.setVisibility(View.VISIBLE);
            holder.propertyImageView.setVisibility(View.GONE);
            holder.propertyNameTextView.setVisibility(View.GONE);
        }else {
            holder.buildingNameTextView.setVisibility(View.GONE);
            holder.propertyImageView.setVisibility(View.VISIBLE);
            holder.propertyNameTextView.setVisibility(View.VISIBLE);
        }

        boolean isSelected = selected.get(position);
        holder.selectedTextView.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        holder.unSelectTextView.setVisibility(isSelected ? View.GONE : View.VISIBLE);

        //TODO: for the others

        //TODO: onSelected
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.cell_property;
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }
}

