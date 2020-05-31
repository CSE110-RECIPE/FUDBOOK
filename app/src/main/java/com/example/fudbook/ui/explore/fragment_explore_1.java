package com.example.fudbook.ui.explore;

import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fudbook.MainActivity;
import com.example.fudbook.R;

public class fragment_explore_1 extends Fragment {

//    Button exit_button; // go back to dash

    private FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_1, container, false);

        Bundle data = getArguments();
        fm = getChildFragmentManager();

        if (data != null) {
            Fragment recipeFrag = new fragment_explore_recipe();
            recipeFrag.setArguments(data);
            fm.beginTransaction().add(R.id.explore_container, recipeFrag).commit();
        } else {
            Toast.makeText(getContext(), "No matches :(", Toast.LENGTH_LONG).show();
        }

        return view;
    }
}
