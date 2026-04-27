package com.example.cookingrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        EditText usernameInput= findViewById(R.id.et_username);
        EditText passwordInput= findViewById(R.id.et_password);
        Button loginBtn= findViewById(R.id.btn_login);
        Button registerBtn= findViewById(R.id.btn_register);

        // Initialize SharedPreferences acting as a local mock database
        SharedPreferences prefs= getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Handle Login Action
        loginBtn.setOnClickListener(v -> {
            String username= usernameInput.getText().toString().trim();
            String password= passwordInput.getText().toString().trim();

            // 1. Input Validation: Check for empty fields
            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in your data", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Authentication Check against stored preferences
            String storedPassword= prefs.getString(username+ "_password", null);
            if (storedPassword!=null && storedPassword.equals(password)) {
                String firstName= prefs.getString(username+ "_firstName", "");
                Toast.makeText(this, "Welcome back, "+ firstName, Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(LoginActivity.this, RecipesActivity.class);
                intent.putExtra("USER_FIRST_NAME", firstName);
                startActivity(intent);
                // Prevent user from going back to the login screen
                finish();
            } else {
                // Failure: Invalid credentials
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Navigation to Registration Screen
        registerBtn.setOnClickListener(v -> {
            Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}