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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.activity.MainGameViewActivity;
import com.example.watopoly.fragment.DialogViewProperty;
import com.example.watopoly.fragment.PropertyFragment;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Player;
import com.example.watopoly.model.Property;

import java.util.ArrayList;

public class AllAssetsPlayerAdapter extends RecyclerView.Adapter<AllAssetsPlayerAdapter.viewHolder> implements CellPropertyListAdapter.OnPropClickListener {

    private Context context;
    private ArrayList<Player> players;

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

        CellPropertyListAdapter adapter = new CellPropertyListAdapter(players.get(position).getProperties(), false, this);
        adapter.setShowSelected(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.propsRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(holder.propsRecyclerView.getContext(),  layoutManager.getOrientation());
        dividerItemDecoration2.setDrawable(ContextCompat.getDrawable(context, R.drawable.empty_divider));
        holder.propsRecyclerView.addItemDecoration(dividerItemDecoration2);
        holder.propsRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    @Override
    public void onPropClick(Property property) {
        DialogViewProperty dialog = new DialogViewProperty(property);
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"tag" );
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
