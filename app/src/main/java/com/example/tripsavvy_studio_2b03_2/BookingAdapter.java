package com.example.tripsavvy_studio_2b03_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<Booking> bookings;
    private int userId;

    // Constructor
    public BookingAdapter(int userId) {
        this.userId = userId;
        this.bookings = new ArrayList<>();
    }

    // Setters for updating data
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);

        // Retrieve Place information based on placeId from the database
        DatabaseHandler placeDb = new DatabaseHandler(holder.itemView.getContext());
        Place place = placeDb.getPlace(String.valueOf(booking.getPlaceId()));

        if (place != null) {
            // Bind data to the views in RelativeLayout using Picasso
            Picasso.get().load(place.getImageUrl()).into(holder.imageView);
            holder.placeNameTextView.setText("Place Name: " + place.getName());
            holder.packageSelectedTextView.setText("Package Selected: " + booking.getPackageGrade());
            holder.dateSelectedTextView.setText("Date Selected: " + booking.getDate());
            holder.numberOfPackagesTextView.setText("Number of Packages: " + booking.getNumberOfTickets());

        } else {
            // Handle the case where Place is null (optional, set default values)
            holder.imageView.setImageResource(R.drawable.package1test);
            holder.placeNameTextView.setText("Place Name: N/A");
            holder.packageSelectedTextView.setText("Package Selected: N/A");
            holder.dateSelectedTextView.setText("Date Selected: N/A");
            holder.numberOfPackagesTextView.setText("Number of Packages: 0");
        }
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    // ViewHolder class
    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView placeNameTextView;
        public TextView packageSelectedTextView;
        public TextView dateSelectedTextView;
        public TextView numberOfPackagesTextView;

        public BookingViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            placeNameTextView = itemView.findViewById(R.id.placeName);
            packageSelectedTextView = itemView.findViewById(R.id.packageSelected);
            dateSelectedTextView = itemView.findViewById(R.id.dateSelected);
            numberOfPackagesTextView = itemView.findViewById(R.id.numberOfPackages);
        }
    }
}
