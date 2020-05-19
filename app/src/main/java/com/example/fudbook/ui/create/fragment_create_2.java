package com.example.fudbook.ui.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fudbook.CreateActivity;
import com.example.fudbook.R;

public class fragment_create_2 extends Fragment {

    private Button upload_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_2, container, false);

        upload_btn = view.findViewById(R.id.add_photo_btn);

        upload_btn.setOnClickListener(upload_listener);


        return view;
    }

    private Button.OnClickListener upload_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // stuff


        }
    };


}
