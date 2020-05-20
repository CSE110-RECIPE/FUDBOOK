package com.example.fudbook.ui.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.fudbook.CreateActivity;
import com.example.fudbook.ExploreActivity;
import com.example.fudbook.R;
import com.example.fudbook.ui.bookshelf.book_adapter;
import com.example.fudbook.ui.bookshelf.fragment_bookshelf;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class fragment_create_1 extends Fragment implements AdapterView.OnItemClickListener {

    EditText recipeNameView, instructionView;
    AutoCompleteTextView ingredientNameView;
    ChipGroup chipGroup;

    ArrayList<String> ingredientList;

    // useful data
    ArrayList<String> selectedIngredients;
    String recipeName, instruction;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_1, container, false);

        ingredientList = new ArrayList<>();
        selectedIngredients = new ArrayList<>();

        try {
            InputStream is = getActivity().getApplicationContext().getAssets().open("ingredients.txt");
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter(" ");

            while (scanner.hasNext()) {
                String in = scanner.next();
                ingredientList.add(in);
            }
        } catch(Exception e ) {

        }

        chipGroup = (ChipGroup) view.findViewById(R.id.chip_group);
        recipeNameView = (EditText) view.findViewById(R.id.recipe_title_input);
        ingredientNameView = (AutoCompleteTextView) view.findViewById(R.id.ingredient_input);
        instructionView = (EditText) view.findViewById(R.id.instructions_input);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),R.layout.ingredient_layout,ingredientList);

        ingredientNameView.setAdapter(adapter);

        ingredientNameView.setOnItemClickListener(this);

        recipeNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipeName = s.toString();
                System.out.println(recipeName);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        instructionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                instruction = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    public void onItemClick(AdapterView<?> av, View v, int position, long arg3) {
        Object item = av.getItemAtPosition(position);

        if (item instanceof String) {
            String ingredient = (String) item;
            selectedIngredients.add(ingredient);
            final Chip c = new Chip(getContext());
            c.setTextSize(16);
            c.setPadding(3,3,3,3);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingredientList.remove(c.getText());
                    chipGroup.removeView(c);
                }
            });
            c.setText(ingredient);
            chipGroup.addView(c);
            ingredientNameView.setText("");
        }
    }
}
