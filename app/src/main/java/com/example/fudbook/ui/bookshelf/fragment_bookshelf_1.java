package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fudbook.R;
import com.example.fudbook.ui.explore.fragment_explore_recipe;

import java.util.ArrayList;
import java.util.List;



// View bookshelf
public class fragment_bookshelf_1 extends Fragment {

    private RecyclerView recyclerView;
    private bookshelf_adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_1, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.books_recycler);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        /**
         * TESTING HERE
         *
         * Need to load "favorites"
         * Need to load "saved for later"
         * Need to load "my recipes"
         *
         */

        // test load for text
        ArrayList<String> images = new ArrayList<>(); // images
        ArrayList<String> input = new ArrayList<>(); // titles
//        for (int i = 0; i < 10; i++) {
//            input.add("Test" + i);
//        }


        input.add("Big Green Smoothie");
        // images

        images.add("https://www.cookingclassy.com/wp-content/uploads/2019/07/green-smoothie-10.jpg");

        // define an adapter
        mAdapter = new bookshelf_adapter(getContext(), input, images);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new bookshelf_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                // load book's recipes
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction().add(R.id.bookshelf_container, new fragment_bookshelf_2()).commit();

                // send array of recipes to next fragment loaded into Bundle
            }
        });

        /**
         * END TESTING
         */

        return view;
    }
}
