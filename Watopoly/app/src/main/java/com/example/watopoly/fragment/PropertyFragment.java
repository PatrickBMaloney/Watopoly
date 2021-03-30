package com.example.watopoly.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.watopoly.R;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.Property;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PropertyFragment extends Fragment {
    View current;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property,container,false);
        current = view;
        return view;
    }

    public void setProperty(Property property) {

    }

    public void setProperty(Building property) {
        // TODO: Change fragment for railways and utilities
        if (property instanceof Building) {
            TextView propertyNameTextView = current.findViewById(R.id.propertyNameTextView);
            TextView baseRentTextView = current.findViewById(R.id.baseRentTextView);
            TextView oneHouseValueTextView = current.findViewById(R.id.oneHouseValueTextView);
            TextView twoHouseValueTextView = current.findViewById(R.id.twoHouseValueTextView);
            TextView threeHouseValueTextView = current.findViewById(R.id.threeHouseValueTextView);
            TextView fourHouseValueTextView = current.findViewById(R.id.fourHouseValueTextView);
            TextView oneHotelTextView = current.findViewById(R.id.oneHotelTextView);

            propertyNameTextView.setText(property.getName());
            propertyNameTextView.setBackgroundColor(256);
            propertyNameTextView.setBackgroundColor(Color.parseColor(property.getPropertyHex()));
            baseRentTextView.setText(String.format("$%.0f", property.getRentPrice(0, true)));
            oneHouseValueTextView.setText(String.format("$   %.0f", property.getRentPrice(1, true)));
            twoHouseValueTextView.setText(String.format("%.0f", property.getRentPrice(2, true)));
            threeHouseValueTextView.setText(String.format("%.0f", property.getRentPrice(3, true)));
            fourHouseValueTextView.setText(String.format("%.0f", property.getRentPrice(4, true)));
            oneHotelTextView.setText(String.format("$%.0f", property.getRentPriceWithHotel()));
        }
    }
}