package com.simats.drscareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class java_patient_data extends AppCompatActivity {

    private TextView textView63;
    private TextView textView66;
    private TextView textView158;
    private TextView textView167;

    private String patientId; // Declare patientId as a class-level variable

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientdata);

        ImageButton imageButton8 = findViewById(R.id.imageButton74);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity
            }
        });

        textView63 = findViewById(R.id.textView63);
        textView66 = findViewById(R.id.textView66);
        textView158 = findViewById(R.id.textView158);
        textView167 = findViewById(R.id.textView167);

        Intent intent = getIntent();
        if (intent != null) {
            String patientName = intent.getStringExtra("patient_name");
            String patientAge = intent.getStringExtra("patient_age");
            String patientMobileNumber = intent.getStringExtra("patient_gender");
            patientId = intent.getStringExtra("patient_id"); // Assign patientId here

            textView63.setText(patientName);
            textView66.setText(patientAge);
            textView158.setText(patientMobileNumber);
            textView167.setText(patientId); // Set patientId to TextView167
        }

        // Set click listeners
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(java_doctor_homepage.class);
            }
        });

        findViewById(R.id.view13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(java_patient_data.this, patient_details.class);
                intent.putExtra("patient_id", patientId);
                startActivity(intent);
            }
        });


        findViewById(R.id.view14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(assessment_of_patients.class);
            }
        });

//        findViewById(R.id.view15).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(java_patient_data.this, "First You have to Do the Assessment Before getting the results", Toast.LENGTH_SHORT).show();
//                navigateToActivity(score_results.class);
//            }
//        });
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(java_patient_data.this, destinationClass);
        startActivity(intent);
    }
}
