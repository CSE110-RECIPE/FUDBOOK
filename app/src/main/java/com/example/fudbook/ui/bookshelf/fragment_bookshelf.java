package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fudbook.R;

public class fragment_bookshelf extends Fragment {
    private static final String TAG = "bookshelf";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);

        Bundle bundle = getArguments();

        FragmentManager fm = getChildFragmentManager();
        Fragment bookshelf = new fragment_bookshelf_1();
        bookshelf.setArguments(bundle);
        fm.beginTransaction().add(R.id.bookshelf_container, bookshelf).commit();

        return view;
    }
}
