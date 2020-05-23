package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



// View bookshelf
public class fragment_bookshelf_1 extends Fragment {

    private static final String TAG = "fragment_bookshelf_1";
    //private String recipeList;
    private RecyclerView recyclerView;
    private bookshelf_adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // cell variables
    private ArrayList<String> titles;
    private ArrayList<String> images;
    private ArrayList<Book> books;
    private ArrayList<String> recipeList;

    Bundle data;

    // Connection
    private RequestQueue requestQueue;
    private String API_URL = "http://10.0.2.2:3000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View set up
        View view = inflater.inflate(R.layout.fragment_bookshelf_1, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.books_recycler);
        recyclerView.setHasFixedSize(true);

        titles = new ArrayList<String>();
        books = new ArrayList<Book>();


        // memory set up
        data = getArguments();
        
//        // dummy favorites
//        String image = "https://pluspng.com/img-png/star-png-star-png-image-2156.png";
//        addBook("Favorites", image);

        // API request
        requestQueue = Volley.newRequestQueue(getContext());

        JSONObject bookshelfBody = new JSONObject();
        JSONArray bookIdArr = new JSONArray();

        /** Test Recipes */
        bookIdArr.put("-M7GhOakF1BvqzTvlcgT"); // favorite
        bookIdArr.put("-M7UtJzTbCzmwpHdpgaQ"); // my recipes

        // creates a body for request
        try {
            bookshelfBody.accumulate("bookshelf", bookIdArr);
        } catch (Exception e ) { }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, API_URL + "/book/bookshelf",
                bookshelfBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "Printing response");

                    System.out.println(response);

                    // change to typecheck
                    try {
                        // for loop to get every book title
                        Iterator<String> book_iterator = response.keys();
                        while(book_iterator.hasNext()) {
                            String key = book_iterator.next();
                            //Extract information from each book
                            JSONObject jo = response.getJSONObject(key);
                            String author = jo.getString("author");
                            boolean def = jo.getBoolean("default");
                            String name = jo.getString("name");

                            //Extract recipes from each book
                            JSONArray rec_arr = jo.getJSONArray("recipes");
                            recipeList = new ArrayList<String>();
                            for(int i = 0; i < rec_arr.length(); i++) {
                                recipeList.add(rec_arr.getString(i));
                            }
                            books.add(new Book(name, author, def, recipeList));
                            titles.add(name);
                            data.putStringArrayList("recipe id", recipeList); // key for recipe id's
                        }
                        // set up layout manager
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);

                        // define an adapter --> send context, titles, images
                        mAdapter = new bookshelf_adapter(getContext(), titles, images);
                        recyclerView.setAdapter(mAdapter);
                        // set on click listener for each item
                        mAdapter.setOnItemClickListener(adapter_lister);

                    } catch (Exception e)
                    {
                        System.out.print(e);
                    }
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

    private bookshelf_adapter.OnItemClickListener adapter_lister = new bookshelf_adapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

            // store clicked item title into bundle
            
            // load book's recipes
            FragmentManager fm = getParentFragmentManager();
            Fragment book_frag = new fragment_bookshelf_2();

            // send recipe id's
            book_frag.setArguments(data);
            fm.beginTransaction()
                    .replace(R.id.bookshelf_container, book_frag)
                    .commit();

        }
    };

}


