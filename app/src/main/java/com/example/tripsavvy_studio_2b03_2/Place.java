package com.example.tripsavvy_studio_2b03_2;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Place {
    public String placeId; // You can manually set this or generate a unique ID
    public String name;

    public double latitude;
    public double longitude;
    public String imageUrl;

    public Place() {
        // Default constructor required for Firebase
    }

    public Place(String placeId, String name, double latitude, double longitude, String imageUrl) {
        this.placeId = placeId;
        this.name = name;

        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        // Add other fields as needed
    }
}
