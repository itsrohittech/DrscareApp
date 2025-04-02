package com.simats.drscareapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Trying_page_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamburgermenu);

        // Find the btnAboutUs button and set its click listener
        Button btnAboutUs = findViewById(R.id.btnAboutUs);
        btnAboutUs.setOnClickListener(v -> {
            // Handle btnAboutUs click by navigating to about_us activity
            startActivity(new Intent(Trying_page_Activity.this, about_us.class));
        });

        // Find the btnGetHelp button and set its click listener
        Button btnGetHelp = findViewById(R.id.btnGetHelp);
        btnGetHelp.setOnClickListener(v -> {
            // Handle btnGetHelp click by showing a message
            Toast.makeText(Trying_page_Activity.this, "Contact administrator!!", Toast.LENGTH_SHORT).show();
        });

        // Find the btnLogout button and set its click listener
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            // Handle btnLogout click by navigating to choose_account_type activity
            startActivity(new Intent(Trying_page_Activity.this, java_choose_acc_type.class));
        });

    }
}
