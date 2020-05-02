package com.example.fudbook.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fudbook.MainActivity;
import com.example.fudbook.R;

public class fragment_dashboard extends Fragment {
    private static final String TAG = "dashboard";

    private ImageButton create_btn;
    private Button explore_btn;
    private ImageButton bookshelf_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, true);

        create_btn = (ImageButton)view.findViewById(R.id.create_button);
        //explore_btn = (Button)view.findViewById(R.id.explore_button);
        bookshelf_btn = (ImageButton)view.findViewById(R.id.bookshelf_button);

        create_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });

        bookshelf_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).setViewPager(5);
            }
        });

        // PLANNED EXPLORE BUTTON
//        explore_btn.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v){
//                ((MainActivity)getActivity()).setViewPager(3);
//            }
//        });

        return view;
    }
}


