package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_summary);

        // Retrieve values from the Intent
        Intent intent = getIntent();
        String selectedPackage = intent.getStringExtra("selectedPackage");
        int placeId = intent.getIntExtra("placeId", -1);
        String date = intent.getStringExtra("date");
        int quantity = intent.getIntExtra("quantity", 0);

        // Fetch Place data based on placeId
        DatabaseHandler db = new DatabaseHandler(this);
        Place place = db.getPlace(String.valueOf(placeId));

        TravelPackageDatabaseHandler pdb = new TravelPackageDatabaseHandler(this);
        String packageGrade = getPackageGrade(selectedPackage);
        float packagePrice = pdb.getPriceByGradeAndPlaceId(packageGrade, placeId);

        // Calculate the total price
        float totalPrice = packagePrice * quantity;

        // Update the TextView with the total price
        TextView totalPriceTextView = findViewById(R.id.totalPrice);
        totalPriceTextView.setText("Total Price: $" + totalPrice);

        ImageView placeImage = findViewById(R.id.imageView);
        Picasso.get().load(place.getImageUrl()).into(placeImage);


        TextView packageSelectedTextView = findViewById(R.id.packageSelected);
        packageSelectedTextView.setText("Package Selected: " + selectedPackage);

        TextView placeIdTextView = findViewById(R.id.placeName);
        placeIdTextView.setText("Place Name: " + place.getName());

        TextView dateTextView = findViewById(R.id.dateSelected);
        dateTextView.setText("Selected Date: " + date);


        TextView quantityTextView = findViewById(R.id.numberOfPackages);
        quantityTextView.setText("x" + quantity);

    }

    private String getPackageGrade(String selectedPackage) {
        // Assuming the selectedPackage string is in the format "Package A", "Package B", etc.
        return selectedPackage.substring(selectedPackage.length() - 1);
    }
}
