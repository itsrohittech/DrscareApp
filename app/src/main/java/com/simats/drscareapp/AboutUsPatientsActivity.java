package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AboutUsPatientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_patients);

        // Find the ImageButton
        ImageButton backButton = findViewById(R.id.imageButton);

        // Set click listener for the ImageButton
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the patient_homepage activity
                Intent intent = new Intent(AboutUsPatientsActivity.this, patient_homepage.class);
                startActivity(intent);
            }
        });
    }
}
