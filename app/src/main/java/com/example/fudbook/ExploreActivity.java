package com.example.fudbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.fudbook.ui.bookshelf.fragment_bookshelf;
import com.example.fudbook.ui.dashboard.fragment_dashboard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExploreActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "ExploreActivity";

    // buttons
    private ImageButton d_dashboard_button;
    private ImageButton d_bookshelf_button;
    private FloatingActionButton d_basket_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.container, new fragment_dashboard()).commit();

        // button set up
        d_dashboard_button = findViewById(R.id.dash_btn);
        d_bookshelf_button = findViewById(R.id.bookshelf_btn);
        d_basket_button = findViewById(R.id.basket_btn); // FLOATING BUTTON

        d_dashboard_button.setOnClickListener(dash_listener);
        d_bookshelf_button.setOnClickListener(dash_listener);
        d_basket_button.setOnClickListener(basket_listener);
    }

    private FloatingActionButton.OnClickListener basket_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {

                    // LOAD BASKET

                }
            };

    private ImageButton.OnClickListener dash_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // go back to main activity and load dashboard
                    Intent exp_intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(exp_intent);
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
}
