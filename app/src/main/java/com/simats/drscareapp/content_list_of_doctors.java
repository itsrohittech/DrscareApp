package com.simats.drscareapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class content_list_of_doctors extends AppCompatActivity {

    TextView textView12, textView15, textView21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list_of_doctors);

        // Get the TextViews
        textView12 = findViewById(R.id.textView12);
        textView15 = findViewById(R.id.textView15);
        textView21 = findViewById(R.id.textView21);

        // Declare the Button
        Button button4 = findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(content_list_of_doctors.this, doctor_details.class);
                startActivity(intent);
            }
        });

        // Fetch doctor details asynchronously
        new FetchDoctorDetailsTask().execute();
    }

    private class FetchDoctorDetailsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            JSONObject doctorObject = new JSONObject();
            try {
                doctorObject.put("doctor_name", "Dr.Prasna Sai");
                doctorObject.put("doctor_education", "Saveetha Medical College");
                doctorObject.put("doctor_location", "Chennai");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray doctorsArray = new JSONArray();
            doctorsArray.put(doctorObject);
            return doctorsArray.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray doctorsArray = new JSONArray(result);
                if (doctorsArray.length() > 0) {
                    JSONObject doctorObject = doctorsArray.getJSONObject(0); // Assuming you want to display details of the first doctor
                    String doctorName = doctorObject.getString("doctor_name");
                    String doctorEducation = doctorObject.getString("doctor_education");
                    String doctorLocation = doctorObject.getString("doctor_location");

                    // Set the doctor's details in the TextViews
                    textView12.setText(doctorName);
                    textView15.setText(doctorEducation);
                    textView21.setText(doctorLocation);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON Error", "Error parsing JSON: " + e.getMessage());
            }
        }
    }
}
