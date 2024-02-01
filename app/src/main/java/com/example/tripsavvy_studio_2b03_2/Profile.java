package com.example.tripsavvy_studio_2b03_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.MemoryPolicy;
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
            TextView points = findViewById(R.id.points);
            points.setPaintFlags(points.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

// Set values in input boxes
            editFname.setText(user.getFirstname());
            editLname.setText(user.getLastname());
            email.setText(user.getEmail());
            points.setText(user.getPoints()+" points");
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
        Button deleteAccountButton = findViewById(R.id.deleteAccount);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the logout method or perform logout actions here
                deleteAccount();
            }
        });

        Button bookingHistoryButton = findViewById(R.id.bookinghistory);

        bookingHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, BookingHistoryActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent intenth = new Intent(Profile.this, Favorites.class);
                        intenth.putExtra("", userId);

                        return true;

                    case R.id.action_favourites:
                        Intent intentf = new Intent(Profile.this, Favorites.class);
                        intentf.putExtra("userId", userId);

                        startActivity(intentf);
                        return true;

                    case R.id.action_profile:

                        return true;

                    case R.id.action_store:
                        Intent intents = new Intent(Profile.this, Store.class);
                        intents.putExtra("userId", userId);
                        startActivity(intents);

                        return true;

                    default:
                        return false;
                }
            }
        });

        // Set the default selected item programmatically
        bottomNavigationView.setSelectedItemId(R.id.action_home);

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
            Log.d("set","setImgUri"+user.getProfileUrl());
            Log.d("newimg","string"+newimg);
            udb.updateProfile(userId,newimg);


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
        User updatedUser = new User(user.getUserId(), newFirstName, newLastName, newEmail, newPassword, newProfileUrl, user.getPoints());

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

    private void deleteAccount() {
        // Create an AlertDialog with an EditText for password input
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Enter your password to confirm delete (Note: Account will be deleted forever.");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the entered password
                String enteredPassword = input.getText().toString();

                // Check if the entered password is correct (you need to implement this part)
                if (isPasswordCorrect(enteredPassword)) {
                    // Password is correct, perform delete account action
                    performDeleteAccount();
                } else {
                    // Password is incorrect, show a message or handle accordingly
                    Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the AlertDialog
        builder.show();
    }

    // Implement this method to check if the entered password is correct
    private boolean isPasswordCorrect(String enteredPassword) {
        UserDatabaseHandler udb = new UserDatabaseHandler(Profile.this);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);

        User user = udb.getUser(String.valueOf(userId));

        return enteredPassword.equals(user.getPassword());
    }

    // Implement this method for the actual delete account action
    private void performDeleteAccount() {
        UserDatabaseHandler udb = new UserDatabaseHandler(Profile.this);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1);

       udb.deleteUser(String.valueOf(userId));
        Toast.makeText(getApplicationContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show();
        Intent intentdelete = new Intent(Profile.this, Login.class);
        intentdelete.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentdelete);
        finish();
        // Add code to navigate to the login or home screen
    }

    public void onPointsClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the title and message
        builder.setTitle("How To Collect Points");
        builder.setMessage("You can collect points when you purchase any travel packages.\n" +
                "$10=1 point");

        // Add a positive button (OK button) and its click listener
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Dismiss the current dialog
                dialog.dismiss();

                // Show the next dialog
                showNextDialog();
            }
        });

        // Add a negative button (Cancel button) and its click listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Handle Cancel button click (you can perform any action here)
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showNextDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the title and message for the next dialog
        builder.setTitle("How To Use Points");
        builder.setMessage("You can use points when you purchase any travel packages by clicking on the “Use Points” toggle button. \n" +
                "All points will be used. You cannot choose the amount of points.\n" +
                "1 point = $1");

        // Add a positive button (OK button) and its click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Handle OK button click (you can perform any action here)
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        // Create and show the next AlertDialog
        AlertDialog nextDialog = builder.create();
        nextDialog.show();
    }




}

