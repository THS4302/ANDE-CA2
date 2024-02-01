package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.DatePicker;



import com.example.tripsavvy_studio_2b03_2.TravelPackageDatabaseHandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPackages extends AppCompatActivity {
    private int userId;
    private LocationTracker locationTracker;


    private int placeId;

    private List<TravelPackage> travelPackageA;
    private List<TravelPackage> travelPackageB;
    private List<TravelPackage> travelPackageC;

    private String selectedPackage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);
        initialize();
    Log.e("checkData", "reached");
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        placeId = intent.getIntExtra("placeId", -1);

        DatabaseHandler db = new DatabaseHandler(this);
        Place place = db.getPlace(String.valueOf(placeId));

        TravelPackageDatabaseHandler pdb = new TravelPackageDatabaseHandler(this);
        travelPackageA = pdb.getPackageAByPlaceId(placeId);


        Log.e("checkData", travelPackageA.toString());
        travelPackageB = pdb.getPackageBByPlaceId(placeId);
        travelPackageC = pdb.getPackageCByPlaceId(placeId);
        for (TravelPackage packageA : travelPackageA) {
            Log.d("TravelPackageA", "Package ID: " + packageA.getPackageid());
            Log.d("TravelPackageA", "Grade: " + packageA.getGrade());
            Log.d("TravelPackageA", "Place ID: " + packageA.getPlaceid());
            Log.d("TravelPackageA", "Details: " + packageA.getDetails());
            Log.d("TravelPackageA", "Price: " + packageA.getPrice());
            Log.d("TravelPackageA", "Package Img: " + packageA.getPackageimg());
            Log.d("TravelPackageA", "-------------------");
        }

        Log.d("PackageASize", "Size: " + travelPackageA.size());
        Log.d("PackageBSize", "Size: " + travelPackageB.size());
        Log.d("PackageCSize", "Size: " + travelPackageC.size());

        ImageButton favoriteButton = findViewById(R.id.buttonFavorite2);
        favoriteButton.setTag(place.getPlaceId());

        // Check if the place is already a favorite
        boolean isFavorite = isPlaceFavorite(placeId);
        favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);


        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the favorite state
                toggleFavorite(place.getPlaceId(), favoriteButton);

                // Update the button state after toggling
                boolean isFavorite = isPlaceFavorite(placeId);
                favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);
            }
        });

        Button checkoutButton = findViewById(R.id.checkoutButton);
        EditText quantityEditText = findViewById(R.id.quantity);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedPackage = getSelectedPackage();

                // Check if a package is selected
                if (selectedPackage != null) {
                    // Get the selected date from the DatePicker
                    DatePicker datePicker = findViewById(R.id.datePicker);
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth() + 1;
                    int dayOfMonth = datePicker.getDayOfMonth();

                    // Create a formatted date string
                    String selectedDate = year + "-" + month + "-" + dayOfMonth;

                    Intent intent = new Intent(ViewPackages.this, Checkout.class);

                    // Put the selected values into the Intent
                    intent.putExtra("selectedPackage", selectedPackage);
                    intent.putExtra("placeId", placeId);
                    intent.putExtra("date", selectedDate);


                    EditText quantityEditText = findViewById(R.id.quantity);
                    String quantityString = quantityEditText.getText().toString();
                    if (!quantityString.isEmpty()) {
                        int quantity = Integer.parseInt(quantityString);
                        intent.putExtra("quantity", quantity);
                    } else {
                        Toast.makeText(ViewPackages.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                        return; // Don't proceed if quantity is empty
                    }

                    startActivity(intent);
                } else {
                    Toast.makeText(ViewPackages.this, "Please select a package", Toast.LENGTH_SHORT).show();
                }
            }
        });



        if (place != null) {
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleFavorite(place.getPlaceId(), favoriteButton);
                }
            });


            ImageView placeImage = findViewById(R.id.placeImage);
            Picasso.get().load(place.getImageUrl()).into(placeImage);

            TextView namedist = findViewById(R.id.placedNamedist);

            // Calculate distance using LocationTracker after initializing
            double placeLat = place.getLatitude();
            double placeLng = place.getLongitude();
            double distance = locationTracker.calculateDistance(locationTracker.getLatitude(), locationTracker.getLongitude(), placeLat, placeLng);

            namedist.setText(place.getName() + " ,📍" + distance);





