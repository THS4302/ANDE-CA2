package com.example.tripsavvy_studio_2b03_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText loginEmail,loginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail  = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get entered email and password
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                // Check credentials in the database
                UserDatabaseHandler udb = new UserDatabaseHandler(Login.this);
                int userId = udb.checkUserCreds(email, password);

                if (userId != -1) {
                    // User authenticated, userId contains the user's ID
                    // You can use this userId for fetching user profile data
                    Toast.makeText(Login.this, "Login successful! UserID: " + userId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                } else {
                    // Invalid credentials, show an error message
                    Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}