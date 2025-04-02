package com.simats.drscareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class add_patients extends AppCompatActivity {

    EditText patientNameEditText, patientAgeEditText,patientGenderEditText, patientEmailEditText, patientMobileEditText, patientIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatients);

        patientNameEditText = findViewById(R.id.editTextText5);
        patientAgeEditText = findViewById(R.id.editTextText6);
        patientEmailEditText = findViewById(R.id.editTextText7);
        patientMobileEditText = findViewById(R.id.editTextText8);
        patientIdEditText = findViewById(R.id.editTextNumber);
        patientGenderEditText = findViewById(R.id.editTextGender);
        Button submitButton = findViewById(R.id.button10);
        ImageButton back_to_homepage = findViewById(R.id.back_To_homepage);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
                finish();
            }
        });

        back_to_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_patients.this, java_doctor_homepage.class);
                startActivity(intent);
            }
        });

    }

    private void submitForm() {
        String patientName = patientNameEditText.getText().toString().trim();
        String patientAge = patientAgeEditText.getText().toString().trim();
        String patientEmail = patientEmailEditText.getText().toString().trim();
        String patientMobileNumber = patientMobileEditText.getText().toString().trim();
        String patientId = patientIdEditText.getText().toString().trim();
        String patientGender = patientGenderEditText.getText().toString().trim();
        if(patientName.isEmpty() || patientAge.isEmpty() || patientEmail.isEmpty() || patientMobileNumber.isEmpty() || patientId.isEmpty() || patientGender.isEmpty()){
            Toast.makeText(add_patients.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(IPv4Connection.getBaseUrl()+"addpatients.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    String postData = "patient_name=" + URLEncoder.encode(patientName, "UTF-8") +
                            "&patient_age=" + URLEncoder.encode(patientAge, "UTF-8") +
                            "&patient_gender=" + URLEncoder.encode(patientGender, "UTF-8") +
                            "&patient_email=" + URLEncoder.encode(patientEmail, "UTF-8") +
                            "&patient_mobile_number=" + URLEncoder.encode(patientMobileNumber, "UTF-8") +
                            "&patient_id=" + URLEncoder.encode(patientId, "UTF-8");

                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(add_patients.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}