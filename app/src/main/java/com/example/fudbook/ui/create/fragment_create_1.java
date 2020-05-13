package com.example.fudbook.ui.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fudbook.R;
import com.example.fudbook.ui.bookshelf.fragment_bookshelf;


public class fragment_create_1 extends Fragment {

    Button next_button;
    Button back_button; // need to implement

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_1, container, false);

        // button set up
        next_button = view.findViewById(R.id.next_btn);
        


        // listener set up
        next_button.setOnClickListener(next_listener);



        return view;
    }

    private Button.OnClickListener back_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up next create page
                    FragmentManager fm = getParentFragmentManager();
                    fm.beginTransaction().replace(R.id.create_container, new fragment_create_2()).commit();
                }
            };

    private Button.OnClickListener next_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up next create page
                    FragmentManager fm = getParentFragmentManager();
                    fm.beginTransaction().replace(R.id.create_container, new fragment_create_2()).commit();
                }
            };
}
