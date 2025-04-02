package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class patient_homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private String mobileNumber; // Declare mobileNumber here to make it accessible globally

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patienthomepage); // Replace with the actual layout file name

        drawerLayout = findViewById(R.id.drawer_layout);

        // Declare the Buttons
        Button button4 = findViewById(R.id.button4);
        ImageButton p_profile = findViewById(R.id.p_profile);
        ImageButton p_menu = findViewById(R.id.p_menu); // Assuming you hav"mobile_numbere an ImageButton with id p_menu

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("patient_mobile_number")) {
            mobileNumber = intent.getStringExtra("patient_mobile_number");
            // Set the mobile number to textView169
            TextView textView169 = findViewById(R.id.textView169);
            textView169.setText(mobileNumber);
        }

        // Set click listener for button4
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the 'doctor_details' activity when button4 is clicked
                Intent intent = new Intent(patient_homepage.this, doctor_details.class);
                startActivity(intent);
            }
        });

        // Set click listener for patient_profile_button
        p_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the 'patient_profile' activity when patient_profile_button is clicked
                Intent intent = new Intent(patient_homepage.this, PatientProfileShowActivity.class);
                intent.putExtra("patient_mobile_number", mobileNumber);
                startActivity(intent);
            }
        });

        // Set click listener for p_menu
        p_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the navigation drawer
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Initialize TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Set a TabLayout.OnTabSelectedListener to handle tab clicks
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle tab selection, navigate to corresponding activity
                switch (tab.getPosition()) {
                    case 1:
                        // Start the 'patient_details' activity when Tab 2 is selected
                        Intent intent2 = new Intent(patient_homepage.this, score_results_for_patients.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        // Start the 'about_for_patients' activity when Tab 3 is selected
                        Intent intent3 = new Intent(patient_homepage.this, about_for_patients.class);
                        startActivity(intent3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselection if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselection if needed
            }
        });

        // Set up navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            // Start the 'about_us' activity when nav_about is selected
            startActivity(new Intent(patient_homepage.this,AboutUsPatientsActivity.class));
        } else if (id == R.id.nav_gethelp) {
            // Show a toast message "Contact Administrator" when nav_gethelp is selected
            Toast.makeText(this, "Contact Doctor For Queries", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            // Start the 'java_choose_acc_type' activity when nav_logout is selected
            startActivity(new Intent(patient_homepage.this, java_choose_acc_type.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
