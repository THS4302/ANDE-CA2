package com.example.tripsavvy_studio_2b03_2;
//Thet Htar San p2235077

import java.io.Serializable;

public class Place implements Serializable {
    private int placeId;
    private String name;
    private double latitude;
    private double longitude;
    private String imageUrl;
    private String placecat;

    private String country;

    private String description;

    private float rating;

    public Place() {
        // Default constructor required for SQLite
    }

    public Place(int placeId, String name, double latitude, double longitude, String imageUrl, String cat, String pcountry, String dsp, float rate ) {
        this.placeId = placeId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;

        this.imageUrl = imageUrl;
        this.placecat = cat;
        this.country = pcountry;

        this.description=dsp;
        this.rating=rate;

    }

    // Add getter methods
    public int getPlaceId() {
        return placeId;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlacecat() {
        return placecat;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }
    public float getRating() {
        return rating;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPlacecat(String placecat) {
        this.placecat = placecat;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

