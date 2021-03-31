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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watopoly.R;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.Property;
import com.example.watopoly.model.Railway;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PropertyFragment extends Fragment {
    private View current;
    private View buildingCardGroup;
    private View transportationCardGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property,container,false);
        current = view;
        return view;
    }

    public void setProperty(Property property) {
        // TODO: Change fragment for railways and utilities
        buildingCardGroup = current.findViewById(R.id.buildingCardGroup);
        transportationCardGroup = current.findViewById(R.id.transportCardGroup);
        if (property instanceof Building) {
            Building building = (Building) property;
            TextView propertyNameTextView = current.findViewById(R.id.propertyNameTextView);
            TextView baseRentTextView = current.findViewById(R.id.baseRentTextView);
            TextView oneHouseValueTextView = current.findViewById(R.id.oneHouseValueTextView);
            TextView twoHouseValueTextView = current.findViewById(R.id.twoHouseValueTextView);
            TextView threeHouseValueTextView = current.findViewById(R.id.threeHouseValueTextView);
            TextView fourHouseValueTextView = current.findViewById(R.id.fourHouseValueTextView);
            TextView oneHotelTextView = current.findViewById(R.id.oneHotelTextView);
            TextView houseCostTextView = current.findViewById(R.id.houseCostTextView);
            TextView hotelCostTextView = current.findViewById(R.id.houseCostTextView2);

            buildingCardGroup.setVisibility(View.VISIBLE);
            transportationCardGroup.setVisibility(View.GONE);
            propertyNameTextView.setText(building.getName());
            propertyNameTextView.setBackgroundColor(256);
            propertyNameTextView.setBackgroundColor(Color.parseColor(building.getPropertyHex()));
            baseRentTextView.setText(String.format("$%.0f", building.getRentPrice(0, false)));
            oneHouseValueTextView.setText(String.format("$   %.0f", building.getRentPrice(1, true)));
            twoHouseValueTextView.setText(String.format("%.0f", building.getRentPrice(2, true)));
            threeHouseValueTextView.setText(String.format("%.0f", building.getRentPrice(3, true)));
            fourHouseValueTextView.setText(String.format("%.0f", building.getRentPrice(4, true)));
            oneHotelTextView.setText(String.format("$%.0f", building.getRentPriceWithHotel()));
            houseCostTextView.setText(String.format("Houses cost $%.0f each", building.getHousePrice()));
            hotelCostTextView.setText(String.format("Hotels, $%.0f plus 4 houses", building.getHousePrice()));
        } else if (property instanceof Railway) {
            Railway railway = (Railway) property;
            TextView transportNameTextView = current.findViewById(R.id.transportNameTextView);
            ImageView transportImageView = current.findViewById(R.id.transportImageView);
            TextView transportRentValueTextView = current.findViewById(R.id.transportRentValueTextView);
            TextView twoTransportRentValueTextView = current.findViewById(R.id.twoTransportRentValueTextView);
            TextView threeTransportRentValueTextView = current.findViewById(R.id.threeTransportRentValueTextView);
            TextView fourTransportRentValueTextView = current.findViewById(R.id.fourTransportRentValueTextView);
            TextView transportMortgageValueTestView = current.findViewById(R.id.transportMortgageValueTestView);

            buildingCardGroup.setVisibility(View.GONE);
            transportationCardGroup.setVisibility(View.VISIBLE);
            transportNameTextView.setText(railway.getName());
            transportImageView.setImageResource(railway.getIcon());
            transportRentValueTextView.setText(String.format("$%.0f", railway.getRentPrice(1)));
            twoTransportRentValueTextView.setText(String.format("%.0f", railway.getRentPrice(2)));
            threeTransportRentValueTextView.setText(String.format("%.0f", railway.getRentPrice(3)));
            fourTransportRentValueTextView.setText(String.format("%.0f", railway.getRentPrice(4)));
            transportMortgageValueTestView.setText(String.format("$%.0f", (railway.getPurchasePrice()/2)));
        } else {
            buildingCardGroup.setVisibility(View.GONE);
            transportationCardGroup.setVisibility(View.GONE);
        }
    }
}