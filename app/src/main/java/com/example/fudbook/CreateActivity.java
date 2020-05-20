package com.example.fudbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.fudbook.ui.FragmentAdapter;
import com.example.fudbook.ui.create.fragment_create_1;
import com.example.fudbook.ui.create.fragment_create_2;
import com.example.fudbook.ui.dashboard.fragment_dashboard;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CreateActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "CreateActivity";

    // create an adapter
    private FragmentAdapter fragmentAdapter;
    private ViewPager2 viewPager;

    // overlaying buttons
    Button next_button;
    Button back_button; // need to implement

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

//        FragmentManager fm = getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.create_container, new fragment_create_1()).commit();

        Log.d(TAG, "onCreate: Setting up viewPager \n");

        viewPager = findViewById(R.id.create_container);
        setupViewPager(viewPager);

        // button set up
        next_button = findViewById(R.id.next_btn);
        back_button = findViewById(R.id.back_btn);

        // listener set up
        next_button.setOnClickListener(next_listener);
        back_button.setOnClickListener(back_listener);


    }

    private Button.OnClickListener back_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up last create page
                    int current = viewPager.getCurrentItem();
                    if (current != 0)
                        setViewPager(current - 1);
                    else {
                        Intent goToDash = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(goToDash);
                    }
                }
            };

    private Button.OnClickListener next_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up next create page
                    int current = viewPager.getCurrentItem();
                    if(current != 3)
                        setViewPager(current+1);
                }
            };

    private void setupViewPager(ViewPager2 viewPager) {
        Log.d(TAG, "setupViewPager: adding create fragments");

        // adapter to send to viewpager
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());

        fragmentAdapter.addFragment(new fragment_create_1(), "Create Recipe Step 1"); // 0
        fragmentAdapter.addFragment(new fragment_create_2(), "Create Recipe Step 2"); // 1
        //adapter.addFragment(new fragment_create_3()), "Create Recipe Step 3"; // 2

        viewPager.setAdapter(fragmentAdapter);
        setViewPager(0);
    }

    // sets viewpager to certain fragment
    public void setViewPager(int fragmentNumber){
        Log.d(TAG, "setViewPager");

        viewPager.setCurrentItem(fragmentNumber);
    }
}
