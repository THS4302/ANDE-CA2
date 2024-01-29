package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        UserDatabaseHandler udb = new UserDatabaseHandler(Profile.this);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);

        User user = udb.getUser(String.valueOf(userId));
        if (user != null) {

            EditText editFname = findViewById(R.id.editFname);
            EditText editLname = findViewById(R.id.editLname);
            TextView email = findViewById(R.id.editEmail);
            EditText editnewPass=findViewById(R.id.newPassword);
            EditText editconPass=findViewById(R.id.confirmPassword);
// Set values in input boxes
            editFname.setText(user.getFirstname());
            editLname.setText(user.getLastname());
            email.setText(user.getEmail());
            //editnewPass.setText(user.getPassword());

// Check if new password fields match
            if (!editnewPass.getText().toString().equals(editconPass.getText().toString())) {
                Toast.makeText(this, "New Password and Confirm New Password don't match", Toast.LENGTH_SHORT).show();
            }




            TextView name = findViewById(R.id.name);
            name.setText(user.getFirstname());
            ImageButton profileImageButton = findViewById(R.id.profileImage);
            Picasso.get().load(user.getProfileUrl()).into(profileImageButton);


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

                    // Update the user profile when the button is clicked
                    updateUserProfile(user, udb);
                }
            });
        } else {
            // Handle the case where user details are not available
            Toast.makeText(this, "User details not found", Toast.LENGTH_SHORT).show();
        }

        Button logoutButton = findViewById(R.id.logout);

        // Set an OnClickListener for the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the logout method or perform logout actions here
                logout();
            }
        });
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
            UserDatabaseHandler udb = new UserDatabaseHandler(Profile.this);
            Intent intent = getIntent();
            int userId = intent.getIntExtra("userId", -1);

            User user = udb.getUser(String.valueOf(userId));
            // Save the new image URI to the user object
            user.setProfileUrl(imageUri.toString());
            String newimg =imageUri.toString();
            Log.d("set","setImgUri"+newimg);
        }
    }

    private void updateUserProfile(User user, UserDatabaseHandler udb) {
        // Get the updated values from the input fields
        String newFirstName = ((EditText) findViewById(R.id.editFname)).getText().toString();
        String newLastName = ((EditText) findViewById(R.id.editLname)).getText().toString();
        String newEmail = ((TextView) findViewById(R.id.editEmail)).getText().toString();
        String newPassword = ((EditText) findViewById(R.id.newPassword)).getText().toString();

        // Check if the new password field is empty
        if (newPassword.isEmpty()) {
            // Retain the existing password
            newPassword = user.getPassword();
        }

        // Get the existing image URL
        String newProfileUrl = user.getProfileUrl();
        Log.d("profileurl","newprofileurl..."+newProfileUrl);

        // Create a new User object with the updated values
        User updatedUser = new User(user.getUserId(), newFirstName, newLastName, newEmail, newPassword, newProfileUrl);

        // Update the user profile in the database
        int rowsAffected = udb.updateUser(updatedUser);

        if (rowsAffected > 0) {
            // Database update successful
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

            // Reload the user from the database after update
            User updatedUserFromDB = udb.getUser(String.valueOf(user.getUserId()));

            // Update the displayed image using Picasso
            ImageButton profileImageButton = findViewById(R.id.profileImage);

            Picasso.get().load(updatedUserFromDB.getProfileUrl()).into(profileImageButton);
        } else {
            // Database update failed
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }


    private void logout() {


        // Navigate to the login screen (replace LoginActivity.class with your login activity)
        Intent intent = new Intent(Profile.this, Login.class);
        // Clear the back stack to prevent going back to the profile screen after logging out
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Finish the current activity
    }

}

