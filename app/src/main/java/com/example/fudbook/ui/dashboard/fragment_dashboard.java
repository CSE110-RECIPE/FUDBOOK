package com.example.fudbook.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.fudbook.R;
import com.example.fudbook.ui.create.fragment_create;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class fragment_dashboard extends Fragment {
    private static final String TAG = "dashboard";

    FloatingActionButton create_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);



        // create_button set up
        create_button = view.findViewById(R.id.create_btn);
        create_button.setOnClickListener(create_listener);

        return view;
    }

    private FloatingActionButton.OnClickListener create_listener =
            new FloatingActionButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // fragment manager set up
                    FragmentManager pfm = getParentFragmentManager();
                    pfm.beginTransaction().replace(R.id.container, new fragment_create()).commit();
                }
            };
}


