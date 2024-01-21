package com.example.tripsavvy_studio_2b03_2;


public class User {
    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String profileUrl;

    public User() {
        // Default constructor required for SQLite
    }

    public User(int userid, String fname, String lname, String email, String password, String profileUrl) {
      this.userId=userid;
      this.firstname=fname;
      this.lastname=lname;
      this.email=email;
      this.password=password;
      this.profileUrl=profileUrl;
        // Add other fields as needed
    }

    // Add getter methods
    public int getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfileUrl() {
        return profileUrl;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }





    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

}

