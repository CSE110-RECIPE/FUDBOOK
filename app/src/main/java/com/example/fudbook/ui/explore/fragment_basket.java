package com.example.fudbook.ui.explore;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fudbook.R;

import java.util.ArrayList;

public class fragment_basket extends Fragment {

    private FragmentManager fm;

    private ArrayList<String> selectedRecipeId;
    private ArrayList<String> selectedRecipeName;
    private ArrayList<Item> itemList;

    public class Item {
        private String name;
        private String imageURL;
        private String UID;

        public Item(String recipeName, String recipeUID) {
            name = recipeName;
            imageURL = recipeUID;
        }

        public Item(String recipeName, String recipeUID, String recipeImageURL) {
            name = recipeName;
            UID = recipeUID;
            imageURL = recipeImageURL;
        }

        public String getName() {return name;}
        public String getUID() {return UID;}
        public String getImageURL() {return imageURL;}
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
        itemList = new ArrayList<Item>();

        for (int i = 0; i < selectedRecipeId.size(); i++) {
            itemList.add(new Item(selectedRecipeName.get(i),selectedRecipeId.get(i)));
        }

        ItemAdapter adapter = new ItemAdapter(getContext(), itemList);

        ListView list = view.findViewById(R.id.basket_container);
        list.setAdapter(adapter);
        return view;
    }
}
