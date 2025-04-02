package com.simats.drscareapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PatientProfileShowActivity extends AppCompatActivity {

    private TextView textViewName, textViewChangePassword, textViewEmail, textViewMobileNumber, textViewReenterPassword;
    private String patientMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_profile_show);
        textViewName = findViewById(R.id.editTextName);
        textViewMobileNumber = findViewById(R.id.editTextMobileNumber);
        textViewEmail = findViewById(R.id.editTextEmail);
        textViewChangePassword = findViewById(R.id.editTextChangePassword);
        textViewReenterPassword = findViewById(R.id.editTextReenterPassword);



        Button button = findViewById(R.id.button16);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfileShowActivity.this, patient_profile.class);
                intent.putExtra("patient_mobile_number", patientMobileNumber);
                startActivity(intent);
            }
        });

        ImageButton imageButton9 = findViewById(R.id.imageButton9);
        imageButton9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish(); // Close the current activity
            }
        });

        // Get the mobile number from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("patient_mobile_number")) {
            patientMobileNumber = intent.getStringExtra("patient_mobile_number");

            // Execute AsyncTask with patientMobileNumber
            new GetPatientDetailsTask().execute(patientMobileNumber);
        }
    }

    private class GetPatientDetailsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                String patientMobileNumber = strings[0];
                URL url = new URL(IPv4Connection.getBaseUrl() + "patient_mobile_get.php?patient_mobile_number=" + patientMobileNumber);
                Log.d("URL", url.toString()); // Log the URL being used for the request
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                // Read the response
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("JSON Response", result); // Log the JSON response received

            if (result == null || result.trim().isEmpty()) {
                Toast.makeText(PatientProfileShowActivity.this, "No response from server", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONArray jsonArray = new JSONArray(result);
                if (jsonArray.length() > 0) {
                    JSONObject patientData = jsonArray.getJSONObject(0);
                    String patientName = patientData.getString("patient_name");
                    String patientMobileNumber = patientData.getString("patient_mobile_number");
                    String patientEmail = patientData.getString("patient_email");
                    String patientPassword = ""; // Assuming you don't want to show password fields in the UI
                    String patientReEnterPassword = ""; // Assuming you don't want to show password fields in the UI

                    // Set the text of each TextView field
                    textViewName.setText(patientName);
                    textViewMobileNumber.setText(patientMobileNumber);
                    textViewEmail.setText(patientEmail);
                    textViewChangePassword.setText(patientPassword); // Assuming you want to show password fields
                    textViewReenterPassword.setText(patientReEnterPassword);
                } else {
                    // No data found
                    Toast.makeText(PatientProfileShowActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(PatientProfileShowActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

