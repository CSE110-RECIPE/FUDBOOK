package com.example.fudbook.ui.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fudbook.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class fragment_explore_recipe extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_recipe, container, false);
        TextView recipeNameTV = view.findViewById(R.id.explore_recipe_name);
        ChipGroup chipGroup = view.findViewById(R.id.chip_group);
        ImageView imageView = view.findViewById(R.id.explore_recipe_image);
        Bundle data = getArguments();

        recipeNameTV.setText(data.getString("name"));

        ArrayList<String> ingredientList = data.getStringArrayList("ingredients");

        /**
         * TODO: need to change URI after full setup
         */
        Picasso.get().load("https:" + data.getString("imageURL"))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .resize(300,300)
                .centerCrop()
                .into(imageView);

        for (String n : ingredientList) {
            Chip c = new Chip(getContext());
            c.setText(n);
            chipGroup.addView(c);
        }

        return view;
    }
}
