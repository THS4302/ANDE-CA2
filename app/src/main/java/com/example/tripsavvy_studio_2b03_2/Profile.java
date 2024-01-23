package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserDatabaseHandler udb = new UserDatabaseHandler(Profile.this);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);

        User user = udb.getUser(String.valueOf(userId));
        if (user != null) {
            // Now you can set the user details in your input boxes
            EditText editFname = findViewById(R.id.editFname);
            EditText editLname = findViewById(R.id.editLname);
            EditText editEmail = findViewById(R.id.editEmail);
            EditText editnewPass=findViewById(R.id.newPassword);
            EditText editconPass=findViewById(R.id.confirmPassword);

            // Set values in input boxes
            editFname.setText(user.getFirstname());
            editLname.setText(user.getLastname());
            editEmail.setText(user.getEmail());

            if (!editnewPass.getText().toString().equals(editconPass.getText().toString())) {
                Toast.makeText(this, "New Password and Confirm New Password don't match", Toast.LENGTH_SHORT).show();
            }

            editnewPass.setText(user.getPassword());

            TextView name = findViewById(R.id.name);
            name.setText(user.getFirstname());
            ImageButton profileImageButton = findViewById(R.id.profileImage);
            Picasso.get().load(user.getProfileUrl()).into(profileImageButton);

            // ImageButton click event for changing the profile image
            //ImageButton profileImageButton = findViewById(R.id.profileImage);
            profileImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImagePicker();
                }
            });


            Button updateProfileButton = findViewById(R.id.updateProfile);
            updateProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update user profile when the button is clicked
                    updateUserProfile(user, udb);
                }
            });
        } else {
            // Handle the case where user details are not available
            Toast.makeText(this, "User details not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagePicker() {
        // Implement code to open an image picker or gallery
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            // Handle the selected image URI (e.g., save it to the database or display it)
            // Example using Picasso:
            ImageButton profileImageButton = findViewById(R.id.profileImage);
            Picasso.get().load(imageUri).into(profileImageButton);
        }
    }

    private void updateUserProfile(User user, UserDatabaseHandler udb) {
        // Get the updated values from the input fields
        String newFirstName = ((EditText) findViewById(R.id.editFname)).getText().toString();
        String newLastName = ((EditText) findViewById(R.id.editLname)).getText().toString();
        String newEmail = ((EditText) findViewById(R.id.editEmail)).getText().toString();
        String newPassword = ((EditText) findViewById(R.id.newPassword)).getText().toString();
        String newProfileUrl = ""; // You need to get the new image URI here

        // Create a new User object with the updated values
        User updatedUser = new User(user.getUserId(), newFirstName, newLastName, newEmail, newPassword, newProfileUrl);

        // Update the user profile in the database
        int rowsAffected = udb.updateUser(updatedUser);

        if (rowsAffected > 0) {
            // Database update successful
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Database update failed
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}

