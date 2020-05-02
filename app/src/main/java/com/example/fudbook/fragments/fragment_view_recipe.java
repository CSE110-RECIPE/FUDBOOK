package com.example.fudbook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fudbook.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_view_recipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_view_recipe extends Fragment {
    private static final String TAG = "bookshelf1";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_recipe, container, true);
        return view;
    }
}
