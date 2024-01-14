package com.example.tripsavvy_studio_2b03_2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHandler {

    private DatabaseReference databaseReference;

    public DatabaseHandler() {
        // Initialize the reference to your database
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child("places");
    }

    public void addPlace(String name, double latitude, double longitude, String imageUrl) {
        // Create a Place instance
        String placeId = "custom_" + System.currentTimeMillis();
        Place place = new Place(placeId, name, latitude, longitude, imageUrl);

        // Add the place to the database
        databaseReference.push().setValue(place);
    }

    // Add other methods for database operations as needed
}
