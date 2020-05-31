package com.example.fudbook.ui.bookshelf;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.example.fudbook.objects.Recipe;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


// View a book
public class fragment_bookshelf_2 extends Fragment {

    private book_adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    private FragmentManager fm;
    private Fragment f;
    private Bundle data;

    private ArrayList<Recipe> recipe_list;
    private ArrayList<String> recipe_id;

    private ImageButton back_btn;

    // Connection
    private RequestQueue requestQueue;
    private String API_URL = "http://10.0.2.2:3000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_2, container, false);

        //button listener
        back_btn = view.findViewById(R.id.back_butn);
        back_btn.setOnClickListener(b_listener);

        // initialize values
        data = getArguments();


        recipe_id = data.getStringArrayList("recipe id"); //

        System.out.println(recipe_id);
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


        // POST request to get recipes for book
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, API_URL + "/recipe/book",
                bookBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Adding recipes");
                System.out.println(response);
                try {

                    JSONArray ja = response.names();
                    JSONObject curr;
                    String author = null;
                    String name = null;
                    String id = null;
                    String image = null;
                    String recipe_key;

                    System.out.println(ja);
                    System.out.println(response);

                    for(int i = 0; i < ja.length(); i++){
                        curr = response.getJSONObject(ja.getString(i));
                        author = curr.getString("author");
                        name = curr.getString("name");
                        image = curr.getString("image");

                        Picasso.get().load(image).fetch();

                        JSONArray ingredients_ja = curr.getJSONArray("ingredients");
                        JSONArray instructions_ja = curr.getJSONArray("steps");
                        JSONArray tags_ja = curr.getJSONArray("tags");

                        // convert from JSONArray to ArrayList
                        ArrayList<String> ingredients = toArrayList(ingredients_ja);
                        ArrayList<String> instructions = toArrayList(instructions_ja);
                        ArrayList<String> tags = toArrayList(tags_ja);

                        // place into Recipe List
                        Recipe rec = new Recipe( ja.getString(i),
                                name,
                                author,
                                ingredients,
                                instructions,
                                image,
                                tags);

                        //combine the recipe instructions
                        rec.setInstr(instructions);

                        recipe_list.add(rec);


                    }
//                    // change using names and a for loop
//                    Iterator<String> recipe_iterator = response.keys();
//                    while(recipe_iterator.hasNext()){
//                        String key = recipe_iterator.next();
//                        JSONObject jo = response.getJSONObject(key);
//
//                        // grab values from response
//                        String author = jo.getString("author");
//                        String name = jo.getString("name");
//                        String image = jo.getString("image");
//
//                        // pre load image
//                        Picasso.get().load(image).fetch();
//
//                        JSONArray ingredients_ja = jo.getJSONArray("ingredients");
//                        JSONArray instructions_ja = jo.getJSONArray("steps");
//                        JSONArray tags_ja = jo.getJSONArray("tags");
//
//                        // convert from JSONArray to ArrayList
//                        ArrayList<String> ingredients = toArrayList(ingredients_ja);
//                        ArrayList<String> instructions = toArrayList(instructions_ja);
//                        ArrayList<String> tags = toArrayList(tags_ja);
//
//                        // place into Recipe List
//                        Recipe rec = new Recipe( key,
//                                name,
//                                author,
//                                ingredients,
//                                instructions,
//                                image,
//                                tags);
//
//                        //combine the recipe instructions
//                        rec.setInstr(instructions);
//
//                        recipe_list.add(rec);
//                    }

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

    private final book_adapter.OnItemClickListener adapter_listener = new book_adapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(getContext(), "Loading Recipe", Toast.LENGTH_SHORT).show();

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
            f = new fragment_recipe();
            f.setArguments(data); // send data
            fm.beginTransaction().add(R.id.bookshelf_container, f, "RECIPE").commit();
        }

        @Override
        public void onImageButtonClick(final int position) {
            AlertDialog dialog = new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Remove recipe from book?")
                    .setMessage("Are you sure you want to remove this recipe?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // API request
                            requestQueue = Volley.newRequestQueue(getContext());

                            JSONObject deleteBody = new JSONObject();
                            String uid = FirebaseAuth.getInstance().getUid();

                            System.out.println("accumulating body request for DELETE");

                            // creates a body for request
                            try {
                                // get user id
                                deleteBody.accumulate("uid", uid);

                                // get book id
                                deleteBody.accumulate("book_id", data.getString("book id"));

                                String x = mAdapter.getRecipe(position).getRecipeId();
                                // get recipe id
                                deleteBody.accumulate("recipe_id", mAdapter.getRecipe(position).getRecipeId());

                                System.out.println(mAdapter.getRecipe(position).getRecipeId());
                            } catch (Exception e) {
                                System.out.println("accumulation error");
                            }


                            // DELETE request to delete a recipe from a book
                            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST,
                                    API_URL + "/recipe/book/delete",
                                    deleteBody,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            System.out.println(response);
                                            try {
                                                System.out.println("TRYING DELETE");
                                                Bundle bundle = new Bundle();

                                                // remove from adapter
                                                mAdapter.remove(position);

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
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

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

    //back button listener
    private Button.OnClickListener b_listener = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm2 = getParentFragmentManager();
            Fragment fragment = fm2.findFragmentByTag("BOOKSHELF2");
            if(fragment !=null) {
                fm2.beginTransaction().remove(fragment).commit();
            }
        }
    };
}
