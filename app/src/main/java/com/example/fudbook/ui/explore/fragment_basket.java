package com.example.fudbook.ui.explore;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fudbook.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class fragment_basket extends Fragment {

    private FragmentManager fm;

    private ArrayList<String> selectedRecipeId, selectedRecipeName, selectedRecipeImage,
            ingredientList;
    private ArrayList<Integer> ingredientKey;

    private ArrayList<Item> itemList;

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
            }

            ChipGroup chipGroup = convertView.findViewById(R.id.chip_group);
            ImageView imageView = convertView.findViewById(R.id.imageView);

            ArrayList<String> ingredientList = item.getIngredientList();

            for (int i = 0; i < ingredientList.size(); i++) {
                Chip c = new Chip(convertView.getContext());
                c.setText(ingredientList.get(i));
                chipGroup.addView(c);
            }

            Picasso.get().load("https:" + item.getImageURL()).networkPolicy(NetworkPolicy.OFFLINE)
                    .fit()
                    .centerCrop()
                    .into(imageView);

            TextView nameTV = convertView.findViewById(R.id.basket_recipe_name);
            nameTV.setText(item.getName());
            return convertView;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        Bundle data = getArguments();

        selectedRecipeId = data.getStringArrayList("recipe ID list");
        selectedRecipeName = data.getStringArrayList("recipe name list");
        selectedRecipeImage = data.getStringArrayList("imageURL");
        ingredientKey = data.getIntegerArrayList("ingredients key");
        ingredientList = data.getStringArrayList("ingredients list");
        itemList = new ArrayList<Item>();
        int count = 0;

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

        ItemAdapter adapter = new ItemAdapter(getContext(), itemList);

        ListView list = view.findViewById(R.id.basket_container);
        list.setAdapter(adapter);
        return view;
    }
}
