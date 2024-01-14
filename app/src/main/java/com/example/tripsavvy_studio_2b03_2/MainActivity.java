package com.example.tripsavvy_studio_2b03_2;


//Thet Htar San 2235077

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler databaseHandler = new DatabaseHandler();


        // Call the method to add a place
        databaseHandler.addPlace("Paris", 48.864716, 2.349014, "https://images.adsttc.com/media/images/5d44/14fa/284d/d1fd/3a00/003d/large_jpg/eiffel-tower-in-paris-151-medium.jpg?1564742900");

        String placeId = "custom_" + System.currentTimeMillis();
        //Place place = new Place(placeId,"Paris",48.864716,2.349014,"https://images.adsttc.com/media/images/5d44/14fa/284d/d1fd/3a00/003d/large_jpg/eiffel-tower-in-paris-151-medium.jpg?1564742900");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        // Handle the home item if needed
                        return true;

                    case R.id.action_favourites:
                        // Navigate to the FavoritesActivity when Favorites item is selected
                        startActivity(new Intent(MainActivity.this, Favorites.class));
                        return true;

                    case R.id.action_profile:
                        startActivity(new Intent(MainActivity.this, Profile.class));
                        return true;

                    case R.id.action_store:
                        startActivity(new Intent(MainActivity.this, Store.class));
                        return true;


                    default:
                        return false;
                }
            }
        });

        // Set the default selected item programmatically
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}