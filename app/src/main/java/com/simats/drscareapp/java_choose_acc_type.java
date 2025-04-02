package com.simats.drscareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class java_choose_acc_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        // Set up the OnClickListener for the doctorButton
        ImageButton doctorButton = findViewById(R.id.doctorbutton);
        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle doctorButton click, navigate to the doctor login activity
                Intent intent = new Intent(java_choose_acc_type.this, java_doctor_login.class);
                startActivity(intent);
            }
        });

        // Set up the OnClickListener for the patientButton
        ImageButton patientButton = findViewById(R.id.patientbutton);
        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle patientButton click, navigate to the patient login activity
                Intent intent = new Intent(java_choose_acc_type.this, patient_login.class);
                startActivity(intent);
            }
        });

    }
}
