package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fudbook.R;
import com.example.fudbook.objects.Book;
import com.example.fudbook.ui.explore.fragment_explore_recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;



// View bookshelf
public class fragment_bookshelf_1 extends Fragment {

    private RecyclerView recyclerView;
    private bookshelf_adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // cell variables
    ArrayList<String> titles;
    ArrayList<String> images;

    // Connection
    private RequestQueue requestQueue;
    private String API_URL = "http://10.0.2.2:3000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // View set up
        View view = inflater.inflate(R.layout.fragment_bookshelf_1, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.books_recycler);
        recyclerView.setHasFixedSize(true);

        // memory set up
        Bundle data = getArguments();
        
        // dummy favorites 
        String image = "https://pluspng.com/img-png/star-png-star-png-image-2156.png";
        addBook("Favorites", image);

        // set up layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter --> send context, titles, images
        mAdapter = new bookshelf_adapter(getContext(), titles, images);
        recyclerView.setAdapter(mAdapter);
        // set on click listener for each item
        mAdapter.setOnItemClickListener(adapter_lister);

        // API request
        requestQueue = Volley.newRequestQueue(getContext());

        JSONObject bookshelfBody = new JSONObject();
        JSONArray bookIdArr = new JSONArray();

        bookIdArr.put("-M7GhOakF1BvqzTvlcgT");
        bookIdArr.put("-M7UtJzTbCzmwpHdpgaQ");

        try {
            bookshelfBody.accumulate("bookshelf", bookIdArr);
        } catch (Exception e ) { }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, API_URL + "/book/bookshelf",
                bookshelfBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response);

                    try {
                        response.getJSONObject("-M7GhOakF1BvqzTvlcgT");
                    } catch (Exception e) {}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });

        requestQueue.add(jor);

        return view;
    }

    private void addBook(String title, String image){
        if (titles == null && images == null){
            titles = new ArrayList<String>();
            images = new ArrayList<String>();
        }
        titles.add(title);
        images.add(image);
    }

    private bookshelf_adapter.OnItemClickListener adapter_lister = new bookshelf_adapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

            // store clicked item title into bundle
            
            // load book's recipes
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.bookshelf_container, new fragment_bookshelf_2())
                    .commit();

            // send recipe id's

        }
    };

}


