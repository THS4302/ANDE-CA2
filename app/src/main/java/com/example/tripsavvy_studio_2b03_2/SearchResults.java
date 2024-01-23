package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(SearchResults.this, Home.class));
                        return true;

                    case R.id.action_favourites:
                        startActivity(new Intent(SearchResults.this, Favorites.class));
                        return true;

                    case R.id.action_store:
                        startActivity(new Intent(SearchResults.this, Store.class));
                        return true;

                    case R.id.action_profile:
                        startActivity(new Intent(SearchResults.this, Profile.class));
                        return true;



                    default:
                        return false;
                }
            }
        });



    }
}
