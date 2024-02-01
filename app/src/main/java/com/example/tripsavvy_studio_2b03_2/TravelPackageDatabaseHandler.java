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

    private static final int DATABASE_VERSION = 16;


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



    public List<TravelPackage> getAllPackages() {
        List<TravelPackage> packageList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PACKAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst()) {
            do {
                TravelPackage p = new TravelPackage();
                p.setPackageid(cursor.getInt(0));
                p.setGrade(cursor.getString(1));
                p.setPlaceid(cursor.getInt(2));
                p.setDetails(cursor.getString(3));
                p.setPrice(cursor.getFloat(4));
                p.setPackageimg(cursor.getString(5));

                // Add each package to the list
                packageList.add(p);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return the package list
        return packageList;
    }

    public void deletePackage(int packageId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete single package
        db.delete(TABLE_PACKAGES, PACKAGE_ID + " = ?", new String[]{String.valueOf(packageId)});

        // Close the database
        db.close();
    }

    public List<TravelPackage> getPackageAByPlaceId(int placeId) {
        return getTravelPackagesByPlaceIdAndGrade(placeId, "A");
    }

    public List<TravelPackage> getPackageBByPlaceId(int placeId) {
        return getTravelPackagesByPlaceIdAndGrade(placeId, "B");
    }

    public List<TravelPackage> getPackageCByPlaceId(int placeId) {
        return getTravelPackagesByPlaceIdAndGrade(placeId, "C");
    }

    private List<TravelPackage> getTravelPackagesByPlaceIdAndGrade(int placeId, String grade) {
        List<TravelPackage> packageList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PACKAGES, new String[]{PACKAGE_ID, GRADE, PLACE_ID, DETAILS, PRICE, PACKAGE_IMG},
                PLACE_ID + "=? AND " + GRADE + "=?", new String[]{String.valueOf(placeId), grade},
                null, null, null);

        // Loop through the cursor to create TravelPackage objects
        Log.e("CheckDataa", String.valueOf(placeId));

        if (cursor.moveToFirst()) {
            do {
                TravelPackage p = new TravelPackage();
                p.setPackageid(cursor.getInt(0));
                p.setGrade(cursor.getString(1));
                p.setPlaceid(cursor.getInt(2));
                p.setDetails(cursor.getString(3));
                p.setPrice(cursor.getFloat(4));
                p.setPackageimg(cursor.getString(5));

                // Add each package to the list
                packageList.add(p);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return packageList;
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




