package com.iiitr.shubham.prescriptions;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class PrescriptionViewerActivity extends AppCompatActivity {


    private TextView ageTV,nameTV,genderTV,diagnosisTV,adviceTV,symptomsTV;
    private LinearLayout linearLayout;

    private String name,age,gender,diagnosis,advice,symptoms;
    private ArrayList<String> prescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_viewer);

        ageTV = findViewById(R.id.tv_age);
        nameTV = findViewById(R.id.tv_name);
        genderTV = findViewById(R.id.tv_gender);
        diagnosisTV = findViewById(R.id.tv_diagnosis);
        adviceTV = findViewById(R.id.tv_advice);
        symptomsTV = findViewById(R.id.tv_symptoms);

        linearLayout = findViewById(R.id.prescriptions_ll);

        name = getIntent().getStringExtra("name");
        gender = getIntent().getStringExtra("gender");
        age = getIntent().getStringExtra("age");
        diagnosis = getIntent().getStringExtra("diagnosis");
        advice = getIntent().getStringExtra("advice");
        symptoms = getIntent().getStringExtra("symptoms");

        prescriptions = getIntent().getStringArrayListExtra("prescriptions");

        nameTV.setText(name);
        ageTV.setText(age);
        genderTV.setText(gender);
        adviceTV.setText(advice);
        diagnosisTV.setText(diagnosis);
        symptomsTV.setText(symptoms);

        for(int i=0;i<prescriptions.size();i++)
        {
            TextView textView = new TextView(this);
            int a = (int)(getResources().getDimension(R.dimen.fab_margin)*10);
            textView.setPadding(a,a,a,a);
            linearLayout.addView(textView);
        }

    }
}
