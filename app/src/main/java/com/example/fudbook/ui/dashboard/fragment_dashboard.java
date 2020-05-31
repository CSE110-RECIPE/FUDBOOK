package com.example.fudbook.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fudbook.CreateActivity;
import com.example.fudbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class fragment_dashboard extends Fragment {
    private static final String TAG = "dashboard";

    Button create_button;

    // recipe data field
    private TextView recipeTitle, authorName, description;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // create_button set up
        create_button = view.findViewById(R.id.create_btn);
        create_button.setOnClickListener(create_listener);

        // View field
        // recipe view
        recipeTitle = view.findViewById(R.id.recipe_title_text);
        authorName = view.findViewById(R.id.author_text);
        description = view.findViewById(R.id.description_text);
        imageView = view.findViewById(R.id.recipe_photo);

        // recipe data field
        Bundle recipeData = getArguments();

        if (recipeData != null) {
            recipeTitle.setText(recipeData.getString("title"));
            authorName.setText(recipeData.getString("author"));

            Picasso.get().load(recipeData.getString("image"))
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }

        return view;
    }

    private Button.OnClickListener create_listener =
            new FloatingActionButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // Go to create recipe activity
                    Intent create_intent = new Intent(getContext(), CreateActivity.class);
                    startActivity(create_intent);
                }
            };
}


