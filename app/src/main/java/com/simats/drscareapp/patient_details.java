package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class patient_details extends AppCompatActivity {

    private String editTextText5;
    private String editTextText6;
    private String editTextText7;
    private String editTextText8;
    private String editTextText9;

    private EditText editTextName, editTextAge, editTextMobileNumber, editTextId, editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientdetails);


        // Display the fetched data
        editTextName = findViewById(R.id.editTextText5);
        editTextAge = findViewById(R.id.editTextText6);
        editTextEmail = findViewById(R.id.editTextText7);
        editTextMobileNumber = findViewById(R.id.editTextText8);
        editTextId = findViewById(R.id.editTextText9);



        // Fetch patient details from the server

        Intent intent = getIntent();
        if (intent != null) {
            String patientId = intent.getStringExtra("patient_id");
            new GetPatientDetailsTask(patientId).execute();
        }

        // Find the Button with id button2
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(view -> {
            // Get the updated form data
            String updatedPatientName = editTextName.getText().toString();
            String updatedPatientAge = editTextAge.getText().toString();
            String updatedPatientEmail = editTextEmail.getText().toString();
            String updatedPatientMobileNumber = editTextMobileNumber.getText().toString();
            String updatedPatientId = editTextId.getText().toString();

            // Make HTTP POST request to update patient details
            new UpdatePatientDetailsTask().execute(updatedPatientName, updatedPatientAge, updatedPatientEmail, updatedPatientMobileNumber, updatedPatientId);
        });

        // Find the ImageButton for navigating to java_patient_data
        ImageButton imageButton4 = findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(view -> {
            // Handle button click by navigating to java_patient_data activity
            finish();
        });
    }

    private class GetPatientDetailsTask extends AsyncTask<Void, Void, String> {
        private final String patientId;

        public GetPatientDetailsTask(String patientId) {
            this.patientId = patientId;
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                // Define the URL for fetching patient details
                URL url = new URL(IPv4Connection.getBaseUrl() + "patient_edit_details.php?patient_id=" + patientId);
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
                Log.d("PatientDetailsTask", "Response: " + stringBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject patientData = jsonArray.getJSONObject(0);

                // Get the values from the JSON object
                String patientName = patientData.getString("patient_name");
                String patientAge = patientData.getString("patient_age");
                String patientEmail = patientData.getString("patient_email");
                String patientMobileNumber = patientData.getString("patient_mobile_number");

                // Set the values in the EditText fields
                editTextName.setText(patientName);
                editTextAge.setText(patientAge);
                editTextEmail.setText(patientEmail);
                editTextMobileNumber.setText(patientMobileNumber);

                // Set patient_id in editTextText9
                editTextId.setText(patientId);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(patient_details.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
            }
        }









    public void execute(String editTextText9) {
        }
    }

    private class UpdatePatientDetailsTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                // Define the URL of the PHP script
                URL url = new URL(IPv4Connection.getBaseUrl() + "patient_update_details.php");

                // Create the connection
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
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(postDataBytes);
                outputStream.flush();
                outputStream.close();

                // Get the response from the server
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Request was successful
                    runOnUiThread(() -> Toast.makeText(patient_details.this, "Record updated successfully.", Toast.LENGTH_SHORT).show());
                } else {
                    // Request failed
                    runOnUiThread(() -> Toast.makeText(patient_details.this, "Error updating record.", Toast.LENGTH_SHORT).show());
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

