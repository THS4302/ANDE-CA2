package com.example.tripsavvy_studio_2b03_2;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.List;

public class insertData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);

        // Inserting Contacts
        Log.d("Insert Data : ", "Inserting ..");
        //db.addPlace(new Place(1,"Paris", 48.85661400,2.35222190,"https://images.adsttc.com/media/images/5d44/14fa/284d/d1fd/3a00/003d/large_jpg/eiffel-tower-in-paris-151-medium.jpg?1564742900","Attraction","France"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Place> places = db.getAllPlaces();

        for (Place p : places) {
            String log = "Id: " + p.getPlaceId() + " ,Name: " + p.getName();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }
}

