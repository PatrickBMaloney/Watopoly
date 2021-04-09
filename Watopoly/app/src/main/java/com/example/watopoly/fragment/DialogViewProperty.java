package com.example.watopoly.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watopoly.R;
import com.example.watopoly.adapter.PropertyListAdapter;
import com.example.watopoly.adapter.ViewPropertyAdapter;
import com.example.watopoly.model.Property;

import java.util.ArrayList;

public class DialogViewProperty extends DialogFragment {

    private ArrayList<Property> props = new ArrayList<>();
    public DialogViewProperty(Property prop) {
        props.clear();
        props.add(prop);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_preview_property, null);

        RecyclerView rv = (RecyclerView) root.findViewById(R.id.trialDialog);
        ViewPropertyAdapter adapter = new ViewPropertyAdapter(getContext(), props);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 1));

        builder.setView(root);
        return builder.create();
    }
}
