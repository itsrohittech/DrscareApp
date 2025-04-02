package com.simats.drscareapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class doctor_profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    EditText doctorNameEditText, doctorEmailEditText, doctorSpecificationEditText,
            doctorMobileNumberEditText, doctorQualificationEditText, doctorExperienceEditText,
            doctorEducationEditText, doctorLocationEditText, doctorPasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorprofileedit);

        doctorNameEditText = findViewById(R.id.editTextName);
        doctorEmailEditText = findViewById(R.id.editTextEmail);
        doctorSpecificationEditText = findViewById(R.id.editTextSpecification);
        doctorMobileNumberEditText = findViewById(R.id.editTextMobileNumber);
        doctorQualificationEditText = findViewById(R.id.editText_Qualification);
        doctorExperienceEditText = findViewById(R.id.editTextExperience);
        doctorEducationEditText = findViewById(R.id.editTextEducation);
        doctorLocationEditText = findViewById(R.id.editTextLocation);
        doctorPasswordEditText = findViewById(R.id.editTextPassword);






        // Fetch and show doctor details
        fetchAndShowDoctorDetails();

        // Button to store data in the database and navigate to java_doctor_homepage
        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(view -> {
            // Validate the input fields
            if (validateInputFields()) {
                // Store the data in the database if validation passes
                storeDataInDatabase();
                // Navigate to java_doctor_homepage
                navigateToDoctorHomepage();
            }
        });



