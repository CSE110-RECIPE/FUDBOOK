package com.example.fudbook.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fudbook.R;
import com.example.fudbook.objects.Recipe;


/**
 * Fragment for viewing recipe
 */
public class fragment_recipe extends Fragment {

    // Store the recipe into this object
    Recipe mRecipe;

    // for api call
    String recipeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment






        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }
    
}
