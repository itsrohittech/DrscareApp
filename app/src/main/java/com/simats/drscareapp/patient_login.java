package com.simats.drscareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class patient_login extends AppCompatActivity {

    private static final String EXPECTED_PASSWORD = "welcome";
    private static final String SERVER_URL = IPv4Connection.getBaseUrl()+"patient_logins.php"; // Replace with your actual server URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page4); // Replace with the actual layout file name

        EditText p_mobile_number = findViewById(R.id.p_mobile_number);
        EditText p_password = findViewById(R.id.p_password);
        Button button_pl = findViewById(R.id.button_pl);
        TextView textView29 = findViewById(R.id.textView29);

        button_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = p_mobile_number.getText().toString();
                String password = p_password.getText().toString();
                if(mobileNumber.isEmpty() || password.isEmpty()){
                    Toast.makeText(patient_login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make a POST request to the server to check if the mobile number exists
                StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String status = jsonResponse.getString("status");
                                    String message = jsonResponse.getString("message");

                                    if (status.equals("success")) {
                                        // Mobile number found, validate the password
                                        if (password.equals(EXPECTED_PASSWORD)) {
                                            // Handle correct credentials, navigate to the next activity
                                            Intent intent = new Intent(patient_login.this, patient_homepage.class);
                                            intent.putExtra("patient_mobile_number", mobileNumber);
                                            startActivity(intent);
                                        } else {
                                            // Display error message for invalid password
                                            Toast.makeText(patient_login.this, "Invalid password", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Display error message for mobile number not found
                                        Toast.makeText(patient_login.this, "Mobile number not found", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    // Display error message for JSON parsing error
                                    Toast.makeText(patient_login.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Display error message for network error
                                Toast.makeText(patient_login.this, "Network error", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("mobile_number", mobileNumber);
                        return params;
                    }
                };

                // Add the request to the RequestQueue
                Volley.newRequestQueue(patient_login.this).add(stringRequest);
            }
        });

        textView29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display "Contact Administrator" message
                Toast.makeText(patient_login.this, "Contact Administrator", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
