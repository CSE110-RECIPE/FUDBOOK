package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fudbook.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_bookshelf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_bookshelf extends Fragment {
    private static final String TAG = "bookshelf";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);

        return view;
    }
}
