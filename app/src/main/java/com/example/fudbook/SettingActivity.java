package com.example.fudbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SettingActivity extends AppCompatActivity {

    private ArrayList<String> ingredientList;
    private ArrayList<String> selectedIncludeFilter, selectedExcludeFilter;

    private AutoCompleteTextView includeAView, excludeAView;

    private ChipGroup includeChip, excludeChip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ingredientList = new ArrayList<>();
        selectedIncludeFilter = getIntent().getStringArrayListExtra("include_filter");
        selectedExcludeFilter = getIntent().getStringArrayListExtra("exclude_filter");

        /** load ingredients list */
        try {
            InputStream is = getApplicationContext().getAssets().open("ingredients.txt");
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter(" ");

            while (scanner.hasNext()) {
                String in = scanner.next();
                ingredientList.add(in);
            }
        } catch (Exception e) {
        }

        includeAView = findViewById(R.id.include_filter_input);
        excludeAView = findViewById(R.id.exclude_filter_input);
        includeChip = findViewById(R.id.include_filter_chip);
        excludeChip = findViewById(R.id.exclude_filter_chip);

        for (String n : selectedIncludeFilter) {
            final Chip c = new Chip(this);
            c.setTextSize(16);
            c.setPadding(3, 3, 3, 3);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedIncludeFilter.remove(c.getText());
                    includeChip.removeView(c);
                }
            });
            c.setText(n);
            includeChip.addView(c);
        }

        for (String n : selectedExcludeFilter) {
            final Chip c = new Chip(this);
            c.setTextSize(16);
            c.setPadding(3, 3, 3, 3);
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedExcludeFilter.remove(c.getText());
                    excludeChip.removeView(c);
                }
            });
            c.setText(n);
            excludeChip.addView(c);
        }

        ArrayAdapter<String> iadapter = new ArrayAdapter<String>(this, R.layout.ingredient_layout, ingredientList);
        ArrayAdapter<String> eadapter = new ArrayAdapter<String>(this, R.layout.ingredient_layout, ingredientList);

        includeAView.setAdapter(iadapter);
        excludeAView.setAdapter(eadapter);
        includeAView.setOnItemClickListener(i_auto_listener);
        excludeAView.setOnItemClickListener(e_auto_listener);
    }

    public void exitSetting(View v) {
        Intent res = new Intent(getBaseContext(), SettingActivity.class);
        res.putStringArrayListExtra("include filter", selectedIncludeFilter);
        res.putStringArrayListExtra("exclude filter", selectedExcludeFilter);
        res.putExtra("reload request", true);
        setResult(1, res);
        finish();
    }

    final AdapterView.OnItemClickListener i_auto_listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object item = parent.getItemAtPosition(position);

            if (item instanceof String) {

                String ingredient = (String) item;
                selectedIncludeFilter.add(ingredient);

                final Chip c = new Chip(view.getContext());
                c.setTextSize(16);
                c.setPadding(3, 3, 3, 3);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedIncludeFilter.remove(c.getText());
                        includeChip.removeView(c);
                    }
                });
                c.setText(ingredient);
                includeChip.addView(c);
                includeAView.setText("");
            }
        }
    };

    final AdapterView.OnItemClickListener e_auto_listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object item = parent.getItemAtPosition(position);

            if (item instanceof String) {

                String ingredient = (String) item;
                selectedExcludeFilter.add(ingredient);

                final Chip c = new Chip(view.getContext());
                c.setTextSize(16);
                c.setPadding(3, 3, 3, 3);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedExcludeFilter.remove(c.getText());
                        excludeChip.removeView(c);
                    }
                });
                c.setText(ingredient);
                excludeChip.addView(c);
                excludeAView.setText("");
            }
        }
    };
}