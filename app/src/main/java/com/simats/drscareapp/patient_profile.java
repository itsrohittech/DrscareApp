package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class patient_profile extends AppCompatActivity {
    private String patientMobileNumber;

    private EditText editTextName, editTextChangePassword, editTextEmail, editTextMobileNumber, editTextReenterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientprofileedit);

        editTextName = findViewById(R.id.editTextName);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextChangePassword = findViewById(R.id.editTextChangePassword);
        editTextReenterPassword = findViewById(R.id.editTextReenterPassword);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("patient_mobile_number")) {
            patientMobileNumber = intent.getStringExtra("patient_mobile_number");
        }

        // Fetch patient details from the server
        new GetPatientDetailsTask().execute(patientMobileNumber);

        // Declare the Button for POST request
        Button button16 = findViewById(R.id.button16);

        // Set click listener for button16 for POST request
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated form data
                String updatedPatientName = editTextName.getText().toString();
                String updatedPatientMobileNumber = editTextMobileNumber.getText().toString();
                String updatedPatientEmail = editTextEmail.getText().toString();
                String updatedPatientPassword = editTextChangePassword.getText().toString();
                String updatedPatientReEnterPassword = editTextReenterPassword.getText().toString();
                if(updatedPatientName.isEmpty() || updatedPatientMobileNumber.isEmpty() || updatedPatientEmail.isEmpty() || updatedPatientPassword.isEmpty() || updatedPatientReEnterPassword.isEmpty()){
                    Toast.makeText(patient_profile.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make HTTP POST request to update patient details
                new UpdatePatientDetailsTask().execute(updatedPatientName, updatedPatientPassword, updatedPatientEmail, updatedPatientMobileNumber, updatedPatientReEnterPassword);
            }
        });

        ImageButton imageButton8 = findViewById(R.id.imageButton10);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define your back button behavior here
                finish(); // Close the current activity
            }
        });
    }

    private class GetPatientDetailsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
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
            try {
                JSONArray jsonArray = new JSONArray(result);
                if (jsonArray.length() > 0) {
                    JSONObject patientData = jsonArray.getJSONObject(0);
                    String patientName = patientData.getString("patient_name");
                    String patientMobileNumber = patientData.getString("patient_mobile_number");
                    String patientEmail = patientData.getString("patient_email");
                    String patientPassword = patientData.getString("patient_password");
                    String patientReEnterPassword = patientData.getString("patient_reenter_password");

                    // Set the text of each TextView field
                    editTextName.setText(patientName);
                    editTextMobileNumber.setText(patientMobileNumber);
                    editTextEmail.setText(patientEmail);
                    editTextChangePassword.setText(patientPassword); // Assuming you want to show password fields
                    editTextReenterPassword.setText(patientReEnterPassword);
                } else {
                    // No data found
                    Toast.makeText(patient_profile.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(patient_profile.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }




        private class UpdatePatientDetailsTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(IPv4Connection.getBaseUrl() + "patient_profiles.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                // Create the POST data
                String postData = "patient_name=" + params[0] +
                        "&patient_age=" + params[1] +
                        "&patient_email=" + params[2] +
                        "&patient_mobile_number=" + params[3] +
                        "&patient_id=" + params[4];
                byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

                // Write the POST data to the connection
                urlConnection.getOutputStream().write(postDataBytes);

                // Get the response from the server
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Request was successful
                    runOnUiThread(() -> Toast.makeText(patient_profile.this, "Record updated successfully.", Toast.LENGTH_SHORT).show());
                } else {
                    // Request failed
                    runOnUiThread(() -> Toast.makeText(patient_profile.this, "Error updating record.", Toast.LENGTH_SHORT).show());
                }

                // Disconnect the connection
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
