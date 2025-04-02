package com.simats.drscareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class doctor_profile_update extends AppCompatActivity {

    private static final int REQUEST_CODE_OPEN_DOCUMENT = 1;  // Use a unique request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileupdate);  // Replace with the actual layout file name

        // Assuming view44 is a View widget in your layout
        View view44 = findViewById(R.id.view44);
        view44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add an Intent to open mobile storage for accessing files
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");  // You can specify a specific MIME type if needed

                // Start the activity with the intent
                startActivityForResult(intent, REQUEST_CODE_OPEN_DOCUMENT);
            }
        });

    }

    // Override onActivityResult to handle the result from the file picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_OPEN_DOCUMENT && resultCode == RESULT_OK && data != null) {
            // Handle the selected file here
            // The file URI can be obtained from data.getData()
            // Example: Uri selectedFileUri = data.getData();
        }
    }
}
