package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends AppCompatActivity {
    private LinearLayout favViewContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        favViewContent = findViewById(R.id.favViewContent);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(Favorites.this, Home.class));
                        return true;

                    case R.id.action_favourites:
                        // Navigate to the FavoritesActivity when Favorites item is selected
                        // startActivity(new Intent(MainActivity.this, Favorites.class));
                        List<Place> favoritedPlaces = getFavoritedPlaces();
                        populateFavorites(favoritedPlaces);
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

    private List<Place> getFavoritedPlaces() {
        List<Place> favoritedPlaces = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(this);

        // Iterate over all places to find the favorited ones
        for (Place place : db.getAllPlaces()) {
            if (isPlaceFavorite(place.getPlaceId())) {
                favoritedPlaces.add(place);
            }
        }
        return favoritedPlaces;
    }

    private void populateFavorites(List<Place> favoritedPlaces) {
        LayoutInflater inflater = LayoutInflater.from(this);

        // Clear existing views from the container
        favViewContent.removeAllViews();

        for (Place place : favoritedPlaces) {
            // Inflate the layout for each favorited place
            View placeView = inflater.inflate(R.layout.place_item, favViewContent, false);

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
            Picasso.get().load(place.getImageUrl()).into(imageView);

            // Set the place name
            textView.setText(place.getName() + "\n📍" + "distance" + " km\nDetails");

            // Add the inflated layout to the favorites container
            favViewContent.addView(placeView);
        }
    }
    private String getFavoriteKey(int placeId) {
        // Generate a unique key for storing the favorite state of a place
        return "favorite_" + placeId;
    }
    private boolean isPlaceFavorite(int placeId) {
        // Retrieve the current state of the favorite for the given placeId from SharedPreferences
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

}
