package com.example.fudbook.ui.explore;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fudbook.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class fragment_basket extends Fragment {

    private FragmentManager fm;

    private ArrayList<String> selectedRecipeId, selectedRecipeName, selectedRecipeImage,
            ingredientList;
    private ArrayList<Integer> ingredientKey;

    private ArrayList<Item> itemList;

    private ItemAdapter adapter;

    private RequestQueue requestQueue;

    private String favorite;

    private boolean done;

    private static final String API_URL = "http://10.0.2.2:3000";

    public class Item {
        private String name;
        private String imageURL;
        private ArrayList<String> ingredientList;
        private String UID;

        public Item(String recipeName, String recipeUID) {
            name = recipeName;
            imageURL = recipeUID;
        }

        public Item(String recipeName, String recipeUID, String recipeImageURL,
                    ArrayList<String> ingredients) {
            name = recipeName;
            UID = recipeUID;
            imageURL = recipeImageURL;
            ingredientList = ingredients;
        }

        public String getName() {return name;}
        public String getUID() {return UID;}
        public String getImageURL() {return imageURL;}
        public ArrayList<String> getIngredientList() {return ingredientList;}
    }

    public class ItemAdapter extends ArrayAdapter<Item> {
        public ItemAdapter(Context c, ArrayList<Item> itemList) {
            super(c, 0, itemList);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Item item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_basket_item, parent, false);
                ChipGroup chipGroup = convertView.findViewById(R.id.chip_group);
                ImageView imageView = convertView.findViewById(R.id.imageView);

                ArrayList<String> ingredientList = item.getIngredientList();

                for (int i = 0; i < ingredientList.size(); i++) {
                    Chip c = new Chip(convertView.getContext());
                    c.setText(ingredientList.get(i));
                    chipGroup.addView(c);
                }

                Picasso.get().load(item.getImageURL()).networkPolicy(NetworkPolicy.OFFLINE)
                        .fit()
                        .centerCrop()
                        .into(imageView);

                TextView nameTV = convertView.findViewById(R.id.basket_recipe_name);
                TextView variable = convertView.findViewById(R.id.basket_item_var);
                variable.setId(position);
                nameTV.setText(item.getName());
            }
            return convertView;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        Bundle data = getArguments();

        // Initiate Request queue;
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();

        // Button view
        Button addBtn = view.findViewById(R.id.add_btn);

        // Button Listener
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject dataJSON = new JSONObject();
                JSONArray recipesJSON = new JSONArray(selectedRecipeId);

                try {
                    dataJSON.accumulate("book_id", favorite);
                    dataJSON.accumulate("recipes", recipesJSON);
                } catch (Exception e) {}

                JsonObjectRequest joR = new JsonObjectRequest(Request.Method.POST,
                        API_URL + "/book/recipe",
                        dataJSON,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("Added to favorite");
                                adapter.clear();
                                done = true;
                                Bundle data = new Bundle();
                                data.putBoolean("done", done);
                                getParentFragmentManager().setFragmentResult("isClear", data);
                                Toast.makeText(getContext(), "Added to Favorite", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error);
                            }
                        });

                requestQueue.add(joR);
            }
        });

        // Initialize field
        selectedRecipeId = data.getStringArrayList("recipe ID list");
        selectedRecipeName = data.getStringArrayList("recipe name list");
        selectedRecipeImage = data.getStringArrayList("imageURL");
        ingredientKey = data.getIntegerArrayList("ingredients key");
        ingredientList = data.getStringArrayList("ingredients list");
        itemList = new ArrayList<Item>();
        favorite = data.getString("favorite");
        done = false;

        int count = 0;

        // Parse ArrayList to Items
        for (int i = 0; selectedRecipeId != null && i < selectedRecipeId.size(); i++) {
            ArrayList<String> currentIngredientList = new ArrayList<>();

            // parse ingredient for each recipe
            int size = ingredientKey.get(i);

            for (int l = 0; l < size; l++) {
                currentIngredientList.add(ingredientList.get(count + l));
            }

            count = size;

            itemList.add(new Item(selectedRecipeName.get(i),selectedRecipeId.get(i)
                    ,selectedRecipeImage.get(i), currentIngredientList));
        }

        adapter = new ItemAdapter(getContext(), itemList);

        ListView list = view.findViewById(R.id.basket_container);
        list.setAdapter(adapter);
        return view;
    }

    public void removeItem(int position) {
        adapter.remove(adapter.getItem(position));
    }

    public void onStop() {
        super.onStop();

        requestQueue.stop();
    }
}
