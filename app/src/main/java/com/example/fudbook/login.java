package com.example.fudbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button = (Button) findViewById(R.id.loginbutton);
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToDash();
            }
        });
    }


    // function to transition ot dashboard
    public void goToDash(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
