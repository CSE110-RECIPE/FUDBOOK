package com.example.fudbook.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.example.fudbook.R;
import com.example.fudbook.objects.Recipe;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Fragment for viewing recipe
 */
public class fragment_recipe extends Fragment {

    // for api call
    private Bundle data;

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


    // variables
    private String title;
    private String author;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;
    private ArrayList<String> tags;
    private String image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        data = getArguments();

        /** load variables */
        title = data.getString("title");
        author = data.getString("author");
        ingredients = data.getStringArrayList("ingredients");
        instructions = data.getStringArrayList("instructions");
        tags = data.getStringArrayList("tags");
        image = data.getString("image");

        setView(view);

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
        recipeTitleView.setText(title);

//        // load image
//        if(image != null) {
//            //Convert string back to Uri
//            try {
//                Glide.with(getContext())
//                        .asBitmap()
//                        .load(image)
//                        .centerCrop()
//                        .into(recipeImageView);
//            }catch(Exception e){
//                System.out.println(e);
//            }
//        }
        Picasso.get().load("https:" + image)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .centerCrop()
                .into(recipeImageView);

        // load chip groups
        if (tags != null) {
            for (String ing : tags) {
                Chip c = new Chip(getContext());
                c.setTextSize(16);
                c.setPadding(3, 3, 3, 3);
                c.setText(ing);
                recipeChipGroup.addView(c);
            }
        }

        // load instructions
        recipeInstructionsView.setText(instructions != null ? instructions.get(0)
                : "No instructions...");
    }
    
}
