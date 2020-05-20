package com.example.fudbook.ui.create;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fudbook.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class fragment_create_3 extends Fragment {

    /** Layout Views */
    // Recipe Title
    private TextView recipeTitleView;

    // Recipe Photo
    private ImageView recipeImageView;

    /** Ingredient views */
    // Recipe Group
    private ChipGroup recipeChipGroup;

    // Recipe Chips
    private Chip[] recipeChips;

    // Recipe Instructions
    private TextView recipeInstructionsView;

    /** Holding variables */

    // Recipe Title
    private String recipeTitle;

    // Recipe Image
    private String recipeImage;

    // Recipe Ingredients
    private String[] recipeIngredients;

    // Recipe Instructions
    private String[] recipeInstructions;

    // memory callback
    public static final String CREATE_PREFERENCES = "Create_Prefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_3, container, false);

        /** Loading data from previous fragment */
        // load bundle from last fragment
        Bundle args = this.getArguments();

        /** load variables */
        recipeTitle = args.getString("recipe name");
        recipeImage = args.getString("recipe image");
        recipeIngredients = args.getStringArray("ingredients");
        recipeInstructions = args.getStringArray("instructions");

        /** testing */

        setView(view);

        // load into the layout
        layoutLoadIntoView();

        return view;
    }

    private void setView(View view) {
        recipeTitleView = view.findViewById(R.id.user_recipe_title);
        recipeImageView = view.findViewById(R.id.recipe_photo);
        recipeChipGroup = (ChipGroup) view.findViewById(R.id.chip_group);
        recipeInstructionsView = view.findViewById(R.id.user_instructions_input);
    }

    private void layoutLoadIntoView(){
        // load values into view

        // load title
        recipeTitleView.setText(recipeTitle);

        // load image
        if(recipeImage != null) {
            //Convert string back to Uri
            Uri imageUri = Uri.parse(recipeImage);

            Glide.with(getContext())
                    .asBitmap()
                    .load(imageUri)
                    .centerCrop()
                    .into(recipeImageView);
        }
        else
        {
            Glide.with(getContext())
                    .asBitmap()
                    .load(recipeImage)
                    .centerCrop()
                    .into(recipeImageView);
        }

        // load chip groups
        if (recipeIngredients != null) {
            for (String ing : recipeIngredients) {
                Chip c = new Chip(getContext());
                c.setTextSize(16);
                c.setPadding(3, 3, 3, 3);
                c.setText(ing);
                recipeChipGroup.addView(c);
            }
        }

        // load instructions
        recipeInstructionsView.setText(recipeInstructions != null ? recipeInstructions[0]
                : "No instructions...");
    }

    // load bundle from last fragment
    private void handleBundle(Bundle bundle){
        if (bundle != null) {
            recipeTitle = bundle.getString("", "Recipe Title");
            recipeImage = bundle.getString("", null); // if null, display android image or missing image
            recipeIngredients = bundle.getStringArray("");
            recipeInstructions = bundle.getStringArray("");
        }
    }

}
