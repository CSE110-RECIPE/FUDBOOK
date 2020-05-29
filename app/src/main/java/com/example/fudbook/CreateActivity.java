package com.example.fudbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fudbook.objects.Recipe;
import com.example.fudbook.ui.FragmentAdapter;
import com.example.fudbook.ui.create.fragment_create_1;
import com.example.fudbook.ui.create.fragment_create_2;
import com.example.fudbook.ui.create.fragment_create_3;
import com.example.fudbook.ui.dashboard.fragment_dashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateActivity extends AppCompatActivity {

    // name of activity
    private static final String TAG = "CreateActivity";

    // create an adapter
    private FragmentAdapter fragmentAdapter;
    private ViewPager2 viewPager;

    // overlaying buttons
    Button next_button;
    Button back_button; // need to implement

    Bundle bundle;
    // For memory sharing
    SharedPreferences sharedPreferences;
    public static final String CREATE_PREFERENCES = "Create_Prefs";

    private Fragment create1, create2, create3;

    // Firebase access
    FirebaseAuth auth;

    // Firestore instances
    FirebaseStorage firestore;

    // Volley API request field
    private RequestQueue requestQueue;
    private static final String API_URL = "http://10.0.2.2:3000";
    private String uid;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // log activity
        Log.d(TAG, "onCreate: Started\n");

        requestQueue = Volley.newRequestQueue(this);

        // for memory sharing
        sharedPreferences = getSharedPreferences(CREATE_PREFERENCES, Context.MODE_PRIVATE);

        Log.d(TAG, "onCreate: Setting up viewPager \n");

        viewPager = findViewById(R.id.create_container);
        setupViewPager(viewPager);

        // button set up
        next_button = findViewById(R.id.next_btn);
        back_button = findViewById(R.id.back_btn);

        // listener set up
        next_button.setOnClickListener(next_listener);
        back_button.setOnClickListener(back_listener);

        // Firebase access
        auth = FirebaseAuth.getInstance();

        // Firestore access
        firestore = FirebaseStorage.getInstance();

        uid = auth.getCurrentUser().getUid();
        username = auth.getCurrentUser().getDisplayName();

        System.out.println(uid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // clear memory after destroyed
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        requestQueue.stop();
    }

    @Override
    public void onBackPressed() {
        int current = viewPager.getCurrentItem();
        if(current != 0) {
            viewPager.setCurrentItem(current-1, true);
        } else {
            super.onBackPressed(); // This will pop the Activity from the stack.
        }
    }

    private Button.OnClickListener back_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up last create page
                    int current = viewPager.getCurrentItem();
                    if (current != 0) {
                        setViewPager(current - 1);
                        Button btn = (Button) findViewById(R.id.next_btn);
                        btn.setText("Next");
                    } else {
                        finish();
                    }
                }
            };

    private Button.OnClickListener next_listener =
            new ImageButton.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // bring up next create page
                    int current = viewPager.getCurrentItem();
                    if(current != 3) {
                        setViewPager(current + 1);

                        if (current + 1 == 2) {
                            Button btn = (Button) findViewById(R.id.next_btn);
                            btn.setText("Post");
                        }

                        if (current + 1 == 3) {
                            // POST action
                            String[] ingredientArr = (String []) bundle.get("ingredients");
                            String recipeName = (String) bundle.get("recipe name");
                            String[] steps = (String[]) bundle.get("instructions");

                            if (ingredientArr != null && steps != null && ingredientArr.length > 0
                                    && !recipeName.equals("")
                                    && steps.length > 0)
                                uploadRecipeImage(ingredientArr, recipeName, steps);
                            else {
                                Toast.makeText(getApplicationContext(), "Form not completed :(",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        fragmentAdapter.notifyItemChanged(2);
                    }

                    for (String key: bundle.keySet())
                    {
                        Log.d ("myApplication", key + " is a key in the bundle");
                    }
                }
            };

    private void setupViewPager(ViewPager2 viewPager) {
        Log.d(TAG, "setupViewPager: adding create fragments");

        // adapter to send to viewpager
        bundle = new Bundle();
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), bundle, getLifecycle());

        create1 = new fragment_create_1();
        create2 = new fragment_create_2();
        create3 = new fragment_create_3();

        fragmentAdapter.addFragment(create1, "Create Recipe Step 1"); // 0
        fragmentAdapter.addFragment(create2, "Create Recipe Step 2"); // 1
        fragmentAdapter.addFragment(create3, "Create Recipe Step 3"); // 2

        fragmentAdapter.notifyDataSetChanged();

        viewPager.setAdapter(fragmentAdapter);
        setViewPager(0);
    }

    // sets viewpager to certain fragment
    public void setViewPager(int fragmentNumber){
        Log.d(TAG, "setViewPager");
        viewPager.setCurrentItem(fragmentNumber);
    }

    private void uploadRecipeImage(final String[] ingredientArr, final String recipeName, final String[] steps) {
        final String name = recipeName;
        ImageView imageView = findViewById(R.id.recipe_photo);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = firestore.getReference().child(name+uid).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                // Handle unsuccessful uploads
                System.out.println(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                System.out.println("Upload success");

                firestore.getReference().child(name+uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        postRecipe(ingredientArr, recipeName, steps, uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        // Handle any errors
                    }
                });
            }
        });
    }

    private void postRecipe(String[] ingredientArr, String recipeName, String[] steps, String uri) {

        JSONObject recipeJSON = new JSONObject();
        JSONArray ingredientJsonArr = new JSONArray();
        JSONArray stepsJsonArr = new JSONArray();

        try {
            recipeJSON.accumulate("uid", uid);
            recipeJSON.accumulate("name", recipeName);
            for (String n : ingredientArr) {
                ingredientJsonArr.put(n);
            }
            recipeJSON.accumulate("ingredients", ingredientJsonArr);
            stepsJsonArr.put(steps[0]);
            recipeJSON.accumulate("steps", stepsJsonArr);
            recipeJSON.accumulate("author", username);
            recipeJSON.accumulate("editor", "");
            recipeJSON.accumulate("image", uri);
        } catch (Exception e) { }

        final String requestBody = recipeJSON.toString();

        StringRequest sr = new StringRequest(Request.Method.POST, API_URL + "/recipe",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (Exception e) {
                    return null;
                }
            }
        };

        requestQueue.add(sr);
    }
}
