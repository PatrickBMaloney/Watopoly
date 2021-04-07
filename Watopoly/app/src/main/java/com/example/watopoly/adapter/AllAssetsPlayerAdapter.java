package com.example.watopoly.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.activity.MainGameViewActivity;
import com.example.watopoly.fragment.PropertyFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import java.util.ArrayList;

public class AllAssetsPlayerAdapter extends RecyclerView.Adapter<AllAssetsPlayerAdapter.viewHolder> implements PropertyListAdapter.onPropClickListener {

    private Context context;
    private ArrayList<Player> players;
    private Game gameState = Game.getInstance();

    public AllAssetsPlayerAdapter(Context c, ArrayList<Player> players) {
        context = c;
        this.players = players;
    }

    @NonNull
    @Override
    public AllAssetsPlayerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_player_assets, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAssetsPlayerAdapter.viewHolder holder, int position) {
        //set up icon
        holder.player_icon.setImageResource(players.get(position).getIcon());
        ImageViewCompat.setImageTintMode(holder.player_icon, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(holder.player_icon, ColorStateList.valueOf(Color.parseColor(players.get(position).getColour())));
        //set up money
        holder.player_money.setText("$"+players.get(position).getMoney().toString());

        //call adapter for this recycler view with this current players properties
            //set grid to be 13 columns over here as well
        PropertyListAdapter adapter = new PropertyListAdapter(context,players.get(position).getProperties(), this);
        holder.propsRecyclerView.setAdapter(adapter);
        holder.propsRecyclerView.setLayoutManager(new GridLayoutManager(context, 13));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    @Override
    public void onPropClick(int propNum) {
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView player_icon;
        TextView player_money;
        RecyclerView propsRecyclerView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            player_icon = itemView.findViewById(R.id.playerIconImageView);
            player_money = itemView.findViewById(R.id.playerMoneyTextView);
            propsRecyclerView = itemView.findViewById(R.id.playerRecycleViewProps);
        }
    }
}
