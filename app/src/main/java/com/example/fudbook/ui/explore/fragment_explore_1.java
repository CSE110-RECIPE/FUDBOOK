package com.example.fudbook.ui.explore;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fudbook.R;

public class fragment_explore_1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_1, container, false);

        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().add(R.id.explore_container, new fragment_explore_recipe()).commit();

        return view;
    }
}
