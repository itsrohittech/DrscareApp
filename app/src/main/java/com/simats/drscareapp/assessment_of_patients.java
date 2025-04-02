package com.simats.drscareapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
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

public class assessment_of_patients extends AppCompatActivity {

    private Button submitButton;
    private Button clearRadioGroups;

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
    private CheckBox checkbox55; // Added checkbox55

    private String value;
    String doc;
    private SharedPreferences sharedPreferences;

    private int TotalScore = 26; // Default TotalScore value is 26

    Button saveButton;







    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duplicate);

        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define your back button behavior here
                finish(); // Close the current activity
            }
        });

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
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
        checkbox55 = findViewById(R.id.checkBox55); // Initialize checkbox55

        // Set the saved values to the radio buttons
        setRadioGroupValues(genderGroup, "gender");
        setRadioGroupValues(ageGroup, "age");
        setRadioGroupValues(RIFPainGroup, "RIFPain");
        setRadioGroupValues(AnorexiaGroup, "Anorexia");
        setRadioGroupValues(MigratoryPainGroup, "MigratoryPain");
        setRadioGroupValues(NauseaVomitingGroup, "NauseaVomiting");
        setRadioGroupValues(LessThan24HoursGroup, "LessThan24Hours");
        setRadioGroupValues(MoreThan24HoursGroup, "MoreThan24Hours");
        setRadioGroupValues(FeverGroup, "Fever");
        setRadioGroupValues(McburneyGroup, "Mcburney");
        setRadioGroupValues(reboundGroup, "rebound");
        setRadioGroupValues(gaurdingGroup, "gaurding");
        setRadioGroupValues(rovsingGroup, "rovsing");
        setRadioGroupValues(leucoytosisGroup, "leucoytosis");
        setRadioGroupValues(usgProbeGroup, "usgProbe");
        setRadioGroupValues(presenceofAppendicolithGroup, "presenceofAppendicolith");
        setRadioGroupValues(diameterofAppendixGroup, "diameterofAppendix");



        Intent intent = getIntent();
        if (intent != null) {
            value = intent.getStringExtra("id");
        }

        saveButton = findViewById(R.id.button8);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Save the selected values to SharedPreferences
                saveRadioGroupValues(genderGroup, "gender");
                saveRadioGroupValues(ageGroup, "age");
                saveRadioGroupValues(RIFPainGroup, "RIFPain");
                saveRadioGroupValues(AnorexiaGroup, "Anorexia");
                saveRadioGroupValues(MigratoryPainGroup, "MigratoryPain");
                saveRadioGroupValues(NauseaVomitingGroup, "NauseaVomiting");
                saveRadioGroupValues(LessThan24HoursGroup, "LessThan24Hours");
                saveRadioGroupValues(MoreThan24HoursGroup, "MoreThan24Hours");
                saveRadioGroupValues(FeverGroup, "Fever");
                saveRadioGroupValues(McburneyGroup, "Mcburney");
                saveRadioGroupValues(reboundGroup, "rebound");
                saveRadioGroupValues(gaurdingGroup, "gaurding");
                saveRadioGroupValues(rovsingGroup, "rovsing");
                saveRadioGroupValues(leucoytosisGroup, "leucoytosis");
                saveRadioGroupValues(usgProbeGroup, "usgProbe");
                saveRadioGroupValues(presenceofAppendicolithGroup, "presenceofAppendicolith");
                saveRadioGroupValues(diameterofAppendixGroup, "diameterofAppendix");

                Toast.makeText(assessment_of_patients.this, "Values saved", Toast.LENGTH_SHORT).show();
            }
        });

        checkbox55.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TotalScore = isChecked ? 30 : 26; // Update TotalScore based on checkbox55 state
            }
        });

        Button clearButton = findViewById(R.id.button9); // Assuming you have a button with id clearButton
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioGroups();
            }
        });



        submitButton = findViewById(R.id.button7);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFields()) {
                    Toast.makeText(assessment_of_patients.this, "Please select an option in each category", Toast.LENGTH_SHORT).show();
                    return;
                }

                int ScoredValue = 0;
                ScoredValue = calculateGender();
                ScoredValue += calculateAge();
                ScoredValue += calculateRIF();
                ScoredValue += calculateAnorexia();
                ScoredValue += calculateMigratoryPain();
                ScoredValue += calculateNauseaVomiting();
                ScoredValue += calculateLessThen24Hours();
                ScoredValue += calculateMoreThen24Hours();
                ScoredValue += calculateFever();
                ScoredValue += calculateMcburney();
                ScoredValue += calculateRebound();
                ScoredValue += calculateGaurding();
                ScoredValue += calculateRovsing();
                ScoredValue += calculateLeucoytosis();
                ScoredValue += calculateUsgProbe();
                ScoredValue += calculatePresenceOfAppendicolith();
                ScoredValue += calculateDiameterOfAppendix();


                sendSurveyDataToServer(ScoredValue, value);

                Toast.makeText(assessment_of_patients.this, "Score: " + ScoredValue, Toast.LENGTH_SHORT).show();
                Intent ultrasoundIntent = new Intent(assessment_of_patients.this, score_results.class);
                ultrasoundIntent.putExtra("ScoredValue", ScoredValue);
                ultrasoundIntent.putExtra("TotalScore", TotalScore);
                ultrasoundIntent.putExtra("value", value);
                ultrasoundIntent.putExtra("did", doc);
                startActivity(ultrasoundIntent);
            }
        });

        setRadioButtonClickListeners();
    }

    private void clearRadioGroups() {
        genderGroup.clearCheck();
        ageGroup.clearCheck();
        RIFPainGroup.clearCheck();
        AnorexiaGroup.clearCheck();
        MigratoryPainGroup.clearCheck();
        NauseaVomitingGroup.clearCheck();
        LessThan24HoursGroup.clearCheck();
        MoreThan24HoursGroup.clearCheck();
        FeverGroup.clearCheck();
        McburneyGroup.clearCheck();
        reboundGroup.clearCheck();
        gaurdingGroup.clearCheck();
        rovsingGroup.clearCheck();
        leucoytosisGroup.clearCheck();
        usgProbeGroup.clearCheck();
        presenceofAppendicolithGroup.clearCheck();
        diameterofAppendixGroup.clearCheck();
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
        return genderGroup.getCheckedRadioButtonId() != -1 &&
                ageGroup.getCheckedRadioButtonId() != -1 &&
                RIFPainGroup.getCheckedRadioButtonId() != -1 &&
                AnorexiaGroup.getCheckedRadioButtonId() != -1 &&
                MigratoryPainGroup.getCheckedRadioButtonId() != -1 &&
                NauseaVomitingGroup.getCheckedRadioButtonId() != -1 &&
                LessThan24HoursGroup.getCheckedRadioButtonId() != -1 &&
                MoreThan24HoursGroup.getCheckedRadioButtonId() != -1 &&
                FeverGroup.getCheckedRadioButtonId() != -1 &&
                McburneyGroup.getCheckedRadioButtonId() != -1 &&
                reboundGroup.getCheckedRadioButtonId() != -1 &&
                gaurdingGroup.getCheckedRadioButtonId() != -1 &&
                rovsingGroup.getCheckedRadioButtonId() != -1 &&
                leucoytosisGroup.getCheckedRadioButtonId() != -1 ;

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

    private void setRadioGroupValues(RadioGroup radioGroup, String key) {
        int selectedId = sharedPreferences.getInt(key, -1);
        if (selectedId != -1) {
            RadioButton radioButton = radioGroup.findViewById(selectedId);
            if (radioButton != null) {
                radioButton.setChecked(true);
            }
        }
    }

    private void saveRadioGroupValues(RadioGroup radioGroup, String key) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, selectedId);
        editor.apply();
    }


    private void sendSurveyDataToServer(final int ScoredValue, final String value) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Replace "YOUR_PHP_ENDPOINT" with the actual endpoint of your PHP file
        String url =IPv4Connection.getBaseUrl()+"process_checkbox.php";

        // Define the parameters to be sent to the server
        Map<String, String> params = new HashMap<>();
        params.put("ScoredValue", String.valueOf(ScoredValue));
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
