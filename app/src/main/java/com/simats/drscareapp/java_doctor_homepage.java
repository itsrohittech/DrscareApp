package com.simats.drscareapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class java_doctor_homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MyAdapter adapter;
    private DrawerLayout drawerLayout;

    List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorhomepage);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview_patients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, items);
        recyclerView.setAdapter(adapter);

        fetchDataFromDatabase();

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        // ImageButton for navigating to Drs-careDProfileActivity
//        ImageButton d_profile = findViewById(R.id.d_profile);
//        d_profile.setOnClickListener(view -> {
//            Log.d("DoctorHomepage", "dProfileButton clicked");
//            Intent intent = new Intent(java_doctor_homepage.this, DoctorProfileShowActivity.class);
//            startActivity(intent);
//        });

        // Additional ImageButton
        ImageButton dMenuButton = findViewById(R.id.d_menu);
        dMenuButton.setOnClickListener(view -> {
            Log.d("DoctorHomepage", "dMenuButton clicked");
            drawerLayout.openDrawer(GravityCompat.START); // Open the drawer
        });

        // ImageButton for navigating to add_patients activity
        ImageButton overlayAddPatient = findViewById(R.id.overlay_add_patient);
        overlayAddPatient.setOnClickListener(view -> {
            Log.d("DoctorHomepage", "overlayAddPatient clicked");
            startActivity(new Intent(java_doctor_homepage.this, add_patients.class));
        });

        // Set up TabLayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        startActivity(new Intent(java_doctor_homepage.this, View_score.class));
                        break;
                    case 2:
                        startActivity(new Intent(java_doctor_homepage.this, DoctorProfileShowActivity.class));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Fetch data from database and update the adapter
      //fetchDataAndUpdateAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.recyclerview_patients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, items);
        recyclerView.setAdapter(adapter);
        fetchDataFromDatabase();
    }

    @SuppressLint("StaticFieldLeak")
      private void fetchDataFromDatabase() {
        Log.d("DoctorHomepage", "fetchDataFromDatabase() called");
          AsyncTask<Void, Void, List<Item>> execute = new AsyncTask<Void, Void, List<Item>>() {
              @Override
              protected List<Item> doInBackground(Void... voids) {
                  Log.d("DoctorHomepage", "AsyncTask: doInBackground() called");
                  List<Item> items = new ArrayList<>();
                  try {
                      URL url = new URL(IPv4Connection.getBaseUrl() + "getdetails_patients.php");
                      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                      InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                      StringBuilder result = new StringBuilder();
                      String line;
                      while ((line = reader.readLine()) != null) {
                          result.append(line);
                      }

                      JSONArray jsonArray = new JSONArray(result.toString());
                      for (int i = 0; i < jsonArray.length(); i++) {
                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                          String name = jsonObject.getString("patient_name");
                          int image = R.drawable.patient_profile_pic; // Replace with actual image resource
                          String age = jsonObject.getString("patient_age");
                          String gender = jsonObject.getString("patient_gender");
                          String id = jsonObject.getString("patient_id");
                          items.add(new Item(name, image, age, gender, id));
                      }

                      urlConnection.disconnect();
                  } catch (IOException | JSONException e) {
                      e.printStackTrace();
                  }

                  return items;
              }

              @Override
              public void onPostExecute(List<Item> items) {
                  Log.d("DoctorHomepage", "AsyncTask: onPostExecute() called");
                  adapter.setItems(items); // Update adapter with fetched items
                  adapter.notifyDataSetChanged();
                  Log.d("DoctorHomepage", "After notifyDataSetChanged()");// Notify adapter that data set has changed
              }
          }.execute();
      }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            startActivity(new Intent(java_doctor_homepage.this, about_us.class));
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(java_doctor_homepage.this, java_choose_acc_type.class));
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
