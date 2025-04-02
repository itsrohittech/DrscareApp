package com.simats.drscareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class delete_confirmation extends AppCompatActivity {

    private RelativeLayout overlayLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the main content view
        setContentView(R.layout.deleteconfirmation);

        // Inflate the overlay layout
        LayoutInflater inflater = LayoutInflater.from(this);
        overlayLayout = (RelativeLayout) inflater.inflate(R.layout.deleteconfirmation, null);

        // Add the overlay to the WindowManager
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                android.graphics.PixelFormat.TRANSLUCENT);

        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(overlayLayout, params);

        // Handle button click to navigate to DrscareDProfileActivity
        Button button14 = overlayLayout.findViewById(R.id.button14);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DrscareDProfileActivity
                Intent intent = new Intent(delete_confirmation.this, doctor_profile.class);
                startActivity(intent);
            }
        });

    }

    // Remove the overlay when the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (overlayLayout != null) {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(overlayLayout);
        }
    }
}
