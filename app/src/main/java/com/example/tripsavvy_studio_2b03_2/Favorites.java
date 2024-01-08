package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Favorites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(Favorites.this, MainActivity.class));
                        return true;

                    case R.id.action_favourites:
                        // Navigate to the FavoritesActivity when Favorites item is selected
                        // startActivity(new Intent(MainActivity.this, Favorites.class));
                        return true;

                    // Add cases for other bottom navigation items if needed

                    default:
                        return false;
                }
            }
        });

        // Set the default selected item programmatically
        bottomNavigationView.setSelectedItemId(R.id.action_favourites);
    }
}
