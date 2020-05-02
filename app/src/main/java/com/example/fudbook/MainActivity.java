package com.example.fudbook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fudbook.fragments.fragment_bookshelf1;
import com.example.fudbook.fragments.fragment_bookshelf2;
import com.example.fudbook.fragments.fragment_create1;
import com.example.fudbook.fragments.fragment_create2;
import com.example.fudbook.fragments.fragment_create3;
import com.example.fudbook.fragments.fragment_create4;
import com.example.fudbook.fragments.fragment_dashboard;
/*
        MAIN ACTIVITY:
            HOLDS ALL FRAGMENTS FOR DASHBOARD:
                - CREATE
                - BOOKSHELF
 */


public class MainActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "MainActivity";

    // initializations
    private Toolbar toolbar;

    private FragmentAdapter mFragmentAdapter;
    private ViewPager2 mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // starting create
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

        // setting up toolbar
        setupToolBar();


        // set up fragment adapter
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        mViewPager = (ViewPager2) findViewById(R.id.container);
        setupViewPager(mViewPager);
    }

    private void setupToolBar(){
        toolbar = findViewById(R.id.toolbarTop);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_home_white_50dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"homebutton clicked", Toast.LENGTH_SHORT).show();
                setViewPager(1);
            }
        });
    }

    // adds fragments for dash
    private void setupViewPager(ViewPager2 viewPager){
        Log.d(TAG, "setupViewPager: adding fragments");

        // adapter to send to viewpager
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());

        // fragments to adds in this order [0...n]
        adapter.addFragment(new fragment_create1(), "Create Page 1"); // 0
        adapter.addFragment(new fragment_dashboard(), "Dashboard"); // 1
        adapter.addFragment(new fragment_bookshelf1(), "Bookshelf Page 1"); // 2

        // NEED TO ADD FOR EXPLORE?

        // connect viewpager to made adapter
        viewPager.setAdapter(adapter);
        setViewPager(1);
    }

    public void setViewPager(int fragmentNumber){
        Log.d(TAG, "setViewPager");

        mViewPager.setCurrentItem(fragmentNumber);
    }
}
