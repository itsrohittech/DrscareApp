package com.simats.drscareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DoctorProfileShowActivity extends AppCompatActivity {

    TextView doctorNameEditText, doctorEmailEditText, doctorSpecificationEditText,
            doctorMobileNumberEditText, doctorQualificationEditText, doctorExperienceEditText,
            doctorEducationEditText, doctorLocationEditText, doctorPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_profile_show);

        doctorNameEditText = findViewById(R.id.editTextName);
        doctorEmailEditText = findViewById(R.id.editTextEmail);
        doctorSpecificationEditText = findViewById(R.id.editTextSpecification);
        doctorMobileNumberEditText = findViewById(R.id.editTextMobileNumber);
        doctorQualificationEditText = findViewById(R.id.editText_Qualification);
        doctorExperienceEditText = findViewById(R.id.editTextExperience);
        doctorEducationEditText = findViewById(R.id.editTextEducation);
        doctorLocationEditText = findViewById(R.id.editTextLocation);
        doctorPasswordEditText = findViewById(R.id.editTextPassword);

        Button button = findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorProfileShowActivity.this, doctor_profile.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton8 = findViewById(R.id.imageButton11);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define your back button behavior here
                finish(); // Close the current activity
            }
        });

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = IPv4Connection.getBaseUrl() + "doctor_details_get.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                doctorNameEditText.setText(jsonObject.getString("doctor_name"));
                                doctorEmailEditText.setText(jsonObject.getString("doctor_email"));
                                doctorSpecificationEditText.setText(jsonObject.getString("doctor_specification"));
                                doctorMobileNumberEditText.setText(jsonObject.getString("doctor_mobilenumber"));
                                doctorQualificationEditText.setText(jsonObject.getString("doctor_qualification"));
                                doctorExperienceEditText.setText(jsonObject.getString("doctor_experience"));
                                doctorEducationEditText.setText(jsonObject.getString("doctor_education"));
                                doctorLocationEditText.setText(jsonObject.getString("doctor_location"));
                                doctorPasswordEditText.setText(jsonObject.getString("password"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
