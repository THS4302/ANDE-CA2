package com.example.tripsavvy_studio_2b03_2;


//Thet Htar San 2235077
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Home extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private LinearLayout scrollViewContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(this);
        setContentView(R.layout.activity_home);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        // Initialize scrollViewContent
        scrollViewContent = findViewById(R.id.scrollViewContent);

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
                        startActivity(new Intent(Home.this, Favorites.class));
                        return true;

                    case R.id.action_profile:
                        startActivity(new Intent(Home.this, Profile.class));
                        return true;

                    case R.id.action_store:
                        startActivity(new Intent(Home.this, Store.class));
                        return true;

                    default:
                        return false;
                }
            }
        });

        // Set the default selected item programmatically
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        populateScrollView(db.getAllPlaces());
    }


    private void populateScrollView(List<Place> places) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Place place : places) {
            // Inflate the layout for each place
            View placeView = inflater.inflate(R.layout.place_item, scrollViewContent, false);

            // Find views in the inflated layout
            ImageView imageView = placeView.findViewById(R.id.imageView);
            TextView textView = placeView.findViewById(R.id.searchedTerm);
            ImageButton favoriteButton = placeView.findViewById(R.id.buttonFavorite);

            // Use Picasso to load the image from the URL
            Picasso.get().load(place.getImageUrl()).into(imageView);// Replace with your logic for setting image resource
            textView.setText(place.getName() + "\n📍" + "distance" + " km\nDetails");



            // Set a unique tag for the favorite button to identify the corresponding place
            //favoriteButton.setTag(place.getPlaceId());
            //favoriteButton.setOnClickListener(this);

            // Add the inflated layout to the ScrollView
            scrollViewContent.addView(placeView);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Handle search query submission (e.g., start SearchResultsActivity)
        startSearchResultsActivity(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Handle search query changes if needed
        return true;
    }

    private void startSearchResultsActivity(String query) {
        // Start SearchResultsActivity and pass the search query
        Intent intent = new Intent(Home.this, SearchResults.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }
}