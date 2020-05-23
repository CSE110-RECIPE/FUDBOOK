package com.example.fudbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fudbook.ui.explore.fragment_basket;
import com.example.fudbook.ui.explore.fragment_explore_1;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "ExploreActivity";

    // fragment for basket
    private Fragment basketFragment;
    private FragmentManager fm;

    // checks if basket is open
    private boolean isBasketOpen;

    // filter
    private ArrayList<String> selectedIncludeFilter, selectedExcludeFilter;

    // basket recipes
    private ArrayList<String> selectedRecipeName, selectedRecipeId, ingredientList,
            selectedRecipeImageURL;
    private ArrayList<Integer> ingredientKey;

    // recipe list
    private JSONObject recipeList;
    private JSONArray recipeIdList;

    // API connection
    private RequestQueue requestQueue;
    private static final String API_URL = "http://10.0.2.2:3000";
    private static final String ADMIN_UID = "Lajm0tmKJEhTHNGxVR6OsQR9rAZ2";

    // On Starting Explore Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

        isBasketOpen = false;

        requestQueue = Volley.newRequestQueue(this);

        fm = getSupportFragmentManager();

        selectedIncludeFilter = getIntent().getStringArrayListExtra("include_filter");
        selectedExcludeFilter = getIntent().getStringArrayListExtra("exclude_filter");
        selectedRecipeName = new ArrayList<>();
        selectedRecipeId = new ArrayList<>();
        selectedRecipeImageURL = new ArrayList<>();
        ingredientList = new ArrayList<>();
        ingredientKey = new ArrayList<>();

        JSONArray includeFilter = new JSONArray(selectedIncludeFilter);
        JSONArray excludeFilter = new JSONArray(selectedExcludeFilter);

        JSONObject filterJSON = new JSONObject();

        try {
            filterJSON.accumulate("include_filter", includeFilter);
            filterJSON.accumulate("exclude_filter", excludeFilter);
        } catch (Exception e) {}

        final String requestBody = filterJSON.toString();

        System.out.println(requestBody);

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, API_URL + "/recipe/filter",
                filterJSON,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        recipeList = response;
                        recipeIdList = recipeList.names();
                        Fragment currentRecipeFrag = new fragment_explore_1();
                        Bundle data = new Bundle();

                        try {
                            if (recipeIdList.length() > 0) {
                                JSONObject recipeObj = recipeList.getJSONObject(recipeIdList.get(0).toString());

                                // parse ingredients
                                JSONArray ingredientArr = recipeObj.getJSONArray("tags");
                                ArrayList<String> ingredientList = new ArrayList<>();

                                for (int i = 0; i < ingredientArr.length(); i++)
                                    ingredientList.add(ingredientArr.getString(i));

                                // parse steps
                                JSONArray stepsArr = recipeObj.getJSONArray("steps");
                                ArrayList<String> stepsList = new ArrayList<>();

                                for (int i = 0; i < stepsArr.length(); i++)
                                    stepsList.add(stepsArr.getString(i));

                                for (int i = 0; i < recipeIdList.length(); i++) {
                                    /**
                                     * TODO: Need to change URL
                                     */
                                    Picasso.get().load("https:" + recipeList.getJSONObject(
                                            recipeIdList.getString(i)).getString("image"))
                                            .fetch();
                                }

                                data.putString("name", recipeObj.getString("name"));
                                data.putStringArrayList("ingredients", ingredientList);
                                data.putStringArrayList("steps", stepsList);
                                data.putString("imageURL", recipeObj.getString("image"));

                                currentRecipeFrag.setArguments(data);
                            }
                        } catch (Exception e) { }
                        fm.beginTransaction().add(R.id.exp_container, currentRecipeFrag).commit();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        requestQueue.add(sr);
    }

    public void onPause() {
        super.onPause();

    }

    public void exitBasket(View v) {
        if (basketFragment != null)
            fm.beginTransaction().remove(basketFragment).commit();

        isBasketOpen = false;
    }

    public void enterBasket(View v) {
        if (!isBasketOpen) {
            basketFragment = new fragment_basket();
            Bundle data = new Bundle();
            data.putStringArrayList("recipe ID list", selectedRecipeId);
            data.putStringArrayList("recipe name list", selectedRecipeName);
            data.putIntegerArrayList("ingredients key", ingredientKey);
            data.putStringArrayList("ingredients list", ingredientList);
            data.putStringArrayList("imageURL", selectedRecipeImageURL);
            basketFragment.setArguments(data);
            fm.beginTransaction().add(R.id.exp_container, basketFragment).commit();
            isBasketOpen = true;
        }
    }

    public void exitExplore(View v) {
       ;
    }

    public void saveInBasket(View v) {
        try {
            JSONObject recipeObj = recipeList.getJSONObject(recipeIdList.get(0).toString());

            selectedRecipeId.add(recipeIdList.get(0).toString());
            selectedRecipeName.add(recipeObj.getString("name"));
            selectedRecipeImageURL.add(recipeObj.getString("image"));

            int keySize = recipeObj.getJSONArray("tags").length();
            ingredientKey.add(keySize);

            for (int i = 0; i < keySize; i++) {
                ingredientList.add(recipeObj.getJSONArray("tags").getString(i));
            }

            recipeIdList.remove(0);

            if (recipeIdList.length() > 0) {
               recipeObj = recipeList.getJSONObject(recipeIdList.get(0).toString());

                Fragment newRecipeFrag = new fragment_explore_1();
                Bundle data = new Bundle();

                // parse ingredients
                JSONArray ingredientArr = recipeObj.getJSONArray("tags");
                ArrayList<String> ingredientList = new ArrayList<>();

                for (int i = 0; i < ingredientArr.length(); i++)
                    ingredientList.add(ingredientArr.getString(i));

                // parse steps
                JSONArray stepsArr = recipeObj.getJSONArray("steps");
                ArrayList<String> stepsList = new ArrayList<>();

                for (int i = 0; i < stepsArr.length(); i++)
                    stepsList.add(stepsArr.getString(i));

                data.putString("name", recipeObj.getString("name"));
                data.putStringArrayList("ingredients", ingredientList);
                data.putStringArrayList("steps", stepsList);
                data.putString("imageURL", recipeObj.getString("image"));

                newRecipeFrag.setArguments(data);

                fm.beginTransaction().replace(R.id.exp_container, newRecipeFrag).commit();
            } else {
                fm.beginTransaction().replace(R.id.exp_container, new fragment_explore_1()).commit();
            }
        } catch (Exception e) {}
    }

    public void discard(View v) {
        try {
            recipeIdList.remove(0);

            if (recipeIdList.length() > 0) {
                JSONObject recipeObj = recipeList.getJSONObject(recipeIdList.get(0).toString());

                Fragment newRecipeFrag = new fragment_explore_1();
                Bundle data = new Bundle();

                // parse ingredients
                JSONArray ingredientArr = recipeObj.getJSONArray("tags");
                ArrayList<String> ingredientList = new ArrayList<>();

                for (int i = 0; i < ingredientArr.length(); i++)
                    ingredientList.add(ingredientArr.getString(i));

                // parse steps
                JSONArray stepsArr = recipeObj.getJSONArray("steps");
                ArrayList<String> stepsList = new ArrayList<>();

                for (int i = 0; i < stepsArr.length(); i++)
                    stepsList.add(stepsArr.getString(i));

                data.putString("name", recipeObj.getString("name"));
                data.putStringArrayList("ingredients", ingredientList);
                data.putStringArrayList("steps", stepsList);
                data.putString("imageURL", recipeObj.getString("image"));
                newRecipeFrag.setArguments(data);

                fm.beginTransaction().replace(R.id.exp_container, newRecipeFrag).commit();
            } else {
                fm.beginTransaction().replace(R.id.exp_container, new fragment_explore_1()).commit();
            }
        } catch (Exception e) {}
    }
}
