package com.example.fudbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private Button create_button, explore_button, bookshelf_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_button = (Button) findViewById(R.id.createbutton);
        explore_button = (Button) findViewById(R.id.explorebutton);
        bookshelf_button = (Button) findViewById(R.id.bookshelfbutton);

        create_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToCreate();
            }
        });

        explore_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToExplore();
            }
        });

        bookshelf_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToBookshelf();
            }
        });
    }

    public void goToCreate(){
        Intent intent = new Intent(this, create.class);
        startActivity(intent);
    }

    public void goToExplore(){
        Intent intent = new Intent(this, explore.class);
        startActivity(intent);
    }

    public void goToBookshelf(){
        Intent intent = new Intent(this, bookshelf.class);
        startActivity(intent);
    }
}
