package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject; // Add this import statement

import java.util.HashMap;
import java.util.Map;

public class java_doctor_login extends AppCompatActivity {

    private static final String URL_INSERT_DOCTOR = IPv4Connection.getBaseUrl()+"doctor_login.php"; // Change this to your actual server address

    EditText editTextDoctorId;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        editTextDoctorId = findViewById(R.id.editTextNumberSigned);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        TextView textView6 = findViewById(R.id.textView6);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredDoctorId = editTextDoctorId.getText().toString();
                String enteredPassword = editTextPassword.getText().toString();
                if(enteredDoctorId.isEmpty() || enteredPassword.isEmpty()){
                    Toast.makeText(java_doctor_login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                insertDoctorData(enteredDoctorId, enteredPassword);
            }
        });

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(java_doctor_login.this, "Contact Administrator!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertDoctorData(final String doctorId, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT_DOCTOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse the response to a JSON object
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");

                            switch (status) {
                                case "success":
                                    Toast.makeText(java_doctor_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(java_doctor_login.this, java_doctor_homepage.class);
                                    startActivity(intent);
                                    break;
                                case "incorrect":
                                    Toast.makeText(java_doctor_login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    break;
                                case "not_found":
                                    Toast.makeText(java_doctor_login.this, "Doctor ID Not Found", Toast.LENGTH_SHORT).show();
                                    break;
                                case "invalid_request":
                                    Toast.makeText(java_doctor_login.this, "Invalid Request", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(java_doctor_login.this, "Unknown Response", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            Toast.makeText(java_doctor_login.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(java_doctor_login.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("doctor_id", doctorId);
                params.put("password", password);
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
