package com.example.fudbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fudbook.ui.bookshelf.fragment_bookshelf;
import com.example.fudbook.ui.dashboard.fragment_dashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
/*
        MAIN ACTIVITY:
            HOLDS ALL FRAGMENTS FOR DASHBOARD:
                - CREATE
                - BOOKSHELF
 */


public class MainActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "MainActivity";

    // buttons
    private ImageButton dashboard_button;
    private ImageButton bookshelf_button;
    private FloatingActionButton explore_button;

    // filter
    private ArrayList<String> selectedIncludeFilter, selectedExcludeFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // starting create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.container, new fragment_dashboard()).commit();

        // button set up
        dashboard_button = findViewById(R.id.dash_btn);
        bookshelf_button = findViewById(R.id.bookshelf_btn);
        explore_button = findViewById(R.id.explore_btn);

        // button listeners set up
        dashboard_button.setOnClickListener(dash_listener);
        bookshelf_button.setOnClickListener(bookshelf_listener);
        explore_button.setOnClickListener(explore_listener);


        // API Calls
        // Maybe will include API calls to fetch user filter, but for now nah
        selectedIncludeFilter = new ArrayList<>();
        selectedExcludeFilter = new ArrayList<>();
    }

    public void enterSetting(View v) {
        Intent setting_intent = new Intent(getBaseContext(), SettingActivity.class);
        setting_intent.putStringArrayListExtra("exclude_filter", selectedExcludeFilter);
        setting_intent.putStringArrayListExtra("include_filter", selectedIncludeFilter);
        startActivityForResult(setting_intent, 1);
    }

    private FloatingActionButton.OnClickListener explore_listener =
            new FloatingActionButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // Go to explore activity
                    Intent exp_intent = new Intent(getBaseContext(), ExploreActivity.class);
                    exp_intent.putStringArrayListExtra("include_filter", selectedIncludeFilter);
                    exp_intent.putStringArrayListExtra("exclude_filter", selectedExcludeFilter);
                    startActivity(exp_intent);
                }
            };

    private ImageButton.OnClickListener dash_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up dashboard
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.container, new fragment_dashboard()).commit();
                }
            };

    private ImageButton.OnClickListener bookshelf_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up bookshelf
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.container, new fragment_bookshelf()).commit();
                }
            };

    /**
     * Once setting is done Listener
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 1) {
                selectedIncludeFilter = data.getStringArrayListExtra("include_filter");
                selectedExcludeFilter = data.getStringArrayListExtra("exclude_filter");

                for (String n: selectedIncludeFilter)
                    System.out.println(n);
            }
        }
    }
}