//        // ImageButton to open doctor_profile_update as an overlay with slide-up animation
//        ImageButton imageButtonProfile = findViewById(R.id.imagebutton5);
//        if (imageButtonProfile != null) {
//            imageButtonProfile.setOnClickListener(view -> openDoctorProfileUpdateWithAnimation());
//        }

        ImageButton imageButton11 = findViewById(R.id.imageButton11);
        imageButton11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Define your back button behavior here
                finish(); // Close the current activity
            }
        });



    }

    private boolean validateInputFields() {
        if (doctorNameEditText.getText().toString().trim().isEmpty()) {
            doctorNameEditText.setError("Please enter your name");
            doctorNameEditText.requestFocus();
            return false;
        }

        if (doctorEmailEditText.getText().toString().trim().isEmpty()) {
            doctorEmailEditText.setError("Please enter your email");
            doctorEmailEditText.requestFocus();
            return false;
        }

        if (doctorSpecificationEditText.getText().toString().trim().isEmpty()) {
            doctorSpecificationEditText.setError("Please enter your specialization");
            doctorSpecificationEditText.requestFocus();
            return false;
        }

        if (doctorMobileNumberEditText.getText().toString().trim().isEmpty()) {
            doctorMobileNumberEditText.setError("Please enter your mobile number");
            doctorMobileNumberEditText.requestFocus();
            return false;
        }

        if (doctorQualificationEditText.getText().toString().trim().isEmpty()) {
            doctorQualificationEditText.setError("Please enter your qualification");
            doctorQualificationEditText.requestFocus();
            return false;
        }

        if (doctorExperienceEditText.getText().toString().trim().isEmpty()) {
            doctorExperienceEditText.setError("Please enter your experience");
            doctorExperienceEditText.requestFocus();
            return false;
        }

        if (doctorEducationEditText.getText().toString().trim().isEmpty()) {
            doctorEducationEditText.setError("Please enter your education");
            doctorEducationEditText.requestFocus();
            return false;
        }

        if (doctorLocationEditText.getText().toString().trim().isEmpty()) {
            doctorLocationEditText.setError("Please enter your location");
            doctorLocationEditText.requestFocus();
            return false;
        }

        if (doctorPasswordEditText.getText().toString().trim().isEmpty()) {
            doctorPasswordEditText.setError("Please enter your password");
            doctorPasswordEditText.requestFocus();
            return false;
        }

        // All fields are valid
        return true;
    }



    // AsyncTask to fetch doctor details from the database
    private class GetDoctorDetailsTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(IPv4Connection.getBaseUrl() + "doctor_details_get.php");
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
                // Parse the JSON response as an array
                JSONArray jsonArray = new JSONArray(result);
                if (jsonArray.length() > 0) {
                    // Assuming only one doctor is returned
                    JSONObject doctorData = jsonArray.getJSONObject(0);
                    String doctorName = doctorData.getString("doctor_name");
                    String doctorEmail = doctorData.getString("doctor_email");
                    String doctorSpecification = doctorData.getString("doctor_specification");
                    String doctorMobileNumber = doctorData.getString("doctor_mobilenumber");
                    String doctorQualification = doctorData.getString("doctor_qualification");
                    String doctorExperience = doctorData.getString("doctor_experience");
                    String doctorEducation = doctorData.getString("doctor_education");
                    String doctorLocation = doctorData.getString("doctor_location");
                    String doctorPassword = doctorData.getString("password");



                    // Set the text of each EditText field
                    doctorNameEditText.setText(doctorName);
                    doctorEmailEditText.setText(doctorEmail);
                    doctorSpecificationEditText.setText(doctorSpecification);
                    doctorMobileNumberEditText.setText(doctorMobileNumber);
                    doctorQualificationEditText.setText(doctorQualification);
                    doctorExperienceEditText.setText(doctorExperience);
                    doctorEducationEditText.setText(doctorEducation);
                    doctorLocationEditText.setText(doctorLocation);
                    doctorPasswordEditText.setText(doctorPassword);

                } else {
                    // No data found
                    Toast.makeText(doctor_profile.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(doctor_profile.this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to fetch and show doctor details
    private void fetchAndShowDoctorDetails() {
        GetDoctorDetailsTask task = new GetDoctorDetailsTask();
        task.execute();
    }

    // Method to store data in the database
    private void storeDataInDatabase() {
        String doctorName = doctorNameEditText.getText().toString();
        String doctorEmail = doctorEmailEditText.getText().toString();
        String doctorSpecification = doctorSpecificationEditText.getText().toString();
        String doctorMobileNumber = doctorMobileNumberEditText.getText().toString();
        String doctorQualification = doctorQualificationEditText.getText().toString();
        String doctorExperience = doctorExperienceEditText.getText().toString();
        String doctorEducation = doctorEducationEditText.getText().toString();
        String doctorLocation = doctorLocationEditText.getText().toString();
        String doctorPassword = doctorPasswordEditText.getText().toString();





        // Create a new instance of AsyncHttpTask and execute it
        AsyncHttpTask task = new AsyncHttpTask();
        task.execute(doctorName, doctorEmail, doctorSpecification, doctorMobileNumber,
                doctorQualification, doctorExperience, doctorEducation, doctorLocation, doctorPassword);
    }

    // Method to navigate to java_doctor_homepage
    private void navigateToDoctorHomepage() {
        Intent intent = new Intent(doctor_profile.this, java_doctor_homepage.class);
        startActivity(intent);
    }

//    // Method to open doctor_profile_update as an overlay with slide-up animation
//    private void openDoctorProfileUpdateWithAnimation() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
//
//        // Apply slide-up animation using ViewPropertyAnimator
//        ImageButton imageButtonProfile = findViewById(R.id.imagebutton5);
//        if (imageButtonProfile != null) {
//            imageButtonProfile.animate().translationY(0).setDuration(500);
//        }
//    }

    // Handle the result of image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                // Set the bitmap as the profile photo
                // For example, if you have an ImageView with id imageViewProfilePhoto:
                // ImageView imageViewProfilePhoto = findViewById(R.id.imageViewProfilePhoto);
                // imageViewProfilePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // AsyncTask to perform the HTTP POST request to the PHP script
    private class AsyncHttpTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String doctorName = params[0];
            String doctorEmail = params[1];
            String doctorSpecification = params[2];
            String doctorMobileNumber = params[3];
            String doctorQualification = params[4];
            String doctorExperience = params[5];
            String doctorEducation = params[6];
            String doctorLocation = params[7];
            String doctorPassword = params[8];



            try {
                // URL of your PHP script
                URL url = new URL(IPv4Connection.getBaseUrl() + "doctor_profile.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set request method
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Construct data to send
                String postData = "doctor_name=" + URLEncoder.encode(doctorName, "UTF-8") +
                        "&doctor_email=" + URLEncoder.encode(doctorEmail, "UTF-8") +
                        "&doctor_specification=" + URLEncoder.encode(doctorSpecification, "UTF-8") +
                        "&doctor_mobilenumber=" + URLEncoder.encode(doctorMobileNumber, "UTF-8") +
                        "&doctor_qualification=" + URLEncoder.encode(doctorQualification, "UTF-8") +
                        "&doctor_experience=" + URLEncoder.encode(doctorExperience, "UTF-8") +
                        "&doctor_education=" + URLEncoder.encode(doctorEducation, "UTF-8") +
                        "&doctor_location=" + URLEncoder.encode(doctorLocation, "UTF-8") +
                        "&password=" + URLEncoder.encode(doctorPassword, "UTF-8");

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

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Show toast message with server response
            Toast.makeText(doctor_profile.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
