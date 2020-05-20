package com.example.fudbook.ui.explore;

import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fudbook.MainActivity;
import com.example.fudbook.R;

public class fragment_explore_1 extends Fragment {

    Button exit_button; // go back to dash


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_1, container, false);

        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().add(R.id.explore_container, new fragment_explore_recipe()).commit();


        exit_button.setOnClickListener(exit_explore_listener);

        return view;
    }

    private Button.OnClickListener exit_explore_listener =
            new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // go back to main activity and load dashboard
                    Intent exp_intent = new Intent(getContext(), MainActivity.class);
                    startActivity(exp_intent);
                }
            };
}