//            //edit starting from here
//            ImageView pack1img = findViewById(R.id.package1Image);
////            Picasso.get().load(travelPackageA.getPackageimg()).into(pack1img);
//
//            // Assuming travelPackageA has the packages for grade A
//            if (!travelPackageA.isEmpty()) {
//                TravelPackage packageA = travelPackageA.get(1); // Assuming you want the first package
//                String imageUrl = packageA.getPackageimg();
//
//                // Use Picasso or another image loading library to load the image into ImageView
//                Picasso.get().load(imageUrl).into(pack1img);
//
//                // Set the value of package1Price TextView
//                TextView package1Price = findViewById(R.id.package1Price);
//                package1Price.setText("" + packageA.getPrice());
//
//                // Set the value of package1Details TextView
//                TextView package1Details = findViewById(R.id.package1Details);
//                package1Details.setText("" + packageA.getDetails());
//
//            }
//
//
//            ImageView pack2img = findViewById(R.id.package2Image);
////            Picasso.get().load(travelPackageA.getPackageimg()).into(pack1img);
//
//            // Assuming travelPackageA has the packages for grade A
//            if (!travelPackageB.isEmpty()) {
//                TravelPackage packageB = travelPackageB.get(2); // Assuming you want the first package
//                String imageUrl = packageB.getPackageimg();
//
//                // Use Picasso or another image loading library to load the image into ImageView
//                Picasso.get().load(imageUrl).into(pack1img);
//
//                // Set the value of package1Price TextView
//                TextView package2Price = findViewById(R.id.package2Price);
//                package2Price.setText("" + packageB.getPrice());
//
//                // Set the value of package1Details TextView
//                TextView package2Details = findViewById(R.id.package2Details);
//                package2Details.setText("" + packageB.getDetails());
//
//            }
//
//
//            ImageView pack3img = findViewById(R.id.package3Image);
////            Picasso.get().load(travelPackageA.getPackageimg()).into(pack1img);
//
//            // Assuming travelPackageA has the packages for grade A
//            if (!travelPackageC.isEmpty()) {
//                TravelPackage packageC = travelPackageC.get(3); // Assuming you want the first package
//                String imageUrl = packageC.getPackageimg();
//
//                // Use Picasso or another image loading library to load the image into ImageView
//                Picasso.get().load(imageUrl).into(pack1img);
//
//                // Set the value of package1Price TextView
//                TextView package3Price = findViewById(R.id.package3Price);
//                package3Price.setText("" + packageC.getPrice());
//
//                // Set the value of package1Details TextView
//                TextView package3Details = findViewById(R.id.package3Details);
//                package3Details.setText("" + packageC.getDetails());
//
//            }


            if (!travelPackageA.isEmpty()) {
                TravelPackage packageA = travelPackageA.get(0); // Assuming you want the first package



                String imageUrl = packageA.getPackageimg();

                // Use Picasso or another image loading library to load the image into ImageView
                ImageView pack1img = findViewById(R.id.package1Image);
                Picasso.get().load(imageUrl).into(pack1img);

                // Set the value of package1Price TextView
                TextView package1Price = findViewById(R.id.package1Price);
                package1Price.setText(String.valueOf(packageA.getPrice()));

                // Set the value of package1Details TextView
                TextView package1Details = findViewById(R.id.package1Details);
                package1Details.setText(packageA.getDetails());
            }

