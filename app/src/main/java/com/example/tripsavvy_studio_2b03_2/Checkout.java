package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;


public class Checkout extends AppCompatActivity {

    private int userId;
    private int placeId;
    private String packageGrade;
    private float totalPrice;
    private String date;
    private int quantity;



    private LocationTracker locationTracker;
//    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_summary);
        initialize();

        // Retrieve values from the Intent
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        String selectedPackage = intent.getStringExtra("selectedPackage");
        placeId = intent.getIntExtra("placeId", -1);
        String date = intent.getStringExtra("date");
        int quantity = intent.getIntExtra("quantity", 0);

        // Fetch Place data based on placeId
        DatabaseHandler db = new DatabaseHandler(this);
        Place place = db.getPlace(String.valueOf(placeId));

        TravelPackageDatabaseHandler pdb = new TravelPackageDatabaseHandler(this);
        String packageGrade = getPackageGrade(selectedPackage);
        Log.d("packagelog","packageGrade"+packageGrade);
        float packagePrice = pdb.getPriceByGradeAndPlaceId(packageGrade, placeId);

        // Calculate the total price
        float totalPrice = packagePrice * quantity;

        // Update the TextView with the total price
        TextView totalPriceTextView = findViewById(R.id.totalPrice);
        totalPriceTextView.setText("Total Price: $" + totalPrice);

        ImageView placeImage = findViewById(R.id.imageView);
        Picasso.get().load(place.getImageUrl()).into(placeImage);


        TextView packageSelectedTextView = findViewById(R.id.packageSelected);
        packageSelectedTextView.setText("Package" + selectedPackage);


        // Calculate distance using LocationTracker after initializing
        double placeLat = place.getLatitude();
        double placeLng = place.getLongitude();
        double distance = locationTracker.calculateDistance(locationTracker.getLatitude(), locationTracker.getLongitude(), placeLat, placeLng);

        //namedist.setText(place.getName() + " ,📍" + distance);


        TextView placeIdTextView = findViewById(R.id.placeName);
        placeIdTextView.setText(place.getName() + "\n" + "📍" + distance);

        TextView dateTextView = findViewById(R.id.dateSelected);
        dateTextView.setText("Selected Date: " + date);


        TextView quantityTextView = findViewById(R.id.numberOfPackages);
        quantityTextView.setText("x" + quantity);

    }

    public void onPayButtonClick(View view) {
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        String selectedPackage = intent.getStringExtra("selectedPackage");
        int placeId = intent.getIntExtra("placeId", -1);
        String date = intent.getStringExtra("date");
        int quantity = intent.getIntExtra("quantity", 0);
        String packageGrade = getPackageGrade(selectedPackage);
        // Collect card details from EditText fields
        String cardNumber = ((EditText) findViewById(R.id.cardNumberEditText)).getText().toString();
        String expirationDate = ((EditText) findViewById(R.id.expirationDateEditText)).getText().toString();
        String cvv = ((EditText) findViewById(R.id.cvvEditText)).getText().toString();

        // Fetch card details from the database
        CardDatabaseHandler cardDb = new CardDatabaseHandler(this);
        Card storedCard = cardDb.getCardByDetails(cardNumber, expirationDate, cvv);

        if (storedCard != null) {

            // Card details match, payment approved
            // You may proceed with other actions or navigate to a success screen
            Toast.makeText(this, "Payment Approved", Toast.LENGTH_SHORT).show();

            // Create a Booking instance
            Booking newBooking = new Booking();
            newBooking.setUserId(userId);
            newBooking.setPlaceId(placeId);
            newBooking.setPackageGrade(packageGrade);
            newBooking.setPrice(totalPrice);
            newBooking.setDate(date);
            newBooking.setNumberOfTickets(quantity);

            Log.d("new booking","newbooking"+newBooking.getPackageGrade()+newBooking.getDate());

            // Add the booking to the database
            BookingDatabaseHandler bookingDb = new BookingDatabaseHandler(this);
            long bookingId = bookingDb.addBooking(newBooking);

            // Display a message or perform additional actions
            Toast.makeText(this, "Booking created. Booking ID: " + bookingId, Toast.LENGTH_SHORT).show();

            Intent intentc = new Intent(this, BookingHistoryActivity.class);
            intentc.putExtra("userId", userId);
            startActivity(intentc);
            finish();
        } else {
            // Card details do not match, payment declined
            // You may display an error message or take appropriate action
            Toast.makeText(this, "Payment Declined. Card details do not match.", Toast.LENGTH_SHORT).show();
        }
    }

//
//    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.action_home:
//                    Intent intenth = new Intent(Checkout.this, Home.class);
//                    intenth.putExtra("userId", userId);
//                    startActivity(intenth);
//                    return true;
//
//                case R.id.action_favourites:
//                    Intent intentf = new Intent(Checkout.this, Favorites.class);
//                    intentf.putExtra("userId", userId);
//                    intentf.putExtra("userLat", locationTracker.getLatitude());
//                    intentf.putExtra("userLng", locationTracker.getLongitude());
//                    startActivity(intentf);
//                    return true;
//
//                case R.id.action_profile:
//                    Intent intentp = new Intent(Checkout.this, Profile.class);
//                    intentp.putExtra("userId", userId);
//                    startActivity(intentp);
//                    return true;
//
//                case R.id.action_store:
//                    startActivity(new Intent(Checkout.this, Store.class));
//                    return true;
//
//                default:
//                    return false;
//            }
//        }
//    });
//
//    bottomNavigationView.setSelectedItemId(0);
//}

    private void initialize() {
        // Create the LocationTracker instance
        locationTracker = new LocationTracker(this);
    }

    private String getPackageGrade(String selectedPackage) {
        // Assuming the selectedPackage string is in the format "Package A", "Package B", etc.
        return selectedPackage.substring(selectedPackage.length() - 1);
    }
}