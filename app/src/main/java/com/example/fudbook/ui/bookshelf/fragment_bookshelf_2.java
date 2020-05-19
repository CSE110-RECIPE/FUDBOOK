package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fudbook.R;
import com.example.fudbook.objects.Recipe;
import com.example.fudbook.ui.fragment_recipe;

import java.util.ArrayList;
import java.util.List;


// View a book
public class fragment_bookshelf_2 extends Fragment {

    private book_adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_2, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.books_recycler);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        /**
         * Load specific book
         */

        String[] ingredients = {"Peanuts", "Eggs", "Love"};
        String[] instr = {"Put all the stuff in the stuff\n then get more stuff and put stuff in it."};
        String[] tags = {"Peanuts"};
        String img = "https://www.seriouseats.com/images/20070401istockpbjbeauty.jpg";

        /**
         * test
         */
        Recipe test_recipe = new Recipe("test recipe",
                                "Jeremiah",
                                        ingredients,
                                        instr,
                                        tags,
                                        img);

        ArrayList<Recipe> recipe_cont = new ArrayList<Recipe>();
        recipe_cont.add(test_recipe);

        /**
         * End test
         */

        mAdapter = new book_adapter(getContext(), recipe_cont);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new book_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),"Loading Recipe",Toast.LENGTH_SHORT).show();

                // open recipe
                fm = getParentFragmentManager();
                fm.beginTransaction().add(R.id.bookshelf_container, new fragment_recipe()).commit();

                // send specific recipe to loaded fragment into Bundle


            }
        });


        return view;
    }
}
