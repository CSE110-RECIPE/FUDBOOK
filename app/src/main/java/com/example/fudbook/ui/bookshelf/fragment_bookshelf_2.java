package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fudbook.R;

import java.util.ArrayList;
import java.util.List;


// View a book
public class fragment_bookshelf_2 extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_1, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.books_recycler);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // test load
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            input.add("Test" + i);
        }

        // define an adapter
        mAdapter = new bookshelf_adapter(input);
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
