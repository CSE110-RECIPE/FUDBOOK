package com.example.fudbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
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
    private fragment_basket basketFragment;
    private FragmentManager fm;

    // checks if basket is open
    private boolean isBasketOpen;

    // filter
    private ArrayList<String> selectedIncludeFilter, selectedExcludeFilter;

    // basket recipes
    private ArrayList<String> selectedRecipeName, selectedRecipeId, ingredientList,
            selectedRecipeImageURL;
    private ArrayList<Integer> ingredientKey;

    // User's favorite book ID
    private String favorite;

    // recipe list
    private JSONObject recipeList;
    private JSONArray recipeIdList;

    // API connection
    private RequestQueue requestQueue;
    private boolean isLoaded;
    private int recipeProgressCount;
    private boolean reloadRequest;
    private static final String API_URL = "http://54.200.224.98:3000";

    // On Starting Explore Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

        // Initialize field
        isBasketOpen = false;
        favorite = getIntent().getStringExtra("favorite");

        System.out.println(favorite);

        // cache set up
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

        fm = getSupportFragmentManager();

        selectedIncludeFilter = getIntent().getStringArrayListExtra("include_filter");
        selectedExcludeFilter = getIntent().getStringArrayListExtra("exclude_filter");
        reloadRequest = getIntent().getBooleanExtra("reload request", false);

        Bundle progressData = getIntent().getBundleExtra("progress data");

        if (progressData != null) {
            selectedRecipeName = progressData.getStringArrayList("recipe name list");
            selectedRecipeId = progressData.getStringArrayList("recipe ID list");
            selectedRecipeImageURL = progressData.getStringArrayList("recipe image list");
            ingredientList = progressData.getStringArrayList("recipe ingredient list");
            ingredientKey = progressData.getIntegerArrayList("recipe key list");
            recipeProgressCount = progressData.getInt("recipe progress count", 0);
            isLoaded = progressData.getBoolean("isLoaded", false);
        } else {
            selectedRecipeName = new ArrayList<>();
            selectedRecipeId = new ArrayList<>();
            selectedRecipeImageURL = new ArrayList<>();
            ingredientList = new ArrayList<>();
            ingredientKey = new ArrayList<>();
        }

        if (reloadRequest) recipeProgressCount = 0;

        JSONArray includeFilter = new JSONArray(selectedIncludeFilter);
        JSONArray excludeFilter = new JSONArray(selectedExcludeFilter);

        JSONObject filterJSON = new JSONObject();

        try {
            filterJSON.accumulate("include_filter", includeFilter);
            filterJSON.accumulate("exclude_filter", excludeFilter);
        } catch (Exception e) {}

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST,
                API_URL + "/recipe/filter",
                filterJSON,
            new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    recipeList = response;
                    recipeIdList = recipeList.names();
                    Fragment currentRecipeFrag = new fragment_explore_1();
                    Bundle data = new Bundle();

                    if (isLoaded && !reloadRequest) {
                        for (int i = 0; i < recipeProgressCount; i++) {
                            recipeIdList.remove(0);
                        }
                    }

                    try {
                        if (recipeIdList.length() > 0) {
                            JSONObject recipeObj = recipeList
                                    .getJSONObject(recipeIdList.getString(0));

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
                                Picasso.get().load(recipeList.getJSONObject(
                                        recipeIdList.getString(i)).getString("image"))
                                        .fetch();
                            }

                            data.putString("name", recipeObj.getString("name"));
                            data.putString("author", recipeObj.getString("author"));
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
            data.putString("favorite", favorite);
            basketFragment.setArguments(data);
            fm.setFragmentResultListener("isClear", basketFragment, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    if (requestKey == "isClear") {
                        if (result.getBoolean("done")) {
                            selectedRecipeId.clear();
                            selectedRecipeImageURL.clear();
                            selectedRecipeName.clear();
                            ingredientKey.clear();
                            ingredientList.clear();
                        }
                    }
                }
            });
            fm.beginTransaction().add(R.id.exp_container, basketFragment).commit();

            isBasketOpen = true;
        }
    }

    public void exitExplore(View v) {
        Intent exploreIntent = new Intent(getBaseContext(), ExploreActivity.class);
        Bundle exploreProgress = new Bundle();

        exploreProgress.putBoolean("isLoaded", true);
        exploreProgress.putStringArrayList("recipe name list", selectedRecipeName);
        exploreProgress.putStringArrayList("recipe ID list", selectedRecipeId);
        exploreProgress.putStringArrayList("recipe image list", selectedRecipeImageURL);
        exploreProgress.putStringArrayList("recipe ingredient list", ingredientList);
        exploreProgress.putIntegerArrayList("recipe key list", ingredientKey);
        exploreProgress.putInt("recipe progress count", recipeProgressCount);
        exploreProgress.putBoolean("reload request", false);

        exploreIntent.putExtra("progress data", exploreProgress);
        setResult(2, exploreIntent);
        requestQueue.stop();
        finish();
    }


    public void basketFunctionKey(View v) {
        Bundle data = new Bundle();
        int idx = v.getId();
        selectedRecipeId.remove(idx);
        selectedRecipeName.remove(idx);
        selectedRecipeImageURL.remove(idx);
        int key = ingredientKey.remove(idx);

        int count = 0;

        for (int i = 0; i < idx; i++) {
            count += ingredientKey.get(i);
        }

        for (int i = 0; i < key; i++) {
            ingredientList.remove(count);
        }

        data.putStringArrayList("recipe ID list", selectedRecipeId);
        data.putStringArrayList("recipe name list", selectedRecipeName);
        data.putIntegerArrayList("ingredients key", ingredientKey);
        data.putStringArrayList("ingredients list", ingredientList);
        data.putStringArrayList("imageURL", selectedRecipeImageURL);

        FragmentTransaction ft = fm.beginTransaction().remove(basketFragment);

        basketFragment = new fragment_basket();
        basketFragment.setArguments(data);
        fm.setFragmentResultListener("isClear", basketFragment, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (requestKey == "isClear") {
                    if (result.getBoolean("done")) {
                        selectedRecipeId.clear();
                        selectedRecipeImageURL.clear();
                        selectedRecipeName.clear();
                        ingredientKey.clear();
                        ingredientList.clear();
                    }
                }
            }
        });
        ft.add(R.id.exp_container, basketFragment).commit();
    }

    public void saveInBasket(View v) {
        try {
            JSONObject recipeObj = recipeList.getJSONObject(recipeIdList.getString(0));

            selectedRecipeId.add(recipeIdList.get(0).toString());
            selectedRecipeName.add(recipeObj.getString("name"));
            selectedRecipeImageURL.add(recipeObj.getString("image"));

            int keySize = recipeObj.getJSONArray("tags").length();
            ingredientKey.add(keySize);

            for (int i = 0; i < keySize; i++) {
                ingredientList.add(recipeObj.getJSONArray("tags").getString(i));
            }

            recipeProgressCount++;

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
                data.putString("author", recipeObj.getString("author"));
                data.putStringArrayList("ingredients", ingredientList);
                data.putStringArrayList("steps", stepsList);
                data.putString("imageURL", recipeObj.getString("image"));

                newRecipeFrag.setArguments(data);

                fm.beginTransaction().replace(R.id.exp_container, newRecipeFrag).commit();
            } else {
                fm.beginTransaction().replace(R.id.exp_container, new fragment_explore_1()).commit();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
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
                data.putString("author", recipeObj.getString("author"));
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