// Repeat the above block of code for packageB and packageC with appropriate changes
            if (!travelPackageB.isEmpty()) {
                TravelPackage packageB = travelPackageB.get(0); // Assuming you want the first package
                String imageUrl = packageB.getPackageimg();

                // Use Picasso or another image loading library to load the image into ImageView
                ImageView pack2img = findViewById(R.id.package2Image);
                Picasso.get().load(imageUrl).into(pack2img);

                // Set the value of package2Price TextView
                TextView package2Price = findViewById(R.id.package2Price);
                package2Price.setText(String.valueOf(packageB.getPrice()));

                // Set the value of package2Details TextView
                TextView package2Details = findViewById(R.id.package2Details);
                package2Details.setText(packageB.getDetails());
            }

            if (!travelPackageC.isEmpty()) {
                TravelPackage packageC = travelPackageC.get(0); // Assuming you want the first package
                String imageUrl = packageC.getPackageimg();

                // Use Picasso or another image loading library to load the image into ImageView
                ImageView pack3img = findViewById(R.id.package3Image);
                Picasso.get().load(imageUrl).into(pack3img);

                // Set the value of package3Price TextView
                TextView package3Price = findViewById(R.id.package3Price);
                package3Price.setText(String.valueOf(packageC.getPrice()));
                Log.d("price", String.valueOf(packageC.getPrice()));

                // Set the value of package3Details TextView
                TextView package3Details = findViewById(R.id.package3Details);
                package3Details.setText(packageC.getDetails());
            }






//            TextView placeDescription = findViewById(R.id.placeDescription);
//            placeDescription.setText(place.getDescription());
//            Log.d("descp","descp:"+place.getDescription());
        }

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intenth = new Intent(ViewPackages.this, Home.class);
                        intenth.putExtra("userId", userId);

                        startActivity(intenth);
                        return true;

                    case R.id.action_favourites:
                        Intent intentf = new Intent(ViewPackages.this, Favorites.class);
                        intentf.putExtra("userId", userId);
                        intentf.putExtra("userLat", locationTracker.getLatitude());
                        intentf.putExtra("userLng", locationTracker.getLongitude());
                        startActivity(intentf);
                        return true;

                    case R.id.action_profile:
                        Intent intentp = new Intent(ViewPackages.this, Profile.class);
                        intentp.putExtra("userId", userId);
                        startActivity(intentp);
                        return true;

                    case R.id.action_store:
                        startActivity(new Intent(ViewPackages.this, Store.class));
                        return true;

                    default:
                        return false;
                }
            }
        });
        bottomNavigationView.setSelectedItemId(0);
        // Set the default selected item programmatically

    }
    private void initialize() {
        // Create the LocationTracker instance
        locationTracker = new LocationTracker(this);
    }
    private boolean isPlaceFavorite(int placeId) {
        // Retrieve the current state of the favorite for the given placeId
        return getSharedPreferences("Favorites", MODE_PRIVATE)
                .getBoolean(getFavoriteKey(placeId), false);
    }

    private void toggleFavorite(int placeId, ImageButton favoriteButton) {
        // Toggle the favorite state
        boolean isFavorite = !isPlaceFavorite(placeId);

        // Update the button state
        favoriteButton.setImageResource(isFavorite ? R.drawable.fav_buttonpressed : R.drawable.imgbutton_fav);

        // Save the updated state to SharedPreferences
        getSharedPreferences("Favorites", MODE_PRIVATE)
                .edit()
                .putBoolean(getFavoriteKey(placeId), isFavorite)
                .apply();

        // Show a Toast message based on the current favorite state
        if (isFavorite) {
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }

    private String getSelectedPackage() {
        // Retrieve the selected package value from your Spinner or wherever it is stored
        Spinner packageSpinner = findViewById(R.id.packageSpinner);
        return packageSpinner.getSelectedItem().toString();
    }

    private void launchCheckoutActivity() {
        // Create an Intent to start CheckoutActivity
        Intent intent = new Intent(ViewPackages.this, Checkout.class);

        // Pass data to CheckoutActivity using Intent extras
        intent.putExtra("selectedPackage", selectedPackage);  // You need to set the value of selectedPackage

        // Add more data if needed
        intent.putExtra("userId", userId);
        intent.putExtra("placeId", placeId);

        // Start the CheckoutActivity
        startActivity(intent);
    }



    private String getFavoriteKey(int placeId) {
        // Generate a unique key for storing the favorite state of a place
        return "favorite_" + placeId;
    }

}
