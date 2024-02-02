package com.example.tripsavvy_studio_2b03_2;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.view.View;







public class BookingHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinghistory);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        Log.d("BookingHistoryActivity", "Received userId: " + userId);

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerView);
        bookingAdapter = new BookingAdapter(userId);

        // Set layout manager and adapter for RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookingAdapter);

        // Call loadBookingHistory to retrieve and display booking data
        loadBookingHistory();

        // Back Button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the current activity and go back to the previous one
                finish();
            }
        });
    }



    private void loadBookingHistory() {
        // Retrieve booking history based on userId from the database
        BookingDatabaseHandler bookingDb = new BookingDatabaseHandler(this);
        List<Booking> bookingList = bookingDb.getBookingsByUserId(userId);
        for (Booking booking : bookingList) {
            String log = "BookingId: " + booking.getBookingId() +
                    ", UserId: " + booking.getUserId() +
                    ", PlaceId: " + booking.getPlaceId() +
                    // ... add other relevant fields
                    ", Check-in Date: " + booking.getDate() +
                    ", Check-out Date: " + booking.getPackageGrade();

            Log.d("BookingList", log);
        }

        // Log the size of the bookingList to verify data
        Log.d("BookingHistoryActivity", "Booking List Size: " + bookingList.size());

        // Update the adapter with the booking data
        bookingAdapter.setBookings(bookingList);
    }


}

