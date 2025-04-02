package com.simats.drscareapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class addpatientoverlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatientoverlay);

        // Create an Intent to navigate to add_patients activity
        Intent intent = new Intent(addpatientoverlay.this, add_patients.class);
        startActivity(intent);

    }
}
