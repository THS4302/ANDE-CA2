package com.example.tripsavvy_studio_2b03_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 15;
  
    private static final String DATABASE_NAME = "tripSavvyPlaceDB";
    private static final String TABLE_PLACES = "places";
    private static final String PLACE_ID = "id";
    private static final String PLACE_NAME = "name";

    private static final String LATITUDE="lat";
    private static final String LONGITUDE="long";
    private static final String IMAGE_URL="image";
    private static final String PLACE_CAT="cat";
    private static final String COUNTRY="country";

    private static final String DESCRIPTION="descp";
    private static final String RATING="rate";






    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLACES_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
                + PLACE_ID + " INTEGER PRIMARY KEY, "
                + PLACE_NAME + " TEXT, "
                + LATITUDE + " REAL, "
                + LONGITUDE + " REAL, "
                + IMAGE_URL + " TEXT,"
                + PLACE_CAT + " TEXT,"
                + COUNTRY + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + RATING + " FLOAT);";
        db.execSQL(CREATE_PLACES_TABLE);
    }








    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLACE_NAME, place.getName());
        values.put(LATITUDE, place.getLatitude());
        values.put(LONGITUDE, place.getLongitude());
        values.put(IMAGE_URL, place.getImageUrl());
        values.put(PLACE_CAT, place.getPlacecat());
        values.put(COUNTRY, place.getCountry());
        values.put(DESCRIPTION, place.getDescription());
        values.put(RATING, place.getRating());

        // Inserting Row
        db.insert(TABLE_PLACES, null, values);

        // Close the database connection
        db.close();
    }



    // code to get the single contact
    Place getPlace(String placeId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[] {String.valueOf(PLACE_ID),
                        PLACE_NAME, String.valueOf(LATITUDE), String.valueOf(LONGITUDE), IMAGE_URL, PLACE_CAT, COUNTRY, DESCRIPTION, RATING },
                PLACE_ID + "=?",
                new String[] { placeId }, null, null, null, null);

        Place place = null;
        if (cursor != null && cursor.moveToFirst()) {
            place = new Place();
            place.setPlaceId(cursor.getInt(0));
            place.setName(cursor.getString(1));
            place.setLatitude(cursor.getDouble(2)); // Use getDouble for latitude
            place.setLongitude(cursor.getDouble(3)); // Use getDouble for longitude
            place.setImageUrl(cursor.getString(4));
            place.setPlacecat(cursor.getString(5));
            place.setDescription(cursor.getString(7));
            place.setRating(cursor.getFloat(8));
            // Add other fields as needed
            cursor.close();
        }

        // close the database
        db.close();

        return place;
    }



    // code to get all contacts in a list view
    public List<Place> getAllPlaces() {
        List<Place> placeList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PLACES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setPlaceId(cursor.getInt(0)); // Assuming the first column is PLACE_ID
                place.setName(cursor.getString(1)); // Assuming the second column is PLACE_NAME
                place.setLatitude(cursor.getDouble(2)); // Assuming the third column is LATITUDE_COLUMN
                place.setLongitude(cursor.getDouble(3)); // Assuming the fourth column is LONGITUDE_COLUMN
                place.setImageUrl(cursor.getString(4)); // Assuming the fifth column is IMAGE_URL_COLUMN
                place.setPlacecat(cursor.getString(5));
                place.setCountry(cursor.getString(6));
                place.setDescription(cursor.getString(7));
                place.setRating(cursor.getFloat(8));
                // Add other fields as needed

                // Adding place to list
                placeList.add(place);
            } while (cursor.moveToNext());
        }

        // close the cursor and database
        cursor.close();
        db.close();

        // return place list
        return placeList;
    }

    /** public int updatePlace(Place place) {
     SQLiteDatabase db = this.getWritableDatabase();

     ContentValues values = new ContentValues();
     values.put(KEY_NAME, contact.getName());
     values.put(KEY_PH_NO, contact.getPhoneNumber());

     // updating row
     return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
     new String[] { String.valueOf(contact.getID()) });
     } **/

    // Deleting single contact
    public void deletePlace(int placeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACES, PLACE_ID + " = ?",
                new String[]{String.valueOf(placeId)});
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


    /** public List<Place> searchPlaces(String query) {
        List<Place> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {PLACE_ID, PLACE_NAME, LATITUDE, LONGITUDE, IMAGE_URL, PLACE_CAT, COUNTRY, DESCRIPTION, RATING};

        // Specify the WHERE clause for the query
        String selection = PLACE_NAME + " LIKE ? OR " +
                COUNTRY + " LIKE ?";
        String[] selectionArgs = {"%" + query + "%", "%" + query + "%"};





        // Execute the query
        Cursor cursor = db.query(TABLE_PLACES, columns, selection, selectionArgs, null, null, null);

        // Iterate through the cursor and add places to the search results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setPlaceId(cursor.getInt(0));
                place.setName(cursor.getString(1));
                place.setLatitude(cursor.getDouble(2));
                place.setLongitude(cursor.getDouble(3));
                place.setImageUrl(cursor.getString(4));
                place.setPlacecat(cursor.getString(5));
                place.setCountry(cursor.getString(6));
                place.setDescription(cursor.getString(7));
                place.setRating(cursor.getFloat(8));
                // Add other fields as needed

                // Adding place to search results
                searchResults.add(place);
            } while (cursor.moveToNext());

            // Close the cursor
            cursor.close();
        }

        // Close the database connection
        db.close();

        return searchResults;
    } **/

    public List<Place> searchPlaces(String query, List<String> categories) {
        List<Place> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] columns = {PLACE_ID, PLACE_NAME, LATITUDE, LONGITUDE, IMAGE_URL, PLACE_CAT, COUNTRY, DESCRIPTION, RATING};

        // Specify the WHERE clause for the query with multiple conditions
        StringBuilder selection = new StringBuilder();
        List<String> selectionArgsList = new ArrayList<>();

        // Add conditions for place name or country
        if (query != null && !query.isEmpty()) {
            selection.append("(")
                    .append(PLACE_NAME).append(" LIKE ? OR ")
                    .append(COUNTRY).append(" LIKE ?")
                    .append(") AND ");

            selectionArgsList.add("%" + query + "%");
            selectionArgsList.add("%" + query + "%");
        }

        // Add conditions for categories
        if (categories != null && !categories.isEmpty()) {
            selection.append("(");
            for (int i = 0; i < categories.size(); i++) {
                selection.append(PLACE_CAT).append(" = ?");
                selectionArgsList.add(categories.get(i));
                if (i < categories.size() - 1) {
                    selection.append(" OR ");
                }
            }
            selection.append(") AND ");
        }

        // Remove the trailing " AND "
        if (selection.length() >= 5) {
            selection.setLength(selection.length() - 5);
        }

        // Convert the list to an array
        String[] selectionArgs = selectionArgsList.toArray(new String[0]);

        // Execute the query
        Cursor cursor = db.query(TABLE_PLACES, columns, selection.toString(), selectionArgs, null, null, null);

        // Iterate through the cursor and add places to the search results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setPlaceId(cursor.getInt(0));
                place.setName(cursor.getString(1));
                place.setLatitude(cursor.getDouble(2));
                place.setLongitude(cursor.getDouble(3));
                place.setImageUrl(cursor.getString(4));
                place.setPlacecat(cursor.getString(5));
                place.setCountry(cursor.getString(6));
                place.setDescription(cursor.getString(7));
                place.setRating(cursor.getFloat(8));
                // Add other fields as needed

                // Adding place to search results
                searchResults.add(place);
            } while (cursor.moveToNext());

            // Close the cursor
            cursor.close();
        }

        // Close the database connection
        db.close();

        return searchResults;
    }


}




