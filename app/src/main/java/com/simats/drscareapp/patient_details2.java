package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class patient_details2 extends AppCompatActivity {

    private EditText editTextText5, editTextText6, editTextText7, editTextText8, editTextText10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientdetails2);

        // Initialize EditText fields
        editTextText5 = findViewById(R.id.editTextText5);
        editTextText6 = findViewById(R.id.editTextText6);
        editTextText7 = findViewById(R.id.editTextText7);
        editTextText8 = findViewById(R.id.editTextText8);
        editTextText10 = findViewById(R.id.editTextText10);

        // Fetch data from database
        new FetchDataFromDatabaseTask().execute();

        // Assuming you have an ImageButton with id imageButton5
        ImageButton imageButton5 = findViewById(R.id.imageButton5);

        // Set click listener for imageButton5
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the 'patient_homepage' activity when imageButton5 is clicked
                finish();
            }
        });

        // Declare the Button
        Button button3 = findViewById(R.id.button3);

        // Set click listener for button3
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update data in the database
                String patientName = editTextText5.getText().toString();
                String patientAge = editTextText6.getText().toString();
                String patientEmail = editTextText7.getText().toString();
                String patientMobileNumber = editTextText8.getText().toString();
                String pastMedicalHistory = editTextText10.getText().toString();

                new UpdateDataInDatabaseTask().execute(patientName, patientAge, patientEmail, patientMobileNumber, pastMedicalHistory);
            }
        });

    }

    // Method to fetch data from the database
    private class FetchDataFromDatabaseTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(IPv4Connection.getBaseUrl()+"patient_edit_details.php");
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
            try {
                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(result);
                // Assuming your JSON response is an array of objects with keys like "patient_name", "patient_age", etc.
                // You would need to extract the values for each key and update the corresponding EditText fields
                if (jsonArray.length() > 0) {
                    JSONObject patientData = jsonArray.getJSONObject(0); // Assuming only one patient is returned
                    String patientName = patientData.getString("patient_name");
                    String patientAge = patientData.getString("patient_age");
                    String patientEmail = patientData.getString("patient_email");
                    String patientMobileNumber = patientData.getString("patient_mobile_number");
                    String pastMedicalHistory = patientData.getString("past_medical_history");

                    // Set the text of each EditText field
                    editTextText5.setText(patientName);
                    editTextText6.setText(patientAge);
                    editTextText7.setText(patientEmail);
                    editTextText8.setText(patientMobileNumber);
                    editTextText10.setText(pastMedicalHistory);
                } else {
                    // No data found
                    Toast.makeText(patient_details2.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(patient_details2.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to update data in the database
    private class UpdateDataInDatabaseTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(IPv4Connection.getBaseUrl()+"patient_update_details.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");

                // Create JSON object with updated patient details
                JSONObject jsonParams = new JSONObject();
                jsonParams.put("patient_name", params[0]);
                jsonParams.put("patient_age", params[1]);
                jsonParams.put("patient_email", params[2]);
                jsonParams.put("patient_mobile_number", params[3]);
                jsonParams.put("past_medical_history", params[4]);

                // Write JSON data to output stream
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(jsonParams.toString());
                writer.flush();
                writer.close();
                outputStream.close();

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
            // Handle the JSON response here
            if (result.equals("success")) {
                Toast.makeText(patient_details2.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(patient_details2.this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
