package com.example.tripsavvy_studio_2b03_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;
import android.util.Log;




public class BookingDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 18;
    private static final String DATABASE_NAME = "bookingManager";
    private static final String TABLE_BOOKINGS = "bookings";

    private static final String KEY_ID = "bookingId";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_PLACE_ID = "placeId";
    private static final String KEY_PACKAGE_GRADE = "packageGrade";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DATE = "date";
    private static final String KEY_NUMBER_OF_TICKETS = "numberOfTickets";

    public BookingDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_ID + " INTEGER,"
                + KEY_PLACE_ID + " INTEGER,"
                + KEY_PACKAGE_GRADE + " TEXT,"
                + KEY_PRICE + " REAL,"
                + KEY_DATE + " TEXT,"
                + KEY_NUMBER_OF_TICKETS + " INTEGER" + ")";
        db.execSQL(CREATE_BOOKINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    public long addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, booking.getUserId());
        values.put(KEY_PLACE_ID, booking.getPlaceId());
        values.put(KEY_PACKAGE_GRADE, booking.getPackageGrade());
        values.put(KEY_PRICE, booking.getPrice());
        values.put(KEY_DATE, booking.getDate());
        values.put(KEY_NUMBER_OF_TICKETS, booking.getNumberOfTickets());

        // Insert the row
        long id = db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return id;
    }

    public Booking getBooking(int bookingId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKINGS, null, KEY_ID + "=?",
                new String[]{String.valueOf(bookingId)}, null, null, null, null);

        Booking booking = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(KEY_ID);
                int userIdIndex = cursor.getColumnIndex(KEY_USER_ID);
                int placeIdIndex = cursor.getColumnIndex(KEY_PLACE_ID);
                int packageGradeIndex = cursor.getColumnIndex(KEY_PACKAGE_GRADE);
                int priceIndex = cursor.getColumnIndex(KEY_PRICE);
                int dateIndex = cursor.getColumnIndex(KEY_DATE);
                int numberOfTicketsIndex = cursor.getColumnIndex(KEY_NUMBER_OF_TICKETS);

                // Check if the column indices are valid
                if (idIndex != -1 && userIdIndex != -1 && placeIdIndex != -1
                        && packageGradeIndex != -1 && priceIndex != -1
                        && dateIndex != -1 && numberOfTicketsIndex != -1) {
                    booking = new Booking();
                    booking.setBookingId(cursor.getInt(idIndex));
                    booking.setUserId(cursor.getInt(userIdIndex));
                    booking.setPlaceId(cursor.getInt(placeIdIndex));
                    booking.setPackageGrade(cursor.getString(packageGradeIndex));
                    booking.setPrice(cursor.getFloat(priceIndex));
                    booking.setDate(cursor.getString(dateIndex));
                    booking.setNumberOfTickets(cursor.getInt(numberOfTicketsIndex));
                }
            }
            cursor.close();
        }

        db.close();
        return booking;
    }

    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("BookingDatabaseHandler", "Querying for userId: " + userId);
        String query = "SELECT * FROM " + TABLE_BOOKINGS + " WHERE " + KEY_USER_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});


        if (cursor != null) {
            while (cursor.moveToNext()) {
                Booking booking = new Booking();
                int idIndex = cursor.getColumnIndex(KEY_ID);
                int userIdIndex = cursor.getColumnIndex(KEY_USER_ID);
                int placeIdIndex = cursor.getColumnIndex(KEY_PLACE_ID);
                int packageGradeIndex = cursor.getColumnIndex(KEY_PACKAGE_GRADE);
                int priceIndex = cursor.getColumnIndex(KEY_PRICE);
                int dateIndex = cursor.getColumnIndex(KEY_DATE);
                int numberOfTicketsIndex = cursor.getColumnIndex(KEY_NUMBER_OF_TICKETS);

                if (idIndex != -1) {
                    booking.setBookingId(cursor.getInt(idIndex));
                }
                if (userIdIndex != -1) {
                    booking.setUserId(cursor.getInt(userIdIndex));
                }
                if (placeIdIndex != -1) {
                    booking.setPlaceId(cursor.getInt(placeIdIndex));
                }
                if (packageGradeIndex != -1) {
                    booking.setPackageGrade(cursor.getString(packageGradeIndex));
                }
                if (priceIndex != -1) {
                    booking.setPrice(cursor.getFloat(priceIndex));
                }
                if (dateIndex != -1) {
                    booking.setDate(cursor.getString(dateIndex));
                }
                if (numberOfTicketsIndex != -1) {
                    booking.setNumberOfTickets(cursor.getInt(numberOfTicketsIndex));
                }

                bookingList.add(booking);
            }

            cursor.close();
        }

        db.close();
        Log.d("BookingDatabaseHandler", "Number of rows retrieved: " + bookingList.size());
        return bookingList;
    }


}

