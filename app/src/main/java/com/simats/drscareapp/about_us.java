package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class about_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Find the ImageButton for navigating back
        ImageButton imageButton = findViewById(R.id.imageButton);

        // Set click listener for navigating back
        imageButton.setOnClickListener(view -> {
            // Handle button click by navigating to the previous page (java_doctor_homepage)
           finish();
        });
    }
}
