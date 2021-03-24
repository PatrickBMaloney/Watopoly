package com.example.watopoly.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

import com.example.watopoly.R;
import com.example.watopoly.model.Player;

public class PlayerInfoHeaderFragment extends Fragment {
    View current;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_info_header,container,false);
        current = view;
        return view;
    }

    public void setPlayer(Player player) {
        TextView nameTextView = current.findViewById(R.id.playerNameTextView);
        TextView moneyTextView = current.findViewById(R.id.playerMoneyTextView);
        ImageView avatarImageView = current.findViewById(R.id.playerIconImageView);

        nameTextView.setText(player.getName());
        moneyTextView.setText("$"+player.getMoney().toString());

        avatarImageView.setImageResource(player.getIcon());
        ImageViewCompat.setImageTintMode(avatarImageView, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(avatarImageView, ColorStateList.valueOf(Color.parseColor(player.getColour())));
    }
}
