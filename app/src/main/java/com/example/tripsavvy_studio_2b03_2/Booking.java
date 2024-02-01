package com.example.tripsavvy_studio_2b03_2;

public class Booking {

    private int bookingId;
    private int userId;
    private int placeId;
    private String packageGrade;
    private float price;
    private String date;
    private int numberOfTickets;

    public Booking() {

    }

    // Constructor with parameters
    public Booking(int userId, int placeId, String packageGrade, float price, String date, int numberOfTickets) {
        this.userId = userId;
        this.placeId = placeId;
        this.packageGrade = packageGrade;
        this.price = price;
        this.date = date;
        this.numberOfTickets = numberOfTickets;
    }

    // Getter and Setter methods for bookingId
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    // Getter and Setter methods for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and Setter methods for placeId
    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    // Getter and Setter methods for packageGrade
    public String getPackageGrade() {
        return packageGrade;
    }

    public void setPackageGrade(String packageGrade) {
        this.packageGrade = packageGrade;
    }

    // Getter and Setter methods for price
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    // Getter and Setter methods for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter and Setter methods for numberOfTickets
    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

}
