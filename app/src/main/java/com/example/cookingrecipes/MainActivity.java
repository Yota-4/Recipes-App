package com.example.cookingrecipes;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the welcome button
        Button welcomeBtn = findViewById(R.id.btn_welcome);
        // Set a click listener to navigate to the Authentication screen
        welcomeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            // Finish this activity to remove it from the back stack
            finish();
        });
    }
}