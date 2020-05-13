package com.example.fudbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.example.fudbook.ui.create.fragment_create_1;
import com.example.fudbook.ui.dashboard.fragment_dashboard;

public class CreateActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "CreateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.create_container, new fragment_create_1()).commit();

    }
}
