package com.example.fudbook.ui.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fudbook.CreateActivity;
import com.example.fudbook.ExploreActivity;
import com.example.fudbook.R;
import com.example.fudbook.ui.bookshelf.fragment_bookshelf;


public class fragment_create_1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_1, container, false);

        return view;
    }
}
