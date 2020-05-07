package com.example.fudbook.ui.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fudbook.R;
import com.example.fudbook.ui.dashboard.fragment_dashboard;

public class fragment_create extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().add(R.id.create_container, new fragment_create_1()).commit();

        return view;
    }
}
