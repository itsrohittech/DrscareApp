package com.simats.drscareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class View_score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drscare_view_score);

        // Find the ImageButton
        ImageButton imageButton12 = findViewById(R.id.imageButton12);

        // Set an OnClickListener for the ImageButton
        imageButton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to navigate to java_doctor_homepage
                finish();
            }
        });

        // Add your View_score activity initialization code here

    }
}
