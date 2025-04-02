package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class about_for_patients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutforpatients);

        // Assuming you have an ImageButton with id imagebutton7
        ImageButton imageButton7 = findViewById(R.id.imageButton7);
        Button button22 = findViewById(R.id.button22);

        // Set click listener for imagebutton7
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the 'patient_homepage' activity when imagebutton7 is clicked
                finish();
            }
        });

        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the 'patient_homepage' activity when imagebutton7 is clicked
                Intent intent = new Intent(about_for_patients.this, score_results_for_patients.class);
                startActivity(intent);
            }
        });
    }
}
