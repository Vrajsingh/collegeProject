package com.iiitr.shubham.prescriptions;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.iiitr.shubham.prescriptions.Adapter.PrescriptionAdapter;
import com.iiitr.shubham.prescriptions.Classes.PrescriptionObject;
import com.iiitr.shubham.prescriptions.Volley.Requests;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PrescriptionListActivity extends AppCompatActivity {

    private RecyclerView prescriptionRecyclerView;
    private PrescriptionAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PrescriptionObject> mList;
    private String FETCH_URL = "";
    private String AUTH_STR = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);
        
        linearLayoutManager = new LinearLayoutManager(this);
        mList = new ArrayList<>();

        adapter = new PrescriptionAdapter(mList, new PrescriptionAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(PrescriptionObject v) {
                expandItem(v);
            }
        });
        
        prescriptionRecyclerView = findViewById(R.id.prescription_recyclerview);
        prescriptionRecyclerView.setAdapter(adapter);
        prescriptionRecyclerView.setLayoutManager(linearLayoutManager);
        getData();
    }

    private void expandItem(PrescriptionObject v) {

        Intent intent = new Intent(PrescriptionListActivity.this,PrescriptionViewerActivity.class);
        intent.putExtra("Name",v.getName());
        intent.putExtra("Family Code Number",v.getAge());
        intent.putExtra("Permanent Address",v.getDiagnosis());
        intent.putExtra("Present Address",v.getAdvice());
        intent.putExtra("Pin code",v.getSymptoms());
        intent.putExtra("Mobile No",v.getPrescription());
        intent.putExtra("Age",v.getGender());
        intent.putExtra("Religion",v.getGender());
        intent.putExtra("Varna",v.getGender());
        intent.putExtra("Qualification",v.getGender());
        intent.putExtra("Ritwik Name",v.getGender());
        startActivity(intent);

    }

    private void getData()
    {
        Requests requests = new Requests(this);
        HashMap headers=new HashMap();
        headers.put("Authorization",AUTH_STR);
        headers.put("Content-Type","application/json");
        requests.getRequest(FETCH_URL, headers, new Requests.JSONArrayResponseListener() {
            @Override
            public void onResponse(JSONArray array) {
                for(int i = 0; i<array.length();i++)
                {
                    try {

                        PrescriptionObject object = new Gson().fromJson(array.get(i).toString(),PrescriptionObject.class);
                        mList.add(object);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
