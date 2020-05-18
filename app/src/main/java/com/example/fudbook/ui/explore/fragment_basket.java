package com.example.fudbook.ui.explore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.fudbook.R;

public class fragment_basket extends Fragment {

    private FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        fm = getChildFragmentManager();

        for (int i = 0; i < 5; i++)
          fm.beginTransaction().add(R.id.basket_container, new fragment_basket_item()).commit();

        return view;
    }
}
