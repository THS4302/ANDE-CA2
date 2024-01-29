package com.example.tripsavvy_studio_2b03_2;


public class TravelPackage {
    private int packageid;
    private String grade; //for Package A,B,C
    private int placeid;
    private String details;
    private float price;

    private String packageimg;


    public TravelPackage() {
        // Default constructor required for SQLite
    }

    public TravelPackage(int packageid,String grade,int placeid,String details,float price,String packageimg) {
      this.packageid=packageid;
      this.grade=grade;
      this.placeid=placeid;
      this.details=details;
      this.price=price;
      this.packageimg=packageimg;
        // Add other fields as needed
    }

    // Add getter methods

    public float getPrice() {
        return price;
    }

    public int getPackageid() {
        return packageid;
    }

    public int getPlaceid() {
        return placeid;
    }

    public String getDetails() {
        return details;
    }

    public String getGrade() {
        return grade;
    }

    public String getPackageimg() {
        return packageimg;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setPackageid(int packageid) {
        this.packageid = packageid;
    }

    public void setPlaceid(int placeid) {
        this.placeid = placeid;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPackageimg(String packageimg) {
        this.packageimg = packageimg;
    }
}

