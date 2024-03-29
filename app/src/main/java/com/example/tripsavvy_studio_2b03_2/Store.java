package com.example.tripsavvy_studio_2b03_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Store extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private LinearLayout scrollViewContent;
    private int userId;
    private LocationTracker locationTracker;
    private static final int REQUEST_CODE_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(this);
        setContentView(R.layout.activity_shop);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        double userLat = intent.getDoubleExtra("userLat", 0.0);
        double userLng = intent.getDoubleExtra("userLng", 0.0);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        // Initialize scrollViewContent
        scrollViewContent = findViewById(R.id.scrollViewShop);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        View rootView = findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            private int previousHeight = rootView.getHeight();

            @Override
            public boolean onPreDraw() {
                int newHeight = rootView.getHeight();
                if (newHeight < previousHeight) {
                    // Keyboard is shown, hide the Bottom Navigation Bar
                    bottomNavigationView.setVisibility(View.GONE);
                } else if (newHeight > previousHeight) {
                    // Keyboard is hidden, show the Bottom Navigation Bar
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
                previousHeight = newHeight;
                return true;
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION);
        } else {
            // Permissions are already granted
            initialize();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_store:
                        // Handle the home item if needed
                        return true;

                    case R.id.action_favourites:
                        Intent intentf = new Intent(Store.this, Favorites.class);
                        intentf.putExtra("userId", userId);
                        intentf.putExtra("userLat", locationTracker.getLatitude());
                        intentf.putExtra("userLng", locationTracker.getLongitude());
                        startActivity(intentf);
                        return true;

                    case R.id.action_profile:
                        Intent intentp = new Intent(Store.this, Profile.class);
                        intentp.putExtra("userId", userId);
                        intentp.putExtra("userLat", locationTracker.getLatitude());
                        intentp.putExtra("userLng", locationTracker.getLongitude());
                        startActivity(intentp);
                        return true;

                    case R.id.action_home:
                        Intent intenth = new Intent(Store.this, Home.class);
                        intenth.putExtra("userId", userId);
                        startActivity(intenth);
                        return true;

                    default:
                        return false;
                }
            }
        });

        // Set the default selected item programmatically
        bottomNavigationView.setSelectedItemId(R.id.action_store);

        populateScrollView(db.getAllPlaces());
    }

    private void initialize() {
        // Create the LocationTracker instance
        locationTracker = new LocationTracker(this);
    }

    private void populateScrollView(List<Place> places) {
        LayoutInflater inflater = LayoutInflater.from(this);

        // Sort places based on distance in ascending order
        Collections.sort(places, new Comparator<Place>() {
            @Override
            public int compare(Place place1, Place place2) {
                double distance1 = locationTracker.calculateDistance(locationTracker.getLatitude(), locationTracker.getLongitude(), place1.getLatitude(), place1.getLongitude());
                double distance2 = locationTracker.calculateDistance(locationTracker.getLatitude(), locationTracker.getLongitude(), place2.getLatitude(), place2.getLongitude());

                // Compare distances
                return Double.compare(distance1, distance2);
            }
        });

        // Clear existing views in the scrollViewContent
        scrollViewContent.removeAllViews();

        // Populate the scroll view with sorted places
        for (Place place : places) {
            View placeView = inflater.inflate(R.layout.shop_item, scrollViewContent, false);

            ImageView imageView = placeView.findViewById(R.id.imageView);
            TextView textView = placeView.findViewById(R.id.placeName);
            ImageButton favoriteButton = placeView.findViewById(R.id.buttonFavorite2);
            favoriteButton.setTag(place.getPlaceId());

            boolean isFavorite = isPlaceFavorite(place.getPlaceId());
            favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);

            placeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle the click event for the place item

                    Intent intentpackages=new Intent(Store.this,ViewPackages.class);
                    intentpackages.putExtra("userId", userId);
                    intentpackages.putExtra("placeId", place.getPlaceId());
                    Log.d("Placeid","placeid:"+place.getPlaceId());
                    intentpackages.putExtra("userLat", locationTracker.getLatitude());
                    intentpackages.putExtra("userLng", locationTracker.getLongitude());


                    startActivity(intentpackages);

                    //Toast.makeText(Home.this, "Place Item Clicked!", Toast.LENGTH_SHORT).show();

                }
            });

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavorite(place.getPlaceId(), favoriteButton);
                }
            });

            // Calculate distance using LocationTracker
            double placeLat = place.getLatitude();
            double placeLng = place.getLongitude();
            double distance = locationTracker.calculateDistance(locationTracker.getLatitude(), locationTracker.getLongitude(), placeLat, placeLng);
            Log.d("userlocation:", "userlocation:" + locationTracker.getLatitude() + "...long.." + locationTracker.getLongitude());
            Picasso.get().load(place.getImageUrl()).into(imageView);
            textView.setText(place.getName() + "\n📍" + distance + " km\nDetails");

            scrollViewContent.addView(placeView);
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        // Handle search query submissio
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
        List<Place> searchResults = db.searchPlaces(query, null);

        // Start SearchResultsActivity and pass the search results and query
        Intent intent = new Intent(Store.this, SearchResults.class);
        intent.putExtra("userId", userId);
        intent.putExtra("searchResults", (Serializable) searchResults); // Make sure Place implements Serializable
        intent.putExtra("searchQuery", query);
        intent.putExtra("userLat", locationTracker.getLatitude());
        intent.putExtra("userLng", locationTracker.getLongitude());

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

        // Show a Toast message based on the current favorite state
        if (isFavorite) {
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFavoriteKey(int placeId) {
        // Generate a unique key for storing the favorite state of a place
        return "favorite_" + placeId;
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with your code
                initialize();
            } else {
                Toast.makeText(this, "Location permission denied. Some features may be disabled.", Toast.LENGTH_SHORT).show();
                initialize();
            }
        }
    }

}
