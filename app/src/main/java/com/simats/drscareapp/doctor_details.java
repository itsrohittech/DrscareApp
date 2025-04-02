package com.simats.drscareapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class doctor_details extends AppCompatActivity {

    TextView textView45, textView46, textView47, doctorIdTextView, doctorNameTextView, doctorSpecTextView, doctorExpTextView, doctorEduTextView, doctorLocationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctordetails);

        // Get the TextViews
        textView45 = findViewById(R.id.textView45);
        textView46 = findViewById(R.id.textView46);
        textView47 = findViewById(R.id.textView47);
        doctorIdTextView = findViewById(R.id.textView51);
        doctorNameTextView = findViewById(R.id.textView53);
        doctorSpecTextView = findViewById(R.id.textView55);
        doctorExpTextView = findViewById(R.id.textView57);
        doctorEduTextView = findViewById(R.id.textView60);
        doctorLocationTextView = findViewById(R.id.textView62);

        // Declare the Button
        Button button5 = findViewById(R.id.button5);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doctor_details.this, patient_homepage.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton8 = findViewById(R.id.imageButton6);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define your back button behavior here
                finish(); // Close the current activity
            }
        });


        // Fetch doctor details asynchronously
        new FetchDoctorDetailsTask().execute();
    }

    private class FetchDoctorDetailsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";

            try {
                URL url = new URL(IPv4Connection.getBaseUrl()+"doctor_details.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String doctorId = jsonObject.getString("doctor_id");
                String doctorName = jsonObject.getString("doctor_name");
                String doctorSpec = jsonObject.getString("doctor_specification");
                String doctorExp = jsonObject.getString("doctor_experience");
                String doctorEdu = jsonObject.getString("doctor_education");
                String doctorQual = jsonObject.getString("doctor_qualification");
                String doctorLoc = jsonObject.getString("doctor_location");

                // Set the doctor's details in the TextViews
                textView45.setText(doctorName);
                textView46.setText(doctorQual);
                textView47.setText(doctorLoc);
                doctorIdTextView.setText(doctorId);
                doctorNameTextView.setText(doctorName);
                doctorSpecTextView.setText(doctorSpec);
                doctorExpTextView.setText(doctorExp);
                doctorEduTextView.setText(doctorEdu);
                doctorLocationTextView.setText(doctorLoc);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
