package com.example.fudbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fudbook.ui.bookshelf.fragment_bookshelf;
import com.example.fudbook.ui.explore.fragment_basket;
import com.example.fudbook.ui.explore.fragment_basket_item;
import com.example.fudbook.ui.explore.fragment_explore_1;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExploreActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "ExploreActivity";

    // fragment for basket
    private Fragment FragmentBasket;
    private FragmentManager fm;

    // checks if basket is open
    private boolean isBasketOpen;

    // filter
    private ArrayList<String> selectedIncludeFilter, selectedExcludeFilter;

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
                        Fragment recipeFrag = new fragment_explore_1();
                        Bundle data = new Bundle();

                        try {
                            if (recipeIdList.length() > 0) {
                                JSONObject recipeObj = recipeList.getJSONObject(recipeIdList.get(0).toString());

                                // parse ingredients
                                JSONArray ingredientArr = recipeObj.getJSONArray("ingredients");
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
                                recipeFrag.setArguments(data);
                            }
                        } catch (Exception e) { }
                        fm.beginTransaction().add(R.id.exp_container, recipeFrag).commit();
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
        if (FragmentBasket != null)
            fm.beginTransaction().remove(FragmentBasket).commit();

        isBasketOpen = false;
    }

    public void enterBasket(View v) {
        if (!isBasketOpen) {
            FragmentBasket = new fragment_basket();
            fm.beginTransaction().add(R.id.exp_container, FragmentBasket).commit();
            isBasketOpen = true;
        }
    }

    public void exitExplore(View v) {
       finish();
    }
}
