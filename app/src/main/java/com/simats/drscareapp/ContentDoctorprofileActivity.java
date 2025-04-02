package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ContentDoctorprofileActivity extends AppCompatActivity {

    EditText nameEditText, mobileNumberEditText, emailEditText, specificationEditText, educationEditText, locationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_doctorprofile);

        nameEditText = findViewById(R.id.editTextName);
        mobileNumberEditText = findViewById(R.id.editTextMobileNumber);
        emailEditText = findViewById(R.id.editTextEmailAddress);
        specificationEditText = findViewById(R.id.editTextSpecification);
        educationEditText = findViewById(R.id.editTextEducation);
        locationEditText = findViewById(R.id.editTextLocation);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void submitForm() {
        String name = nameEditText.getText().toString();
        String mobileNumber = mobileNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String specification = specificationEditText.getText().toString();
        String education = educationEditText.getText().toString();
        String location = locationEditText.getText().toString();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    // URL of your PHP script
                    URL url = new URL(IPv4Connection.getBaseUrl()+"doctor_profile.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Set request method
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // Construct data to send
                    String postData = "doctor_name=" + URLEncoder.encode(name, "UTF-8")
                            + "&doctor_mobilenumber=" + URLEncoder.encode(mobileNumber, "UTF-8")
                            + "&doctor_email=" + URLEncoder.encode(email, "UTF-8")
                            + "&doctor_specification=" + URLEncoder.encode(specification, "UTF-8")
                            + "&doctor_education=" + URLEncoder.encode(education, "UTF-8")
                            + "&doctor_location=" + URLEncoder.encode(location, "UTF-8");

                    // Send data
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    // Get response
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();

                    // Return response
                    return response.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                // Handle the response from the server
                if (result.equals("Record updated successfully")) {
                    Toast.makeText(ContentDoctorprofileActivity.this, "Data updated successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ContentDoctorprofileActivity.this, "Error: " + result, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
