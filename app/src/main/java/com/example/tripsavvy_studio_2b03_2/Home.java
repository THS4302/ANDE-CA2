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
import android.widget.SearchView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

public class Home extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private LinearLayout scrollViewContent;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(this);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);
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
                        Intent intentf = new Intent(Home.this, Favorites.class);
                        intentf.putExtra("userId", userId);
                        startActivity(intentf);
                        // Navigate to the FavoritesActivity when Favorites item is selected
                        //startActivity(new Intent(Home.this, Favorites.class));
                        return true;


                    case R.id.action_profile:
                        Intent intentp = new Intent(Home.this, Profile.class);
                        intentp.putExtra("userId", userId);
                        startActivity(intentp);

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
            TextView textView = placeView.findViewById(R.id.placeName);
            ImageButton favoriteButton = placeView.findViewById(R.id.buttonFavorite2);
            favoriteButton.setTag(place.getPlaceId());

            boolean isFavorite = isPlaceFavorite(place.getPlaceId());
            favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle the favorite state when the button is clicked
                    toggleFavorite(place.getPlaceId(), favoriteButton);
                }
            });

            // Use Picasso to load the image from the URL
            Picasso.get().load(place.getImageUrl()).into(imageView);// Replace with your logic for setting image resource
            textView.setText(place.getName() + "\n📍" + "distance" + " km\nDetails");





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
        // Get the search results based on the query
        DatabaseHandler db = new DatabaseHandler(this);
        List<Place> searchResults = db.searchPlaces(query,null);

        // Start SearchResultsActivity and pass the search results
        Intent intent = new Intent(Home.this, SearchResults.class);
        intent.putExtra("userId", userId);
        intent.putExtra("searchResults", (Serializable) searchResults); // Make sure Place implements Serializable
        startActivity(intent);
    }
    private boolean isPlaceFavorite(int placeId) {
        // Retrieve the current state of the favorite for the given placeId
        return getSharedPreferences("Favorites", MODE_PRIVATE)
                .getBoolean(getFavoriteKey(placeId), false);
    }

    private void toggleFavorite(int placeId, ImageButton favoriteButton) {
        // Toggle the favorite state
        boolean isFavorite = !isPlaceFavorite(placeId);

        // Update the button state
        favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);

        // Save the updated state to SharedPreferences
        getSharedPreferences("Favorites", MODE_PRIVATE)
                .edit()
                .putBoolean(getFavoriteKey(placeId), isFavorite)
                .apply();
    }

    private String getFavoriteKey(int placeId) {
        // Generate a unique key for storing the favorite state of a place
        return "favorite_" + placeId;
    }
}