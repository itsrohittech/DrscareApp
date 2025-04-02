package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class score_results extends AppCompatActivity {

    TextView textView26;
    TextView textView30;
    TextView textView129;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreresults);

        textView26 = findViewById(R.id.textView26);
        textView30 = findViewById(R.id.textView30);
        textView129 = findViewById(R.id.textView129);
        seekBar = findViewById(R.id.slider);

        // Get the ScoredValue from the intent extras
        int scoredValue = getIntent().getIntExtra("ScoredValue", 0);

        // Set the ScoredValue to textView26
        textView26.setText(String.valueOf(scoredValue));

        // Get the TotalScore from the intent extras
        int totalScore = getIntent().getIntExtra("TotalScore", 0);

        // Set the TotalScore to textView30
        textView30.setText(String.valueOf(totalScore));

        // Update SeekBar position based on the total score
        updateSeekBar(totalScore, scoredValue);

        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        Button button13 = findViewById(R.id.button13);

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the assessment_of_patients activity
                finish();
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send data to the database
                new SendDataToDatabaseTask().execute(scoredValue, totalScore, textView129.getText().toString());
            }
        });
    }

    // AsyncTask to send data to the database
    private class SendDataToDatabaseTask extends AsyncTask<Object, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Object... params) {
            int scoredValue = (int) params[0];
            int totalScore = (int) params[1];
            String outcome = (String) params[2];

            try {
                URL url = new URL(IPv4Connection.getBaseUrl()+"fetch_total.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                // Create the query string
                String data = URLEncoder.encode("scoredValue", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(scoredValue), "UTF-8");
                data += "&" + URLEncoder.encode("totalScore", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(totalScore), "UTF-8");
                data += "&" + URLEncoder.encode("outcome", "UTF-8") + "=" + URLEncoder.encode(outcome, "UTF-8");

                // Write the data to the connection
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                // Get the response
                InputStream is = new BufferedInputStream(conn.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String response = br.readLine();
                br.close();

                conn.disconnect();

                // Return true if the operation was successful
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Return false if the operation failed
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                // Show a toast message "Success"
                Toast.makeText(score_results.this, "Success", Toast.LENGTH_SHORT).show();
                // Create an Intent to start the java_patient_data activity
                Intent intent = new Intent(score_results.this, java_doctor_homepage.class);
                startActivity(intent);
            } else {
                // Show a toast message "Failure"
                Toast.makeText(score_results.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Update SeekBar position based on the total score
    private void updateSeekBar(int totalScore, int scoredValue) {
        if (totalScore == 30) {
            if (scoredValue >= 0 && scoredValue <= 10) {
                seekBar.setProgress(0);
                textView129.setText("Wait & Watch");
            } else if (scoredValue >= 11 && scoredValue <= 15) {
                seekBar.setProgress(12);
                textView129.setText("Plan for Conservative Management");
            } else if (scoredValue >= 16 && scoredValue <= 21) {
                seekBar.setProgress(50);
                textView129.setText("Need for Observation and Further Evaluation");
            } else {
                seekBar.setProgress(100);
                textView129.setText("Need for Immediate Intervention");
            }
        } else if (totalScore == 26) {
            if (scoredValue >= 0 && scoredValue <= 8) {
                seekBar.setProgress(0);
                textView129.setText("Wait & Watch");
            } else if (scoredValue >= 9 && scoredValue <= 13) {
                seekBar.setProgress(12);
                textView129.setText("Plan for Conservative Management");
            } else if (scoredValue >= 14 && scoredValue <= 19) {
                seekBar.setProgress(50);
                textView129.setText("Need for Observation and Further Evaluation");
            } else {
                seekBar.setProgress(100);
                textView129.setText("Need for Immediate Intervention");
            }
        }
    }
}
