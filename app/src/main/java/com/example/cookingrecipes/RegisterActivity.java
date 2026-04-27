package com.example.cookingrecipes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    // Define UI components as private to enforce encapsulation
    private EditText firstNameInput, lastNameInput, usernameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        firstNameInput= findViewById(R.id.et_first_name);
        lastNameInput= findViewById(R.id.et_last_name);
        usernameInput= findViewById(R.id.et_username);
        passwordInput= findViewById(R.id.et_password);
        Button registerBtn= findViewById(R.id.btn_register);

        // Handle Registration Action
        registerBtn.setOnClickListener(v -> {
            String firstName= firstNameInput.getText().toString().trim();
            String lastName= lastNameInput.getText().toString().trim();
            String username= usernameInput.getText().toString().trim();
            String password= passwordInput.getText().toString().trim();

            // 1. Input Validation: Ensure no fields are left blank
            if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                return;     // Stop execution to prevent empty data saving
            }

            // 2. Save User Data (Mock Database using SharedPreferences)
            SharedPreferences prefs= getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor= prefs.edit();
            editor.putString(username+ "_firstName", firstName);
            editor.putString(username+ "_lastName", lastName);
            editor.putString(username+ "_password", password);
            // Using apply() instead of commit() for asynchronous, non-blocking saving
            editor.apply();

            // 3. Provide feedback and navigate back to Login
            Toast.makeText(this, "Registration successful! Welcome "+ firstName+ " "+ lastName, Toast.LENGTH_SHORT).show();

            Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            // Finish this activity so the user doesn't return to the form via the back button
            finish();
        });
    }
}