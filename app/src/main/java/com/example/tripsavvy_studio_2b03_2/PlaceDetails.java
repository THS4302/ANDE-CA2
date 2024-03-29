package com.example.tripsavvy_studio_2b03_2;
//Thet Htar San p2235077
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceDetails extends AppCompatActivity {
    private int userId;
    private LocationTracker locationTracker;


    private int placeId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placedetails);
        initialize();

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        placeId = intent.getIntExtra("placeId", -1);

        DatabaseHandler db = new DatabaseHandler(this);
        Place place = db.getPlace(String.valueOf(placeId));

        ImageButton favoriteButton = findViewById(R.id.buttonFavorite2);
        favoriteButton.setTag(place.getPlaceId());

        // Check if the place is already a favorite
        boolean isFavorite = isPlaceFavorite(userId,placeId);
        favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the favorite state
                toggleFavorite(userId
                        ,place.getPlaceId(), favoriteButton);

                // Update the button state after toggling
                boolean isFavorite = isPlaceFavorite(userId,placeId);
                favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);
            }
        });


        if (place != null) {
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavorite(userId,place.getPlaceId(), favoriteButton);
                }
            });
            ImageView placeImage = findViewById(R.id.placeImage);
            Picasso.get().load(place.getImageUrl()).into(placeImage);

            TextView namedist = findViewById(R.id.placedNamedist);

            // Calculate distance using LocationTracker after initializing
            double placeLat = place.getLatitude();
            double placeLng = place.getLongitude();
            double distance = locationTracker.calculateDistance(locationTracker.getLatitude(), locationTracker.getLongitude(), placeLat, placeLng);

            namedist.setText(place.getName() + " , "+place.getCountry()+" ,📍" + distance+" km");
            Log.d("country","country"+place.getCountry());
            TextView placeDescription = findViewById(R.id.placeDescription);
            placeDescription.setText(place.getDescription());
            Log.d("descp","descp:"+place.getDescription());
        }

    BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intenth = new Intent(PlaceDetails.this, Home.class);
                        intenth.putExtra("userId", userId);

                        startActivity(intenth);
                        return true;

                    case R.id.action_favourites:
                        Intent intentf = new Intent(PlaceDetails.this, Favorites.class);
                        intentf.putExtra("userId", userId);
                        intentf.putExtra("userLat", locationTracker.getLatitude());
                        intentf.putExtra("userLng", locationTracker.getLongitude());
                        startActivity(intentf);
                        return true;

                    case R.id.action_profile:
                        Intent intentp = new Intent(PlaceDetails.this, Profile.class);
                        intentp.putExtra("userId", userId);
                        startActivity(intentp);
                        return true;

                    case R.id.action_store:
                        startActivity(new Intent(PlaceDetails.this, Store.class));
                        return true;

                    default:
                        return false;
                }
            }
        });
        bottomNavigationView.setSelectedItemId(0);
        // Set the default selected item programmatically

    }
    private void initialize() {
        // Create the LocationTracker instance
        locationTracker = new LocationTracker(this);
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
