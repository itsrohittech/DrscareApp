package com.simats.drscareapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Patient_data extends Fragment {

    // ... Other code ...

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_data, container, false);

        // Find the ImageButton for navigating to java_doctor_homepage

        return view;
    }
}
