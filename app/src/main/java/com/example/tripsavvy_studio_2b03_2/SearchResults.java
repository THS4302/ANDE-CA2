package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchResults extends AppCompatActivity {
    private LinearLayout scrollViewContent;
    private List<Place> originalSearchResults;
    private LocationTracker locationTracker;

    private static final int FILTER_OPTIONS_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);
        initialize();
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);
        double userLat = intent.getDoubleExtra("userLat", 0.0);
        double userLng = intent.getDoubleExtra("userLng", 0.0);
        Log.d("userid","userid:"+userId);

        String searchQuery = intent.getStringExtra("searchQuery");
        TextView searchedTerm = findViewById(R.id.searchedTerm);
        searchedTerm.setText("Searched Results: "+searchQuery);




        originalSearchResults = (List<Place>) intent.getSerializableExtra("searchResults");
        List<Place> searchResults = (List<Place>) intent.getSerializableExtra("searchResults");
        scrollViewContent = findViewById(R.id.scrollViewContent);
        populateScrollView(searchResults);
        ImageButton filterButton = findViewById(R.id.filterButton);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(0);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intenth = new Intent(SearchResults.this, Home.class);
                        intenth.putExtra("userId", userId);
                        startActivity(intenth);
                        return true;

                    case R.id.action_favourites:
                        Intent intentf = new Intent(SearchResults.this, Favorites.class);
                        intentf.putExtra("userId", userId);
                        startActivity(intentf);
                        return true;

                    case R.id.action_profile:
                        Intent intentp = new Intent(SearchResults.this, Profile.class);
                        intentp.putExtra("userId", userId);
                        startActivity(intentp);
                        return true;

                    case R.id.action_store:
                        startActivity(new Intent(SearchResults.this, Store.class));
                        return true;

                    default:
                        return false;
                }

            }

        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the FilterOptions activity for result
                Intent intent = new Intent(SearchResults.this, FilterOptions.class);
                startActivityForResult(intent, FILTER_OPTIONS_REQUEST_CODE);
            }
        });

        bottomNavigationView.setSelectedItemId(0);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("searchQuery");
        DatabaseHandler dbHandler = new DatabaseHandler(this);



        if (requestCode == FILTER_OPTIONS_REQUEST_CODE && resultCode == RESULT_OK) {

            // Retrieve selected filter options from the result intent
            ArrayList<String> selectedCategories = data.getStringArrayListExtra("selectedCategories");

            TextView filterCat = findViewById(R.id.filterCats);
            String joinedCategories = TextUtils.join(", ", selectedCategories);
            filterCat.setText("Filters: " + joinedCategories);
            Log.d("SearchPlaces", "Selection: " + selectedCategories.toString());
            Log.d("SearchPlaces", "Query: " + searchQuery);
            // Apply the filter options to your search results
            List<Place> filteredResults = dbHandler.searchPlaces(searchQuery,selectedCategories);

            // Update your UI with the filtered results
            populateScrollView(filteredResults);
        }
    }

    private void initialize() {
        // Create the LocationTracker instance
        locationTracker = new LocationTracker(this);
    }
    private void populateScrollView(List<Place> places) {
        scrollViewContent.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);
        Collections.sort(places, new Comparator<Place>() {
            @Override
            public int compare(Place place1, Place place2) {
                double distance1 = locationTracker.calculateDistance(locationTracker.getLatitude(), locationTracker.getLongitude(), place1.getLatitude(), place1.getLongitude());
                double distance2 = locationTracker.calculateDistance(locationTracker.getLatitude(), locationTracker.getLongitude(), place2.getLatitude(), place2.getLongitude());

                // Compare distances
                return Double.compare(distance1, distance2);
            }
        });

        for (Place place : places) {
            // Inflate the layout for each favorited place
            View placeView = inflater.inflate(R.layout.place_item, scrollViewContent, false);

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
                    Intent intentplacedetails=new Intent(SearchResults.this,PlaceDetails.class);
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
                    Intent intent = getIntent();
                    int userId = intent.getIntExtra("userId", -1);
                    // Toggle the favorite state when the button is clicked
                    toggleFavorite(userId,place.getPlaceId(), favoriteButton);
                }
            });

            // Use Picasso to load the image from the URL
            Picasso.get().load(place.getImageUrl()).into(imageView);

            Intent intentj = getIntent();

            double userLat = intentj.getDoubleExtra("userLat", 0.0);
            double userLng = intentj.getDoubleExtra("userLng", 0.0);
            double placeLat = place.getLatitude();
            double placeLng = place.getLongitude();
            double distance = locationTracker.calculateDistance(userLat,userLng, placeLat, placeLng);
            Log.d("userlocation:", "userlocation:" + locationTracker.getLatitude() + "...long.." + locationTracker.getLongitude());
            Picasso.get().load(place.getImageUrl()).into(imageView);
            textView.setText(place.getName() + "\n📍" + distance + " km\nDetails");

            // Add the inflated layout to the ScrollView
            scrollViewContent.addView(placeView);
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
