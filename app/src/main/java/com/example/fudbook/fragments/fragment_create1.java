package com.example.fudbook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fudbook.R;


public class fragment_create1 extends Fragment {
    private static final String TAG = "create1";

    private FragmentManager cFragmentManager;
    private Fragment cFragment;

    private Button nextCreate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create1, container, true);

        nextCreate = (Button) view.findViewById(R.id.btn_to_create2);

//        nextCreate.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v){
//                cFragment = new fragment_create2();
//                cFragmentManager = getChildFragmentManager();
//                FragmentTransaction transaction = cFragmentManager.beginTransaction();
//                transaction.replace(R.id.fragment_create2, cFragment);
//                transaction.commit();
//            }
//        });

        return view;
    }


}
