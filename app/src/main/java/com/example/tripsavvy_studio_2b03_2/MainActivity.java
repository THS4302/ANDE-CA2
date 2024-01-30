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
            String log = "Id: " + p.getPlaceId() + " ,Name: " + p.getName()+ "Cat"+p.getPlacecat()+"Descp"+p.getDescription();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        UserDatabaseHandler udb= new UserDatabaseHandler(this);

        udb.addUser(new User(1,"Ren","San","rensan02@gmail.com","abc","https://i.pinimg.com/originals/96/4f/3a/964f3a01c170d9306a7e091729f75935.jpg",0));

       udb.addUser(new User(2,"test","testing","test@test.com","abc","https://i.pinimg.com/originals/e5/1e/ad/e51ead901ccb9d7d9b346306fe085b82.jpg",0));
        udb.addUser(new User(3,"please","work","work@work.com","abc","https://i.pinimg.com/originals/e5/1e/ad/e51ead901ccb9d7d9b346306fe085b82.jpg",0));

        //udb.deleteUser("4");
        //udb.deleteUser("5");
        Log.d("Reading: ", "Reading all users..");
        List<User> users = udb.getAllUsers();

        for (User u : users) {
            String log = "Id: " + u.getUserId() + " ,Name: " + u.getFirstname()+ u.getLastname()+u.getEmail()
                    +u.getPassword()+"  profile:"+u.getProfileUrl();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }



        //farah
        TravelPackageDatabaseHandler pdb = new TravelPackageDatabaseHandler(this);

        //paris
        pdb.addPackage(new TravelPackage(1, "A", 1, "Package A:\n" + "\n" + "Hotel Ritz Paris\n" + "\n" + "Tickets to Louvre Museum\n" + "\n" + "An Eiffel Tower Visit\n" + "\n" + "A One Day Mont Saint-Michel Tour" + "\n", 1200.40f, "https://media.cntraveler.com/photos/61e12ae954fe6df2acc90f89/16:9/w_2560,c_limit/Bar-RitzParis-France-VincentLeroux.jpeg" ));
        pdb.addPackage(new TravelPackage(2, "B", 1, "Package B:\n" + "\n" + "Hotel Holiday Inn Paris - Notre Dame\n" + "\n" + "Sainte-Chapelle\n" + "\n" + "Montmartre and Sacré-Cœur Basilica\n" + "\n" , 999.90f, "https://cf.bstatic.com/xdata/images/hotel/max1280x900/421458624.jpg?k=ad1909a036d4aba99db4284b2ea1eca354c71ba3099754e4cfb413a8c13f8429&o=&hp=1" ));
        pdb.addPackage(new TravelPackage(3, "C", 1, "Package C:\n" + "\n" + "Hotel ibis Paris Gare de Lyon Ledru Rollin\n" + "\n" + "Luxembourg Gardens\n" + "\n" + "Musée Rodin\n" + "\n" , 705.90f, "https://media-cdn.tripadvisor.com/media/photo-s/04/40/ea/5e/ibis-paris-gare-de-lyon.jpg" ));

        //venice
        pdb.addPackage(new TravelPackage(4, "A", 2, "Package A:\n" + "\n" + "Hotel Gritti Palace\n" + "\n" + "Tickets to Doge's Palace\n" + "\n" + "30min Grand Canal Gondola Ride\n" + "\n" + "St. Mark's Basilica" + "\n", 1030.20f, "https://cf.bstatic.com/xdata/images/hotel/max1024x768/433361948.jpg?k=93517212d5fccc6a9b4d24c5149f1d57059a7b17857c2976565e5767bc0538d1&o=&hp=1" ));
        pdb.addPackage(new TravelPackage(5, "B", 2, "Package B:\n" + "\n" + "Hotel Colombina\n" + "\n" + "Rialto Bridge\n" + "\n" + "Gallerie dell'Accademia\n" + "\n" , 769.90f, "https://i0.wp.com/majortraveler.com/wp-content/uploads/2018/04/HC1.jpg?resize=960%2C638&ssl=1" ));
        pdb.addPackage(new TravelPackage(6, "C", 2, "Package C:\n" + "\n" + "Hotel Guerrini\n" + "\n" + "Peggy Guggenheim Collection\n" + "\n" + "Traghetto Ride\n" + "\n" , 605.90f, "https://media-cdn.tripadvisor.com/media/photo-s/0a/60/e0/db/facciata-principale.jpg" ));

        //monaco
        pdb.addPackage(new TravelPackage(7, "A", 3, "Package A:\n" + "\n" + "Hotel Hermitage Monte-Carlo\n" + "\n" + "Unlimited access to the hotel restaurant\n" + "\n" + "Formula One Circuit Guided Tour\n" + "\n" + "French Riviera Full-Day Tour" + "\n", 1259.00f, "https://static-new.lhw.com/HotelImages/Final/LW0661/lw0661_108242211_720x450.jpg" ));
        pdb.addPackage(new TravelPackage(8, "B", 3, "Package B:\n" + "\n" + "Hotel Novotel Monte Carlo\n" + "\n" + "Monte-Carlo & Eze Village Half-Day Tour\n" + "\n" + "Private Collection of Antique Cars\n" + "\n" , 998.50f, "https://cf.bstatic.com/xdata/images/hotel/max1024x768/107545933.jpg?k=e93d2ff1e838fa4d1c2085e6fdd15d480d471b5a8f752c626875bd6f2f596c55&o=&hp=1" ));
        pdb.addPackage(new TravelPackage(9, "C", 3, "Package C:\n" + "\n" + "Hotel Le Best Western Premier Roosevelt\n" + "\n" + "Hop on Hop Off Sightseeing Bus Tour\n" + "\n" , 695.90f, "https://cf.bstatic.com/xdata/images/hotel/max1024x768/258686113.jpg?k=774ea2d3426670c15e9ac79d614e49cab691543f33ab340d815e650a6726ee5a&o=&hp=1" ));

        //Ngwe Saung
        pdb.addPackage(new TravelPackage(10, "A", 4, "Package A:\n" + "\n" + "Aureum Palace Resort & Spa Ngwe Saung\n" + "\n" + "Ngwe Saung Beach\n" + "\n" + "Elephant Camp\n" + "\n" + "Snorkeling" + "\n", 479.60f, "https://aureumpalacehotel.com/wp-content/uploads/2023/03/1600x650-banner-ans.jpg" ));
        pdb.addPackage(new TravelPackage(11, "B", 4, "Package B:\n" + "\n" + "Eskala Hotels & Resorts\n" + "\n" + "Boat Tour to Nearby Islands\n" + "\n" + "Village Excursion\n" + "\n" , 256.70f, "https://q-xx.bstatic.com/xdata/images/hotel/max500/248434674.jpg?k=c713438acafaa1ebe147f37260ad4ba45fc2daa87b306bf0de323aa365b29374&o=" ));
        pdb.addPackage(new TravelPackage(12, "C", 4, "Package C:\n" + "\n" + "Sunny Paradise Resort\n" + "\n" + "Bicycle Tour\n" + "\n" + "Snorkeling Adventure\n" + "\n" , 230.20f, "https://lh3.googleusercontent.com/proxy/kZNs1D71m_X5Kl9ntD1KZgWUwNP1QQ14YxAUp6zlEWvJed4yrnga040Cu6EL7lDFD3mYXzbtgy5gh8JKnvcuX9xnc2EcpLSfYHv6aAIDjwKNDF23PnZsdfcoRqupc1_Q5GqbQMdJ2V5trlpJPHRDpdKim3jRv524w8HY7W4HiEJNacMQi4o2SC8M68fDfBiXX6h8ENddQYdoL3s8lKgi6kNQjn2MuA" ));

        //Pattaya Beach
        pdb.addPackage(new TravelPackage(13, "A", 5, "Package A:\n" + "\n" + "Hotel Amari Pattaya\n\n" + "\n" + "Coral Island (Koh Larn) Tour\n" + "\n" + "Sanctuary of Truth\n" + "\n" + "Walking Street" + "\n", 530.40f, "https://www.hotelscombined.com.sg/himg/8b/b5/b1/revato-10084-12270613-157718.jpg" ));
        pdb.addPackage(new TravelPackage(14, "B", 5, "Package B:\n" + "\n" + "Nova Platinum Hotel\n" + "\n" + "Art in Paradise\n" + "\n" + "Tiffany's Show\n" + "\n" , 470.10f, "https://cf.bstatic.com/xdata/images/hotel/max1024x768/82927325.jpg?k=71666e517a7bf5c6f9c1f12c130ebcd80d59a046202e943da03c9d4399084a0c&o=&hp=1" ));
        pdb.addPackage(new TravelPackage(15, "C", 5, "Package C:\n" + "\n" + "Hotel Sawasdee Sea View\n" + "\n" + "Mini Siam\n" + "\n" + "Pattaya Viewpoint (Khao Pattaya Viewpoint)\n" + "\n" , 325.90f, "https://ik.imagekit.io/tvlk/apr-asset/dgXfoyh24ryQLRcGq00cIdKHRmotrWLNlvG-TxlcLxGkiDwaUSggleJNPRgIHCX6/hotel/asset/10012127-d2ba2c3246dc41f1906c5e6a41b20be1.jpeg?tr=q-40,c-at_max,w-740,h-500&_src=imagekit" ));

        List<TravelPackage> packages = pdb.getAllPackages();

        for (TravelPackage p : packages) {
            String log = "Id: " + p.getPackageid() +
                    " ,Grade: " + p.getGrade() +
                    " ,Place ID: " + p.getPlaceid() +
                    " ,Details: " + p.getDetails() +
                    " ,Price: " + p.getPrice() +
                    " ,Package Image: " + p.getPackageimg();

            // Writing TravelPackages to log
            Log.d("TravelPackage: ", log);
        }

    }
}























