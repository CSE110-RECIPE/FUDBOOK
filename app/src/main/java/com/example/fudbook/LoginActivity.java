package com.example.fudbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 3030;
    private Button login_button;
    private static final String API_URL = "http://10.0.2.2:3000";

    GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button = (Button) findViewById(R.id.loginbutton);
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signIn();
            }
        });

        // Google sign in options and setup
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
    }


    // function to transition ot dashboard
    public void goToDash(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = GoogleSignIn.getClient(this,gso).getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credit = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                final FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithCredential(credit).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        System.out.println("Completing task");

                        if (task.getResult().getAdditionalUserInfo().isNewUser()){
                            System.out.println("New User detected.");
                            System.out.println(task.getResult().getUser().getUid());
                            RequestQueue v = Volley.newRequestQueue(getApplicationContext());
                            JSONObject jo = new JSONObject();
                            try {
                                jo.accumulate("uid", task.getResult().getUser().getUid());
                            }catch(Exception e){}

                            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, API_URL + "/book/newUser",
                                    jo, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    goToDash();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error);
                                }
                            });
                            v.add(jor);
                        }else{
                            goToDash();
                        }
                    }
                });

            } catch (ApiException e) {
                System.out.println(e);
                Toast.makeText(getApplicationContext(), "Sign In Error", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
