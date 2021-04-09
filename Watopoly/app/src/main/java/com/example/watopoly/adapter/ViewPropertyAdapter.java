package com.example.watopoly.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.model.Building;
import com.example.watopoly.model.Property;
import com.example.watopoly.model.Railway;
import com.example.watopoly.model.Utility;

import java.util.ArrayList;

public class ViewPropertyAdapter extends RecyclerView.Adapter<ViewPropertyAdapter.viewHolder> {

    private ArrayList<Property> props;
    private Context context;
    public ViewPropertyAdapter(Context c, ArrayList<Property> props) {
        context = c;
        this.props = props;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_property, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Property property = props.get(position);
        if (property instanceof Building) {
            Building building = (Building) property;

            holder.buildingCardGroup.setVisibility(View.VISIBLE);
            holder.transportationCardGroup.setVisibility(View.GONE);
            holder.utilityCardGroup.setVisibility(View.GONE);

            holder.propertyNameTextView.setText(building.getName());
            holder.propertyNameTextView.setBackgroundColor(256);
            holder.propertyNameTextView.setBackgroundColor(Color.parseColor(building.getPropertyHex()));
            holder.baseRentTextView.setText(String.format("$%.0f", building.getRentPrice(0, false)));
            holder.oneHouseValueTextView.setText(String.format("$   %.0f", building.getRentPrice(1, true)));
            holder.twoHouseValueTextView.setText(String.format("%.0f", building.getRentPrice(2, true)));
            holder.threeHouseValueTextView.setText(String.format("%.0f", building.getRentPrice(3, true)));
            holder.fourHouseValueTextView.setText(String.format("%.0f", building.getRentPrice(4, true)));
            holder.oneHotelTextView.setText(String.format("$%.0f", building.getRentPriceWithHotel()));
            holder.houseCostTextView.setText(String.format("Houses cost $%.0f each", building.getHousePrice()));
            holder.hotelCostTextView.setText(String.format("Hotels, $%.0f plus 4 houses", building.getHousePrice()));
        } else if (property instanceof Railway) {
            Railway railway = (Railway) property;

            holder.buildingCardGroup.setVisibility(View.GONE);
            holder.transportationCardGroup.setVisibility(View.VISIBLE);
            holder.utilityCardGroup.setVisibility(View.GONE);

            holder.transportNameTextView.setText(railway.getName());
            holder.transportImageView.setImageResource(railway.getIcon());
            holder.transportRentValueTextView.setText(String.format("$%.0f", railway.getRentPrice(1)));
            holder.twoTransportRentValueTextView.setText(String.format("%.0f", railway.getRentPrice(2)));
            holder.threeTransportRentValueTextView.setText(String.format("%.0f", railway.getRentPrice(3)));
            holder.fourTransportRentValueTextView.setText(String.format("%.0f", railway.getRentPrice(4)));
            holder.transportMortgageValueTestView.setText(String.format("$%.0f", (railway.getPurchasePrice() / 2)));
        } else {
            Utility utility = (Utility) property;

            holder.buildingCardGroup.setVisibility(View.GONE);
            holder.transportationCardGroup.setVisibility(View.GONE);
            holder.utilityCardGroup.setVisibility(View.VISIBLE);

            holder.utilityNameTextView.setText(utility.getName());
            holder.utilityMortgageValueTestView.setText(String.format("$%.0f", (utility.getPurchasePrice() / 2)));
        }
    }

    @Override
    public int getItemCount() {
        return props.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        View buildingCardGroup;
        View transportationCardGroup;
        View utilityCardGroup;
        TextView propertyNameTextView;
        TextView baseRentTextView;
        TextView oneHouseValueTextView;
        TextView twoHouseValueTextView;
        TextView threeHouseValueTextView;
        TextView fourHouseValueTextView;
        TextView oneHotelTextView;
        TextView houseCostTextView;
        TextView hotelCostTextView;
        TextView transportNameTextView;
        ImageView transportImageView;
        TextView transportRentValueTextView;
        TextView twoTransportRentValueTextView;
        TextView threeTransportRentValueTextView;
        TextView fourTransportRentValueTextView;
        TextView transportMortgageValueTestView;
        TextView utilityNameTextView;
        TextView utilityMortgageValueTestView;
        PropertyListAdapter.OnPropClickListener onPropClickListener;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            buildingCardGroup = itemView.findViewById(R.id.buildingCardGroup);
            transportationCardGroup = itemView.findViewById(R.id.transportCardGroup);
            utilityCardGroup = itemView.findViewById(R.id.utilityCardGroup);

            propertyNameTextView = itemView.findViewById(R.id.propertyNameTextView);
            baseRentTextView = itemView.findViewById(R.id.baseRentTextView);
            oneHouseValueTextView = itemView.findViewById(R.id.oneHouseValueTextView);
            twoHouseValueTextView = itemView.findViewById(R.id.twoHouseValueTextView);
            threeHouseValueTextView = itemView.findViewById(R.id.threeHouseValueTextView);
            fourHouseValueTextView = itemView.findViewById(R.id.fourHouseValueTextView);
            oneHotelTextView = itemView.findViewById(R.id.oneHotelTextView);
            houseCostTextView = itemView.findViewById(R.id.houseCostTextView);
            hotelCostTextView = itemView.findViewById(R.id.houseCostTextView2);

            transportNameTextView = itemView.findViewById(R.id.transportNameTextView);
            transportImageView = itemView.findViewById(R.id.transportImageView);
            transportRentValueTextView = itemView.findViewById(R.id.transportRentValueTextView);
            twoTransportRentValueTextView = itemView.findViewById(R.id.twoTransportRentValueTextView);
            threeTransportRentValueTextView = itemView.findViewById(R.id.threeTransportRentValueTextView);
            fourTransportRentValueTextView = itemView.findViewById(R.id.fourTransportRentValueTextView);
            transportMortgageValueTestView = itemView.findViewById(R.id.transportMortgageValueTestView);

            utilityNameTextView = itemView.findViewById(R.id.utilityNameTextView);
            utilityMortgageValueTestView = itemView.findViewById(R.id.utilityMortgageValueTestView);
        }
    }
}
