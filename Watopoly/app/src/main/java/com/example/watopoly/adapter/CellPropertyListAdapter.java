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
import com.example.watopoly.model.Railway;
import com.example.watopoly.model.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CellPropertyListAdapter extends RecyclerView.Adapter<CellPropertyListAdapter.PropertyInfoHolder> {
    private ArrayList<Property> properties;
    private List<Boolean> selected;
    private Boolean singleSelect = false;
    private Boolean showSelected = true;
    private CellPropertyListAdapter.OnPropClickListener listener;

    public class PropertyInfoHolder extends RecyclerView.ViewHolder {
        ImageView propertyImageView;
        TextView buildingNameTextView;
        TextView propertyNameTextView;
        TextView unSelectTextView;
        TextView selectedTextView;
        TextView amountTextView;
        View container;

        PropertyInfoHolder (final View itemView, final CellPropertyListAdapter.OnPropClickListener onPropClickListener) {
            super(itemView);
            container = itemView;
            propertyImageView = itemView.findViewById(R.id.cellPropertyImageView);
            buildingNameTextView = itemView.findViewById(R.id.cellBuildNameTextView);
            propertyNameTextView = itemView.findViewById(R.id.cellPropertyNameTextView);
            unSelectTextView = itemView.findViewById(R.id.cardBorder);
            selectedTextView = itemView.findViewById(R.id.selectedBorder);
            amountTextView = itemView.findViewById(R.id.cellPropertyAmountTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (singleSelect) {
                        for (int x = 0; x < properties.size(); x++) {
                            if (x != getAdapterPosition()) selected.set(x, false);
                        }
                    }

                    selected.set(pos, selected.get(pos) ? false : true);
                    notifyDataSetChanged();
                    if (onPropClickListener != null) {
                        onPropClickListener.onPropClick(properties.get(pos));
                    }
                }
            });
        }
    }

    public CellPropertyListAdapter(ArrayList<Property> properties, Boolean singleSelect, CellPropertyListAdapter.OnPropClickListener listener) {
        this.properties = properties;
        this.selected = new ArrayList<>(Collections.nCopies(properties.size(), false));
        this.singleSelect = singleSelect;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CellPropertyListAdapter.PropertyInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new CellPropertyListAdapter.PropertyInfoHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CellPropertyListAdapter.PropertyInfoHolder holder, int position) {
        Property property = properties.get(position);
        if (property.getMortgaged()) {
            holder.container.setBackgroundColor(Color.GRAY);
        } else {
            holder.container.setBackgroundColor(Color.WHITE);
        }

        if (property instanceof Building) {
            Building building = (Building)property;
            holder.buildingNameTextView.setText(property.getName());
            holder.buildingNameTextView.setBackgroundColor(256);
            holder.buildingNameTextView.setBackgroundColor(Color.parseColor(building.getPropertyHex()));

            holder.buildingNameTextView.setVisibility(View.VISIBLE);
            holder.propertyImageView.setVisibility(View.GONE);
            holder.propertyNameTextView.setVisibility(View.GONE);
            holder.amountTextView.setText("$" + building.getRentPrice());
        }else {
            holder.buildingNameTextView.setVisibility(View.GONE);
            holder.propertyImageView.setVisibility(View.VISIBLE);
            holder.propertyNameTextView.setVisibility(View.VISIBLE);

            holder.propertyNameTextView.setText(property.getName());
            if (property instanceof Utility) {
                holder.propertyImageView.setImageResource(R.drawable.coffee_and_donut);
                holder.amountTextView.setText(String.format("$%.0f", (property.getPurchasePrice() / 2)));
            }else {
                Railway railway = (Railway) property;
                holder.propertyImageView.setImageResource(railway.getIcon());
                holder.amountTextView.setText("$" + railway.getRentPrice());
            }
        }

        boolean isSelected = selected.get(position);
        if (showSelected) {
            holder.selectedTextView.setVisibility(isSelected ? View.VISIBLE : View.GONE);
            holder.unSelectTextView.setVisibility(isSelected ? View.GONE : View.VISIBLE);
        } else {
            holder.selectedTextView.setVisibility(View.GONE);
            holder.unSelectTextView.setVisibility(View.VISIBLE);
        }

    }

    public void setShowSelected(Boolean showSelected) {
        this.showSelected = showSelected;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.cell_property;
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    public List<Boolean> getSelected() {
        return selected;
    }

    public interface OnPropClickListener{
        void onPropClick(Property property);
    }
}

