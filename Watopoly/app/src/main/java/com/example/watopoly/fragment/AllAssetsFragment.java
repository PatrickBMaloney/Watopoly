package com.example.watopoly.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.watopoly.R;
import com.example.watopoly.model.Game;
import com.example.watopoly.model.Property;


public class AllAssetsFragment extends Fragment {

    private Game gameState = Game.getInstance();
    int [] playerID = new int[4];
    int [] imageID = new int[4];
    int [] moneyID = new int[4];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_assets, container, false);
        setButtons(root);
        int numPlayers = gameState.getPlayers().size();
        setPlayerIndex();
        setMoneyID();
        setImageID();

        //default hide all
        root.findViewById(playerID[0]).setVisibility(View.GONE);
        root.findViewById(playerID[1]).setVisibility(View.GONE);
        root.findViewById(playerID[2]).setVisibility(View.GONE);
        root.findViewById(playerID[3]).setVisibility(View.GONE);
        for(int i = 0; i < numPlayers; i++) {
            showPlayerAssets(root, i);
            root.findViewById(playerID[i]).setVisibility(View.VISIBLE);
        }
        return root;
    }
    private void setPlayerIndex() {
        playerID[0] = R.id.player0;
        playerID[1] = R.id.player1;
        playerID[2] = R.id.player2;
        playerID[3] = R.id.player3;
    }

    private void setMoneyID() {
        moneyID[0] = R.id.playerMoneyTextView0;
        moneyID[1] = R.id.playerMoneyTextView1;
        moneyID[2] = R.id.playerMoneyTextView2;
        moneyID[3] = R.id.playerMoneyTextView3;
    }

    private void setImageID() {
        imageID[0] = R.id.playerIconImageView0;
        imageID[1] = R.id.playerIconImageView1;
        imageID[2] = R.id.playerIconImageView2;
        imageID[3] = R.id.playerIconImageView3;
    }
    private void setButtons(final View root) {
        Button return_to_board = root.findViewById(R.id.back_to_board);
        return_to_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }
    private void showPlayerAssets(View root, int Player) {

        final FragmentManager fm = getChildFragmentManager();
        int size = gameState.getPlayers().get(Player).getProperties().size(); //number of properties

        TextView moneyTextView = root.findViewById(moneyID[Player]);
        ImageView avatarImageView = root.findViewById(imageID[Player]);

        moneyTextView.setText("$"+gameState.getPlayers().get(Player).getMoney().toString());

        avatarImageView.setImageResource(gameState.getPlayers().get(Player).getIcon());
        ImageViewCompat.setImageTintMode(avatarImageView, PorterDuff.Mode.SRC_ATOP);
        ImageViewCompat.setImageTintList(avatarImageView, ColorStateList.valueOf(Color.parseColor(gameState.getPlayers().get(Player).getColour())));
        //will start setting all properties
        for(int i = 0; i < size; i++) {
            String frag = "propCard" + String.valueOf(i) + "." + String.valueOf(Player);
            int resourceViewID = getResources().getIdentifier(frag, "id", requireActivity().getPackageName());
            PropertyFragment propertyFragment = (PropertyFragment) fm.findFragmentById(resourceViewID);
            final Property property = gameState.getPlayers().get(Player).getProperties().get(i); //get the current property
            propertyFragment.setProperty(property);
        }

        for(int j = size; j < 26; j++) {
            String frag = "propCard" + String.valueOf(j) + "." + String.valueOf(Player);
            int resourceViewID = getResources().getIdentifier(frag, "id", requireActivity().getPackageName());
            View hideFrag = root.findViewById(resourceViewID);
            if(hideFrag != null) {
                hideFrag.setVisibility(View.GONE);
            }
        }
    }
}