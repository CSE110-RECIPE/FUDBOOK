package com.example.fudbook.ui.create;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.fudbook.R;
import com.example.fudbook.objects.Recipe;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class fragment_create_1 extends Fragment{

    private EditText recipeNameView, instructionView;
    private AutoCompleteTextView ingredientNameView;
    private ChipGroup chipGroup;


    private ArrayList<String> ingredientList;

    // useful data
    private ArrayList<String> selectedIngredients;
    private String recipeName, instruction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // initializations
        View view = inflater.inflate(R.layout.fragment_create_1, container, false);
        final Bundle args = getArguments();
        ingredientList = new ArrayList<>();
        selectedIngredients = new ArrayList<>();

        /** load ingredients list */
        try {
            InputStream is = getActivity().getApplicationContext().getAssets().open("ingredients.txt");
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter(" ");

            while (scanner.hasNext()) {
                String in = scanner.next();
                ingredientList.add(in);
            }
        } catch(Exception e ) { }

        chipGroup = (ChipGroup) view.findViewById(R.id.chip_group);
        recipeNameView = (EditText) view.findViewById(R.id.recipe_title_input);
        ingredientNameView = (AutoCompleteTextView) view.findViewById(R.id.ingredient_input);
        instructionView = (EditText) view.findViewById(R.id.instructions_input);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.ingredient_layout,ingredientList);

        ingredientNameView.setAdapter(adapter);
        ingredientNameView.setOnItemClickListener(auto_listener);

        recipeNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipeName = s.toString();
                System.out.println(recipeName);
                args.putString("recipe name", recipeName);
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
                String[] arr_instr = {instruction};
                args.putStringArray("instructions", arr_instr);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    final AdapterView.OnItemClickListener auto_listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object item = parent.getItemAtPosition(position);
            Bundle args = getArguments();

            if (item instanceof String) {

                String ingredient = (String) item;
                selectedIngredients.add(ingredient);

                // selected ingredients
                args.putStringArray("ingredients",
                                    selectedIngredients.toArray(new String[selectedIngredients.size()]));

                final Chip c = new Chip(getContext());
                c.setTextSize(16);
                c.setPadding(3, 3, 3, 3);
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
    };

}
