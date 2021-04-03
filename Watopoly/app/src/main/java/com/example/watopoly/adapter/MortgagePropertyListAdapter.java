package com.example.watopoly.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.Property;

import java.util.ArrayList;

public class MortgagePropertyListAdapter extends RecyclerView.Adapter<MortgagePropertyListAdapter.PropertyInfoHolder> {
    private ArrayList<Property> properties;

    private Integer selected = -1;

    public class PropertyInfoHolder extends RecyclerView.ViewHolder {
        TextView propertyHeader;
        TextView price;
        RadioButton radioButton;
        PropertyInfoHolder (final View itemView) {
            super(itemView);
            propertyHeader = itemView.findViewById(R.id.propertyRowTextView);
            price = itemView.findViewById(R.id.dollarValueTextView);
            radioButton = itemView.findViewById(R.id.mortgagePropertyRadioButton);
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

    public MortgagePropertyListAdapter(ArrayList<Property> properties) {
        this.properties = properties;
    }

    @NonNull
    @Override
    public PropertyInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new PropertyInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyInfoHolder holder, int position) {
        holder.propertyHeader.setText(properties.get(position).getName());
        if (properties.get(position) instanceof Building) {
            Building building = (Building) properties.get(position);
            holder.propertyHeader.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            holder.propertyHeader.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(building.getPropertyHex())));
        }
        String priceText = "$"+properties.get(position).getPurchasePrice();
        holder.price.setText(priceText);
        holder.radioButton.setChecked(selected == position);
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.row_mortgage_properties;
    }

    //Getters and Setters

    public int getSelected() { return selected; }

    public void setSelected(int selected) { this.selected = selected; }

    public ArrayList<Property> getProperties() { return properties; }
}
