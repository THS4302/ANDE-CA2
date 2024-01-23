package com.example.tripsavvy_studio_2b03_2;


//Thet Htar San 2235077
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.FirebaseApp;

import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      DatabaseHandler db = new DatabaseHandler(this);

        // Inserting Contacts
       // Log.d("Insert Data : ", "Inserting ..");
      db.addPlace(new Place(1,"Paris", 48.85661400,2.35222190,"https://images.adsttc.com/media/images/5d44/14fa/284d/d1fd/3a00/003d/large_jpg/eiffel-tower-in-paris-151-medium.jpg?1564742900","Attraction","France","\n" +
                "Welcome to Paris, the City of Light, where history, culture, and romance converge. Iconic landmarks like the Eiffel Tower and Louvre Museum await, offering breathtaking views and artistic treasures. Stroll down the Champs-Élysées to the Arc de Triomphe, explore the bohemian charm of Montmartre, and cruise the Seine River for a romantic perspective. Indulge in Parisian cuisine, from pastries in charming cafes to Michelin-starred restaurants. Every cobblestone street tells" +
                "a story, inviting you to immerse yourself in a city that has inspired dreamers for centuries." +
                " Paris is an unforgettable journey into the heart of culture and sophistication.", 0));
        db.addPlace(new Place(2,"Venice",45.44146850 ,12.31526720, "https://www.tripsavvy.com/thmb/QUyteNPHX41p41kCHqFpjrVq8zs=/3865x2576/filters:no_upscale():max_bytes(150000):strip_icc()/grand-canal-in-venice--italy-584393947-5a89a0d78023b900374a486b.jpg",
                "Attraction","Italy",
                "Welcome to Venice, the \"Floating City,\" where enchanting canals wind through historic streets. Glide on a gondola along the Grand Canal, passing beneath iconic bridges. St. Mark's Square, with its Basilica and Campanile, is a cultural hub. Explore the vibrant Rialto Market and its lively atmosphere. Venice's architecture showcases a mix of Gothic, Renaissance, and Byzantine styles. During the Carnival of Venice, the city transforms into a celebration of masks and festivities. Immerse yourself in art and culture at museums like the Peggy Guggenheim Collection. Venice is a timeless destination, where each corner reveals the city's unique charm and allure.",0));
        db.addPlace(new Place(3,"Monaco",43.73333333,7.40000000 ,
                "https://www.facefull-news.com/wp-content/uploads/Que-faire-a-Monaco-en-1-jour-Monte-Carlo-2048x1365.jpg",
                "Hilly","Monaco", "Welcome to Monaco, a glamorous principality on the French Riviera. Nestled along the Mediterranean coastline, Monaco is synonymous with luxury, casinos, and Formula 1 racing. With its glittering cityscape, high-end boutiques, and the famous Casino de Monte-Carlo, this tiny enclave exudes opulence. Explore the historic Prince's Palace, revel in the vibrant atmosphere of the Monaco Grand Prix, and indulge in the chic ambiance of this playground for the rich and famous. Monaco is a captivating blend of sophistication and extravagance, " +
                "making it a unique and glamorous destination.",0));

        db.addPlace(new Place(4,"Ngwe Saung",16.848522,94.396410, "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/13/53/6e/30/this-is-at-the-sea-side.jpg?w=1200&h=-1&s=1",
                "Beach","Myanmar","Welcome to Ngwe Saung, a serene beach paradise on the Bay of Bengal in Myanmar. With its pristine white sands stretching along the coastline, Ngwe Saung offers a tranquil escape in a tropical setting. This idyllic destination is known for its crystal-clear waters, palm-fringed shores, and a laid-back atmosphere that beckons travelers seeking relaxation.\n" +
                "\n" +
                "Explore the charming fishing villages nearby, savor fresh seafood at local eateries, and unwind in the warm embrace of the sun. Ngwe Saung's beauty extends beyond its beaches to lush landscapes, providing a perfect backdrop for leisurely walks and exploration.\n" +
                "\n" +
                "Whether you're seeking a peaceful retreat or engaging in water activities like snorkeling and boating, Ngwe Saung promises a rejuvenating experience. Immerse yourself in the natural beauty and unhurried pace of life that define this hidden gem along Myanmar's coastline.",0));
        // Reading all contacts
        db.addPlace(new Place(5,"Pattaya Beach",12.927608, 100.877083,"https://d13jio720g7qcs.cloudfront.net/images/destinations/origin/5de4c32872c52.jpg", "Beach","Thailand",
                "\n" +
                        "Welcome to Pattaya Beach, a vibrant coastal destination on the Gulf of Thailand. Known for its lively atmosphere and diverse offerings, Pattaya is a popular seaside resort that caters to a variety of interests.\n" +
                        "\n" +
                        "Stretching along the shoreline, Pattaya Beach boasts golden sands and a bustling promenade. From water activities like jet skiing and parasailing to beachside relaxation and vibrant nightlife, this destination offers a dynamic blend of leisure and entertainment.\n" +
                        "\n" +
                        "Explore the vibrant street markets, indulge in delectable Thai cuisine at beachfront restaurants, and witness the lively energy that permeates the area. Pattaya's nightlife is renowned, with a variety of bars, clubs, and entertainment options that cater to every taste.\n" +
                        "\n" +
                        "For those seeking cultural experiences, nearby attractions like the Sanctuary of Truth showcase traditional Thai architecture and craftsmanship. Pattaya seamlessly blends the excitement of a beach resort with the charm of Thai hospitality, making it a versatile destination for travelers seeking both relaxation and entertainment.",0));

        Log.d("Reading: ", "Reading all contacts..");
        List<Place> places = db.getAllPlaces();

        for (Place p : places) {
            String log = "Id: " + p.getPlaceId() + " ,Name: " + p.getName()+ "Cat"+p.getPlacecat();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

       UserDatabaseHandler udb= new UserDatabaseHandler(this);

      //udb.addUser(new User(1,"Ren","San","rensan02@gmail.com","abc","https://i.pinimg.com/originals/96/4f/3a/964f3a01c170d9306a7e091729f75935.jpg"));
     //udb.addUser(new User(2,"test","testing","test@test.com","abc","https://i.pinimg.com/originals/e5/1e/ad/e51ead901ccb9d7d9b346306fe085b82.jpg"));
    //udb.deleteUser("3");
        //udb.deleteUser("4");
        Log.d("Reading: ", "Reading all users..");
        List<User> users = udb.getAllUsers();

        for (User u : users) {
            String log = "Id: " + u.getUserId() + " ,Name: " + u.getFirstname();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }
}