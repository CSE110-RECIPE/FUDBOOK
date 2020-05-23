package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
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
import com.example.fudbook.objects.Recipe;
import com.example.fudbook.ui.fragment_recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


// View a book
public class fragment_bookshelf_2 extends Fragment {

    private book_adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    private FragmentManager fm;
    private Bundle data;

    private ArrayList<Recipe> recipe_list;
    private ArrayList<String> recipe_id;

    // Connection
    private RequestQueue requestQueue;
    private String API_URL = "http://10.0.2.2:3000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_2, container, false);

        // initialize values
        data = getArguments();


        recipe_id = data.getStringArrayList("recipe id"); //
        recipe_list = new ArrayList<Recipe>();

        recyclerView = (RecyclerView) view.findViewById(R.id.books_recycler);
        recyclerView.setHasFixedSize(true);

        /**
         * Load specific book
         */
        // API request
        requestQueue = Volley.newRequestQueue(getContext());

        JSONObject bookBody = new JSONObject();
        JSONArray recipeIdArr = new JSONArray();

        // convert list of recipe_id to json
        for (String i : recipe_id){
            recipeIdArr.put(i);
            System.out.println(i);
        }
        // creates a body for request
        try {
            bookBody.accumulate("recipes", recipeIdArr);
        } catch (Exception e ) {
            System.out.println("accumulation error");
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, API_URL + "/recipe/book",
                bookBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                System.out.println(response);



                try {
                    Iterator<String> recipe_iterator = response.keys();
                    while(recipe_iterator.hasNext()){
                        String key = recipe_iterator.next();
                        JSONObject jo = response.getJSONObject(key);

                        // grab values from response
                        String author = jo.getString("author");
                        String name = jo.getString("name");
                        String image = jo.getString("image");
                        JSONArray ingredients_ja = jo.getJSONArray("ingredients");
                        JSONArray instructions_ja = jo.getJSONArray("steps");
                        JSONArray tags_ja = jo.getJSONArray("tags");

                        // convert from JSONArray to ArrayList
                        ArrayList<String> ingredients = toArrayList(ingredients_ja);
                        ArrayList<String> instructions = toArrayList(instructions_ja);
                        ArrayList<String> tags = toArrayList(tags_ja);

                        // place into Recipe List
                        recipe_list.add(new Recipe(
                                            name,
                                            author,
                                            ingredients,
                                            instructions,
                                            image,
                                            tags));
                    }

                    // set up layout manager
                    layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    mAdapter = new book_adapter(getContext(), recipe_list);
                    recyclerView.setAdapter(mAdapter);

                    mAdapter.setOnItemClickListener(adapter_listener);

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

    private book_adapter.OnItemClickListener adapter_listener = new book_adapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(getContext(),"Loading Recipe",Toast.LENGTH_SHORT).show();

            // load recipe
            Recipe toSend = mAdapter.getRecipe(position);
            data.putString("title", toSend.getTitle());
            data.putString("author", toSend.getAuthor());
            data.putStringArrayList("ingredients", toSend.getIngr());
            data.putStringArrayList("instructions", toSend.getInstr());
            data.putStringArrayList("tags", toSend.getTags());
            data.putString("image", toSend.getImage());

            // open recipe
            fm = getParentFragmentManager();
            Fragment f = new fragment_recipe();
            f.setArguments(data); // send data
            fm.beginTransaction().replace(R.id.bookshelf_container, f).commit();
        }
    };

    private ArrayList<String> toArrayList(JSONArray jArray){
        ArrayList<String> listdata = new ArrayList<String>();
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                try{
                    listdata.add(jArray.getString(i));
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        return listdata;
    };

}
