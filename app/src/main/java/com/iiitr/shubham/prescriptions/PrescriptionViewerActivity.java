package com.iiitr.shubham.prescriptions;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class PrescriptionViewerActivity extends AppCompatActivity {


    private TextView nameTV,fcnTV,perAddTV,preAddTV,pinTV,mobTV,ageTV,religionTV,varnaTV,qualTV,ritNameTV;
    private LinearLayout linearLayout;

    private String Name,Family Code Number,Permanent Address,Present Address,Pin code,Mobile No,Age,Religion,Varna,Qualification,Ritwik Name;
    private ArrayList<String> prescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_viewer);

        nameTV = findViewById(R.id.tv_name);
        fcnTV = findViewById(R.id.tv_fcn);
        perAddTV = findViewById(R.id.tv_perAdd);
        preAddTV = findViewById(R.id.tv_preAdd);
        pinTV = findViewById(R.id.tv_pin);
        mobTV = findViewById(R.id.tv_mob);
        ageTV = findViewById(R.id.tv_age);
        religionTV = findViewById(R.id.tv_religion);
        varnaTV = findViewById(R.id.tv_varna);
        qualTV = findViewById(R.id.tv_qual);
        ritNameTV = findViewById(R.id.tv_ritName);
        
        
        linearLayout = findViewById(R.id.prescriptions_ll);

        Name = getIntent().getStringExtra("Name"); //name
        Family Code Number = getIntent().getStringExtra("Family Code Number"); //gender
        Permanent Address = getIntent().getStringExtra("Permanent Address"); //age
        Present Address = getIntent().getStringExtra("Present Address"); //diagnosis
        Pin code = getIntent().getStringExtra("Pin code"); //advice
        Mobile No = getIntent().getStringExtra("Mobile No"); //symptoms
        Age = getIntent().getStringExtra("Age");
        Religion = getIntent().getStringExtra("Religion");
        Varna = getIntent().getStringExtra("Varna");
        Qualification = getIntent().getStringExtra("Qualification");
        Ritwik Name = getIntent().getStringExtra("Ritwik Name");

        prescriptions = getIntent().getStringArrayListExtra("prescriptions");

        nameTV.setText(Name);
        fcnTV.setText(Family Code Number);
        perAddTV.setText(Permanent Address);
        preAddTV.setText(Present Address);
        pinTV.setText(Pin code);
        mobTV.setText(Mobile No);
        ageTV.setText(Age);
        religionTV.setText(Religion);
        varnaTV.setText(Varna);
        qualTV.setText(Qualification);
        ritNameTV.setText(Ritwik Name);

        for(int i=0;i<prescriptions.size();i++)
        {
            TextView textView = new TextView(this);
            int a = (int)(getResources().getDimension(R.dimen.fab_margin)*10);
            textView.setPadding(a,a,a,a);
            linearLayout.addView(textView);
        }

    }
}
