package com.example.tripsavvy_studio_2b03_2;
//Thet Htar San 2235077
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 18;

    private static final String DATABASE_NAME = "tripSavvyUserDB";
    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "id";
    private static final String FIRST_NAME = "fname";
    private static final String LAST_NAME = "lname";

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final String POINTS ="points";



    private static final String PROFILE_URL="image";







    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY, "
                + FIRST_NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + EMAIL + " TEXT, "
                + PASSWORD + " TEXT, "
                + PROFILE_URL + " TEXT,"
                + POINTS + " TEXT)";

        db.execSQL(CREATE_USERS_TABLE);
    }








    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log the old and new versions for debugging
        Log.d("DatabaseUpgrade", "Upgrading database from version " + oldVersion + " to " + newVersion);

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Log the SQL statement for table deletion
        Log.d("DatabaseUpgrade", "Table dropped: " + TABLE_USERS);

        // Create tables again
        onCreate(db);

        // Log the SQL statement for table creation
        Log.d("DatabaseUpgrade", "Table created: " + TABLE_USERS);
    }


    // code to add the new user
    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, user.getFirstname());
        values.put(LAST_NAME, user.getLastname());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());

        values.put(PROFILE_URL, user.getProfileUrl());

        values.put(POINTS,user.getPoints());


        // Inserting Row
        db.insert(TABLE_USERS, null, values);

        // Close the database connection
        db.close();
    }



    // code to get the single user
    User getUser(String userid) {
        if (userid == null) {
            return null;  // or handle the null case accordingly
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] {USER_ID,
                        FIRST_NAME,LAST_NAME,EMAIL, PASSWORD, PROFILE_URL,POINTS},
                USER_ID + "=?",
                new String[] { userid }, null, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setUserId(cursor.getInt(0));
            user.setFirstname(cursor.getString(1));
            user.setLastname(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setPassword(cursor.getString(4));
            user.setProfileUrl(cursor.getString(5));
            user.setPoints(cursor.getInt(6));

            // Add other fields as needed
            cursor.close();
        }

        // close the database
        db.close();

        return user;
    }




    // code to get all users in a list view
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();

                user.setUserId(cursor.getInt(0));
                user.setFirstname(cursor.getString(1));
                user.setLastname(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setPassword(cursor.getString(4));
                user.setProfileUrl(cursor.getString(5));
                user.setPoints(cursor.getInt(6));

                // Add each user to the list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // close the cursor and database
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public int checkUserCreds(String email, String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{USER_ID},
                EMAIL + "=? AND "+PASSWORD+"=?",
                new String[]{email,password},null,null,null
                );
        int userId=-1;
        if(cursor!=null){
            int userIdColumnIndex = cursor.getColumnIndex(USER_ID);
            if(cursor.moveToFirst()&& userIdColumnIndex!=-1){
                userId=cursor.getInt(userIdColumnIndex);
            }

            cursor.close();
        }
        db.close();
        return userId;


    }


    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, user.getFirstname());
        values.put(LAST_NAME, user.getLastname());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD,user.getPassword());
        values.put(PROFILE_URL, user.getProfileUrl());


        // Updating Row
        int rowsAffected = db.update(TABLE_USERS, values, USER_ID + "=?",
                new String[]{String.valueOf(user.getUserId())});

        // Close the database connection
        db.close();

        return rowsAffected;
    }

    public int updateProfile(int userId,String profileUrl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PROFILE_URL,profileUrl);


        // Updating Row
        int rowsAffected = db.update(TABLE_USERS, values, USER_ID + "=?",
                new String[]{String.valueOf(userId)});

        // Close the database connection
        db.close();

        return rowsAffected;
    }



    // Deleting single user
  public void deleteUser(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, USER_ID + " = ?",
                new String[]{userid});
        db.close();
    }



}




