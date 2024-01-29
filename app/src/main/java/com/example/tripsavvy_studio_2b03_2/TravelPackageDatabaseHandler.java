package com.example.tripsavvy_studio_2b03_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TravelPackageDatabaseHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "tripSavvyDB";
    private static final String TABLE_PACKAGES = "travelpackages";

    private static final String PACKAGE_ID = "id";

    private static final String GRADE = "grade";
    private static final String PLACE_ID = "PLACEid";

    private static final String DETAILS = "details";

    private static final String PRICE = "price";

    private static final String PACKAGE_IMG = "IMG";





    public TravelPackageDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PACKAGES_TABLE = "CREATE TABLE " + TABLE_PACKAGES + "("
                + PACKAGE_ID + " INTEGER PRIMARY KEY, "
                + GRADE + " TEXT, "
                + PLACE_ID + " INTEGER, "
                + DETAILS + " TEXT, "
                + PRICE + " REAL, "

                + PACKAGE_IMG + " TEXT)";

        db.execSQL(CREATE_PACKAGES_TABLE);
    }








    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log the old and new versions for debugging
        Log.d("DatabaseUpgrade", "Upgrading database from version " + oldVersion + " to " + newVersion);

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACKAGES);

        // Log the SQL statement for table deletion
        Log.d("DatabaseUpgrade", "Table dropped: " + TABLE_PACKAGES);

        // Create tables again
        onCreate(db);

        // Log the SQL statement for table creation
        Log.d("DatabaseUpgrade", "Table created: " + TABLE_PACKAGES);
    }


    // code to add the new contact
    void addPackage(TravelPackage p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GRADE,p.getGrade());
        values.put(PLACE_ID,p.getPlaceid());
        values.put(DETAILS,p.getDetails());
        values.put(PRICE,p.getPrice());
        values.put(PACKAGE_IMG,p.getPackageimg());





        // Inserting Row
        db.insert(TABLE_PACKAGES, null, values);

        // Close the database connection
        db.close();
    }



    // code to get the single contact
    TravelPackage getPackage(String packageid) {
        if (packageid == null) {
            return null;  // or handle the null case accordingly
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PACKAGES, new String[] {PACKAGE_ID,
                        GRADE,PLACE_ID,DETAILS, PRICE, PACKAGE_IMG},
                PACKAGE_ID + "=?",
                new String[] { packageid }, null, null, null, null);

        TravelPackage p = null;
        if (cursor != null && cursor.moveToFirst()) {
            p = new TravelPackage();
            p.setPackageid(cursor.getInt(0));
            p.setGrade(cursor.getString(1));
            p.setPlaceid(cursor.getInt(2));
            p.setDetails(cursor.getString(3));
            p.setPrice(cursor.getFloat(4));
            p.setPackageimg(cursor.getString(5));


            // Add other fields as needed
            cursor.close();
        }

        // close the database
        db.close();

        return p;
    }



    // code to get all contacts in a list view
    // code to get all users in a list view
  /**  public List<User> getAllUsers() {
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


    // Deleting single user
    public void deleteUser(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, USER_ID + " = ?",
                new String[]{userid});
        db.close();
    }


    // Getting contacts Count
    /**  public int getContactsCount() {
     String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
     SQLiteDatabase db = this.getReadableDatabase();
     Cursor cursor = db.rawQuery(countQuery, null);
     cursor.close();

     // return count
     return cursor.getCount();
     } **/


}




