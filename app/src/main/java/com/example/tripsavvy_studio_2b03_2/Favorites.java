package com.example.tripsavvy_studio_2b03_2;
//Thet Htar San p2235077
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends AppCompatActivity {
    private LinearLayout favViewContent;
    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);
        double userLat = intent.getDoubleExtra("userLat", 0.0);
        double userLng = intent.getDoubleExtra("userLng", 0.0);
        favViewContent = findViewById(R.id.favViewContent);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intenth = new Intent(Favorites.this, Home.class);
                        intenth.putExtra("userId", userId);

                        startActivity(intenth);

                        return true;

                    case R.id.action_favourites:
                        // Navigate to the FavoritesActivity when Favorites item is selected
                        // startActivity(new Intent(MainActivity.this, Favorites.class));
                        List<Place> favoritedPlaces = getFavoritedPlaces();
                        populateFavorites(favoritedPlaces);
                        return true;

                    case R.id.action_profile:
                        Intent intentp = new Intent(Favorites.this, Profile.class);
                        intentp.putExtra("userId", userId);
                        intentp.putExtra("userLat", locationTracker.getLatitude());
                        intentp.putExtra("userLng", locationTracker.getLongitude());
                        startActivity(intentp);

                        return true;

                    case R.id.action_store:
                        Intent intents=new Intent(Favorites.this, Store.class);
                        intents.putExtra("userId", userId);
                        intents.putExtra("userLat", locationTracker.getLatitude());
                        intents.putExtra("userLng", locationTracker.getLongitude());
                        startActivity(intents);

                        return true;



                    default:
                        return false;
                }
            }
        });
        initialize();

        // Set the default selected item programmatically
        bottomNavigationView.setSelectedItemId(R.id.action_favourites);
    }

    private List<Place> getFavoritedPlaces() {
        List<Place> favoritedPlaces = new ArrayList<>();
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);
        DatabaseHandler db = new DatabaseHandler(this);

        // Iterate over all places to find the favorited ones
        for (Place place : db.getAllPlaces()) {
            if (isPlaceFavorite(userId,place.getPlaceId())) {
                favoritedPlaces.add(place);
            }
        }
        return favoritedPlaces;
    }
    private void initialize() {
        // Create the LocationTracker instance
        locationTracker = new LocationTracker(this);
    }

    private void populateFavorites(List<Place> favoritedPlaces) {
        LayoutInflater inflater = LayoutInflater.from(this);
        Intent intentid = getIntent();
        int userId = intentid.getIntExtra("userId", -1);
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

            boolean isFavorite = isPlaceFavorite(userId,place.getPlaceId());
            favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);
            placeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle the click event for the place item
                    Intent intent = getIntent();
                    int userId = intent.getIntExtra("userId", -1);
                    Intent intentplacedetails=new Intent(Favorites.this,PlaceDetails.class);
                    intentplacedetails.putExtra("userId", userId);
                    intentplacedetails.putExtra("placeId", place.getPlaceId());
                    Log.d("Placeid","placeid:"+place.getPlaceId());
                    intentplacedetails.putExtra("userLat", locationTracker.getLatitude());
                    intentplacedetails.putExtra("userLng", locationTracker.getLongitude());


                    startActivity(intentplacedetails);

                    //Toast.makeText(Home.this, "Place Item Clicked!", Toast.LENGTH_SHORT).show();

                }
            });
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle the favorite state when the button is clicked
                    toggleFavorite(userId,place.getPlaceId(), favoriteButton);
                }
            });
            // Use Picasso to load the image from the URL
            Picasso.get().load(place.getImageUrl()).into(imageView);

            Intent intent = getIntent();

            double userLat = intent.getDoubleExtra("userLat", 0.0);
            double userLng = intent.getDoubleExtra("userLng", 0.0);
            double placeLat = place.getLatitude();
            double placeLng = place.getLongitude();
            double distance = locationTracker.calculateDistance(userLat,userLng, placeLat, placeLng);
            Log.d("userlocation:", "userlocation:" + locationTracker.getLatitude() + "...long.." + locationTracker.getLongitude());
            Picasso.get().load(place.getImageUrl()).into(imageView);
            textView.setText(place.getName() + "\n📍" + distance + " km\nDetails");

            // Add the inflated layout to the favorites container
            favViewContent.addView(placeView);
        }
    }
    private String getFavoriteKey(int userId, int placeId) {
        // Generate a unique key for storing the favorite state of a place for a specific user
        return "favorite_" + userId + "_" + placeId;
    }

    private boolean isPlaceFavorite(int userId, int placeId) {
        // Retrieve the current state of the favorite for the given placeId and userId from SharedPreferences
        return getSharedPreferences("Favorites", MODE_PRIVATE)
                .getBoolean(getFavoriteKey(userId, placeId), false);
    }

    private void toggleFavorite(int userId, int placeId, ImageButton favoriteButton) {
        // Toggle the favorite state
        boolean isFavorite = !isPlaceFavorite(userId, placeId);

        // Update the button state
        favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);

        // Save the updated state to SharedPreferences
        getSharedPreferences("Favorites", MODE_PRIVATE)
                .edit()
                .putBoolean(getFavoriteKey(userId, placeId), isFavorite)
                .apply();

        // Show a Toast message based on the current favorite state
        if (isFavorite) {
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }



}
