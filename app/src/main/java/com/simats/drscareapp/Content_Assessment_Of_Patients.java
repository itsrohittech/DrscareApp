package com.simats.drscareapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Content_Assessment_Of_Patients extends AppCompatActivity {

    private Button submitButton;

    private RadioGroup genderGroup;
    private RadioGroup ageGroup;
    private RadioGroup RIFPainGroup;
    private RadioGroup AnorexiaGroup;
    private RadioGroup MigratoryPainGroup;
    private RadioGroup NauseaVomitingGroup;
    private RadioGroup LessThan24HoursGroup;
    private RadioGroup MoreThan24HoursGroup;
    private RadioGroup FeverGroup;
    private RadioGroup McburneyGroup;
    private RadioGroup reboundGroup;
    private RadioGroup gaurdingGroup;
    private RadioGroup rovsingGroup;
    private RadioGroup leucoytosisGroup;
    private RadioGroup usgProbeGroup;
    private RadioGroup presenceofAppendicolithGroup;
    private RadioGroup diameterofAppendixGroup;

    private String value;
    String doc;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_assessment_of_patients);
        Intent intent1 = getIntent();
        doc = intent1.getStringExtra("did");

        genderGroup = findViewById(R.id.rbg1);
        ageGroup = findViewById(R.id.rbg2);
        RIFPainGroup = findViewById(R.id.rbg3);
        AnorexiaGroup = findViewById(R.id.rbg4);
        MigratoryPainGroup = findViewById(R.id.rbg5);
        NauseaVomitingGroup = findViewById(R.id.rbg6);
        LessThan24HoursGroup = findViewById(R.id.rbg7);

        MoreThan24HoursGroup = findViewById(R.id.rbg8);
        FeverGroup = findViewById(R.id.rbg9);
        McburneyGroup = findViewById(R.id.rbg10);
        reboundGroup = findViewById(R.id.rbg11);
        gaurdingGroup = findViewById(R.id.rbg12);
        rovsingGroup = findViewById(R.id.rbg13);
        leucoytosisGroup = findViewById(R.id.rbg14);
        usgProbeGroup = findViewById(R.id.rbg15);
        presenceofAppendicolithGroup = findViewById(R.id.rbg16);
        diameterofAppendixGroup = findViewById(R.id.rbg17);

        Intent intent = getIntent();
        if (intent != null) {
            value = intent.getStringExtra("id");
        }

        submitButton = findViewById(R.id.button7);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields()) {
                    Toast.makeText(Content_Assessment_Of_Patients.this, "Please select an option in each category", Toast.LENGTH_SHORT).show();
                    return;
                }

                int totalScore = 0;
                totalScore = calculateGender();
                totalScore += calculateAge();
                totalScore += calculateRIF();
                totalScore += calculateAnorexia();
                totalScore += calculateMigratoryPain();
                totalScore += calculateNauseaVomiting();
                totalScore += calculateLessThen24Hours();
                totalScore += calculateMoreThen24Hours();
                totalScore += calculateFever();
                totalScore += calculateMcburney();
                totalScore += calculateRebound();
                totalScore += calculateGaurding();
                totalScore += calculateRovsing();
                totalScore += calculateLeucoytosis();
                totalScore += calculateUsgProbe();
                totalScore += calculatePresenceOfAppendicolith();
                totalScore += calculateDiameterOfAppendix();


                sendSurveyDataToServer(totalScore, value);

                Toast.makeText(Content_Assessment_Of_Patients.this, "Total Score: " + totalScore, Toast.LENGTH_SHORT).show();
                Intent ultrasoundIntent = new Intent(Content_Assessment_Of_Patients.this, score_results.class);
                ultrasoundIntent.putExtra("totalScore", totalScore);
                ultrasoundIntent.putExtra("value", value);
                ultrasoundIntent.putExtra("did", doc);
                startActivity(ultrasoundIntent);
            }
        });

        setRadioButtonClickListeners();
    }


    private int calculateGender() {
        int weightScore = 0;
        int checkedRadioButtonId = genderGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb1) {
            weightScore = 2;
        } else if (checkedRadioButtonId == R.id.rb2) {
            weightScore = 1;
        }
        return weightScore;
    }


    private int calculateAge() {
        int weightScore = 0;
        int checkedRadioButtonId = ageGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb3) {
            weightScore = 2;
        } else if (checkedRadioButtonId == R.id.rb4) {
            weightScore = 1;
        }
        return weightScore;
    }


    private int calculateRIF() {
        int weightScore = 0;
        int checkedRadioButtonId = RIFPainGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb5) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb6) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateAnorexia() {
        int weightScore = 0;
        int checkedRadioButtonId = AnorexiaGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb7) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb8) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateMigratoryPain() {
        int weightScore = 0;
        int checkedRadioButtonId = MigratoryPainGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb9) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb10) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateNauseaVomiting() {
        int weightScore = 0;
        int checkedRadioButtonId = NauseaVomitingGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb11) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb12) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateLessThen24Hours() {
        int weightScore = 0;
        int checkedRadioButtonId = LessThan24HoursGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb13) {
            weightScore = 2;
        } else if (checkedRadioButtonId == R.id.rb14) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateMoreThen24Hours() {
        int weightScore = 0;
        int checkedRadioButtonId = MoreThan24HoursGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb15) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb16) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateFever() {
        int weightScore = 0;
        int checkedRadioButtonId = FeverGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb17) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb18) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateMcburney() {
        int weightScore = 0;
        int checkedRadioButtonId = McburneyGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb19) {
            weightScore = 4;
        } else if (checkedRadioButtonId == R.id.rb20) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateRebound() {
        int weightScore = 0;
        int checkedRadioButtonId = reboundGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb21) {
            weightScore = 2;
        } else if (checkedRadioButtonId == R.id.rb22) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateGaurding() {
        int weightScore = 0;
        int checkedRadioButtonId = gaurdingGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb23) {
            weightScore = 3;
        } else if (checkedRadioButtonId == R.id.rb24) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateRovsing() {
        int weightScore = 0;
        int checkedRadioButtonId = rovsingGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb25) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb26) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateLeucoytosis() {
        int weightScore = 0;
        int checkedRadioButtonId = leucoytosisGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb27) {
            weightScore = 2;
        } else if (checkedRadioButtonId == R.id.rb28) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateUsgProbe() {
        int weightScore = 0;
        int checkedRadioButtonId = usgProbeGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb29) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb30) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculatePresenceOfAppendicolith() {
        int weightScore = 0;
        int checkedRadioButtonId = presenceofAppendicolithGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb31) {
            weightScore = 1;
        } else if (checkedRadioButtonId == R.id.rb32) {
            weightScore = 0;
        }
        return weightScore;
    }


    private int calculateDiameterOfAppendix() {
        int weightScore = 0;
        int checkedRadioButtonId = diameterofAppendixGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and update the weightScore accordingly
        if (checkedRadioButtonId == R.id.rb33) {
            weightScore = 2;
        } else if (checkedRadioButtonId == R.id.rb34) {
            weightScore = 0;
        }
        return weightScore;
    }

    private boolean validateFields() {
        // Check if all radio groups have a selection
        if (genderGroup.getCheckedRadioButtonId() == -1 ||
                ageGroup.getCheckedRadioButtonId() == -1 ||
                RIFPainGroup.getCheckedRadioButtonId() == -1 ||
                AnorexiaGroup.getCheckedRadioButtonId() == -1 ||
                MigratoryPainGroup.getCheckedRadioButtonId() == -1 ||
                NauseaVomitingGroup.getCheckedRadioButtonId() == -1 ||
                LessThan24HoursGroup.getCheckedRadioButtonId() == -1 ||
                MoreThan24HoursGroup.getCheckedRadioButtonId() == -1 ||
                FeverGroup.getCheckedRadioButtonId() == -1 ||
                McburneyGroup.getCheckedRadioButtonId() == -1 ||
                reboundGroup.getCheckedRadioButtonId() == -1 ||
                gaurdingGroup.getCheckedRadioButtonId() == -1 ||
                rovsingGroup.getCheckedRadioButtonId() == -1 ||
                usgProbeGroup.getCheckedRadioButtonId() == -1 ||
                presenceofAppendicolithGroup.getCheckedRadioButtonId() == -1 ||
                diameterofAppendixGroup.getCheckedRadioButtonId() == -1 ) {
            return false; // At least one radio group has no selection
        }

        return true; // All fields are filled
    }

    private void setRadioButtonClickListeners() {
        RadioGroup[] radioGroups = new RadioGroup[0];
        for (final RadioGroup radioGroup : radioGroups) {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    if (radioButton != null) {
                        String scoreText = radioButton.getText().toString();
                        try {
                            int score = Integer.parseInt(scoreText);
                            // Do something with the checked score if needed
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private void sendSurveyDataToServer(final int totalScore, final String value) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Replace "YOUR_PHP_ENDPOINT" with the actual endpoint of your PHP file
        String url =IPv4Connection.getBaseUrl()+"process_checkbox.php";

        // Define the parameters to be sent to the server
        Map<String, String> params = new HashMap<>();
        params.put("totalScore", String.valueOf(totalScore));
        params.put("value", value);

        // Create the request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            // Process the jsonResponse if needed
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Handle success or failure accordingly
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("VolleyError", "Error during request: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}