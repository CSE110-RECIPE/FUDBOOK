package com.example.fudbook.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fudbook.CreateActivity;
import com.example.fudbook.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class fragment_dashboard extends Fragment {
    private static final String TAG = "dashboard";

    FloatingActionButton create_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);



        // create_button set up
        create_button = view.findViewById(R.id.create_btn);
        create_button.setOnClickListener(create_listener);

        return view;
    }

    private FloatingActionButton.OnClickListener create_listener =
            new FloatingActionButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // Go to create recipe activity
                    Intent create_intent = new Intent(getContext(), CreateActivity.class);
                    startActivity(create_intent);
                }
            };
}


