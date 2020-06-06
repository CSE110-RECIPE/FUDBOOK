package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fudbook.R;
import com.example.fudbook.objects.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;



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

    private Bundle data;
    private Bundle book_bundle;

    // Connection
    private RequestQueue requestQueue;
    private static final String API_URL = "http://10.0.2.2:3000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View set up
        View view = inflater.inflate(R.layout.fragment_bookshelf_1, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.books_recycler);
        recyclerView.setHasFixedSize(true);

        titles = new ArrayList<String>();
        books = new ArrayList<Book>();

        // memory set up
        data = getArguments();

        System.out.println(data.getString("favorite book"));

        JSONObject bookshelfBody = new JSONObject();
        JSONArray bookIdArr = new JSONArray();

        // API request
        requestQueue = Volley.newRequestQueue(getContext());

        // place favorite book id
        bookIdArr.put(data.getString("favorite book"));

        // place personal book id
        bookIdArr.put(data.getString("personal book"));

        // create a body for request
        try {
            bookshelfBody.accumulate("bookshelf", bookIdArr);
        } catch (Exception e ) { }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, API_URL + "/book/bookshelf",
                bookshelfBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "Printing response");
                    System.out.println(response);
                    RecyclerView recyclerView = getView().findViewById(R.id.books_recycler);

                    // change to typecheck
                    try {

                        JSONArray ja = response.names();
                        JSONObject curr;
                        String author = "";
                        boolean def = false;
                        String name = null;
                        String id = null;
                        JSONObject recipes;
                        String recipe_key;
                        Iterator<String> it;

                        for (int i = 0; i < ja.length(); i++){
                            id = ja.getString(i);
                            System.out.println("current id" + id);
                            curr = response.getJSONObject(id);
                            author = curr.getString("author");
                            def = curr.getBoolean("default");
                            name = curr.getString("name");

                            if(curr.has("recipes")){
                                recipes = curr.getJSONObject("recipes");
                                it = recipes.keys();
                                recipeList = new ArrayList<String>();

                                while (it.hasNext()) {
                                    recipe_key = it.next();
                                    recipeList.add(recipe_key);
                                }
                            }else{
                                recipeList = new ArrayList<String>();
                            }

                            books.add(new Book(id, name, author, def, recipeList));
                        }

                        // set up layout manager
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);

                        // define an adapter --> send context, titles, images
                        mAdapter = new bookshelf_adapter(getContext(), books);
                        recyclerView.setAdapter(mAdapter);
                        // set on click listener for each item
                        mAdapter.setOnItemClickListener(adapter_listener);

                    } catch (Exception e) {
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

    private bookshelf_adapter.OnItemClickListener adapter_listener = new bookshelf_adapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

            System.out.println("Printing recipe ids");
            // get book chosen
            Book book = mAdapter.getBook(position);

            if (!book.isEmpty()) {
                for (String i : mAdapter.getBook(position).getRecipes()) {
                    System.out.println("recipe:" + i);
                }

                // store clicked item title into bundle
                data.putStringArrayList("recipe id", book.getRecipes()); // key for recipe id's
            }

            data.putString("book id", book.getBookId());
            data.putString("book name", book.getName());
            // load book's recipes
            FragmentManager fm = getParentFragmentManager();
            Fragment book_frag = new fragment_bookshelf_2();

            // send recipe id's
            book_frag.setArguments(data);
            fm.beginTransaction()
                    .replace(R.id.bookshelf_container, book_frag, "BOOKSHELF2")
                    .commit();

        }
    };

}


