package com.iiitr.shubham.prescriptions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.iiitr.shubham.prescriptions.Intents.IntentNotFoundError;
import com.iiitr.shubham.prescriptions.Intents.Intents;
import com.iiitr.shubham.prescriptions.Utils.EmailUtil;
import com.iiitr.shubham.prescriptions.Utils.TTSClass;
import com.iiitr.shubham.prescriptions.Volley.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import ai.kitt.snowboy.audio.RecordingThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int STT_FLAG = 10;
    private Intent recognizerIntent;
    private String LANGUAGE = "en";
    private String DB_URL = "https://i9m7d51vu5.execute-api.us-east-1.amazonaws.com/dev/presc";

    private String QUERY = "";
    private String USER_INTENT = "";
    private EditText nameEditText, adviceEditText, diagnosisEditText, symptomsEditText;
    private LinearLayout prescriptionLinearLayout;
    private int linearLayoutIndex=0;
    private boolean isIntentDetection=false;
    private boolean isAddPrescription=false;
    private boolean isAddSymptoms=false;
    private boolean isAddDiagnosis=false;
    private boolean isAddName=false;
    private boolean isAddAdvice=false;
    private RecordingThread recordingThread=null;
    private boolean isDetectionOn=false;
    private HashMap sendMap;
    private EditText genderEditText;
    private EditText ageEditText;
    private boolean isAddAge=false;
    private boolean isAddGender=false;
    private Button addPrescriptionButton;
    private String AUTH_STR="eyJraWQiOiJadXIwNG11YzBkOVU4ZHA1NUsxbThhVHYwT1NYV2ErVDlZYVhWQWZ2SkJvPSIsImFsZyI6IlJTMjU2In0.eyJhdF9oYXNoIjoiS3BzMHlPT3Jvd1ROTF9MalIwbEdlQSIsInN1YiI6ImE0NDA5NWEzLTVhOWEtNGI0MC1iOTgzLTVlZjg3Y2QzOTJkZCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfeGJvYzhKV1l0IiwicGhvbmVfbnVtYmVyX3ZlcmlmaWVkIjp0cnVlLCJjb2duaXRvOnVzZXJuYW1lIjoiYTQ0MDk1YTMtNWE5YS00YjQwLWI5ODMtNWVmODdjZDM5MmRkIiwiYXVkIjoiNzJhdTIxa2czajVrZ2hvbG5uYjM1NWxyMDkiLCJldmVudF9pZCI6IjM3Mzc1MmY4LTA2ZGQtNDY1Yy05YmYzLTA5NGU2YmM3ZGQyNCIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTc5MzQ1NDYyLCJwaG9uZV9udW1iZXIiOiIrOTE5MzA0MjI1NzUyIiwiZXhwIjoxNTc5MzQ5MDYyLCJpYXQiOjE1NzkzNDU0NjIsImVtYWlsIjoiZGVueXNodWJoYW1AZ21haWwuY29tIn0.czxxCkBYb0khmazOxl-023K7DLAJZ8y5QnEAqtX5G-X2VJyy-NqbAZUrpcJ-aM02lSolUMz4_TdyVF1f39lQ1lYdU1AdvESqjX_7rkKRmMNtS8b9kxAQo3izM2Rj5PuzttNNWHGQ_F4R2_UhhfxB6AEVGZGeAS_4JLELSlG5Td1W_SyxX5V8eF4YFNSVfEcXInrlQCYBP8Ho9i-6vB5dI7jSv7E1Y8F2Qz9f7MJdEY3bHBFcrJNb7sjXBjZ3fTX9oH8G-AvGTWKc5fUetu53kZbWESWUniz82G4cteElnh98a2CwXF8rwd-829c3T7DwTCpBHfyUufe7gnBwiSvjEg";
    private int task=0;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText phoneEditText,emailEditText;
    private String name,age,gender,advice,diagnosis,symptoms,phone,email;
    private ArrayList<String> prescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        addSendButton();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(String.valueOf(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        //initHotword();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isIntentDetection = true;
                initSTT();
            }
        });
    }

    private void addSendButton()
    {
        final FloatingActionButton send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractData(v);
                postData();
                sendMessage();
            }
        });
    }

    private void sendMessage() {

        EmailUtil emailUtil=new EmailUtil(MainActivity.this);
        String message = "Name : "+name+"\nAge : "+age
                +"\nGender : "+gender+"\nAdvice : "+advice
                +"\nSymptoms : "+symptoms+"\nDiagnosis : "+diagnosis
                +"\nPrescription :\n";
        for(int i=0;i<prescription.size();i++)
        {
            String prs = prescription.get(i);
            message += ""+(i+1)+". "+prs+"\n";
        }
        emailUtil.sendEmail(email,message);

    }

    private void postData() {

        Requests requestPost = new Requests(MainActivity.this);
        HashMap headers = new HashMap<>();
        //headers.put("Authorization",AUTH_STR);
        headers.put("Content-Type","application/json");
        //headers.put("description","");
        HashMap map = new HashMap();
        map.put("properties",sendMap);
        requestPost.sendStringRequest(DB_URL,sendMap,headers);

    }

    private void extractData(View v)
    {
        prescription = new ArrayList<>();

        email = emailEditText.getText().toString();
        emailEditText.setText("");

        phone = phoneEditText.getText().toString();
        phoneEditText.setText("");

        name = nameEditText.getText().toString();
        nameEditText.setText("");

        diagnosis = diagnosisEditText.getText().toString();
        diagnosisEditText.setText("");

        symptoms = symptomsEditText.getText().toString();
        symptomsEditText.setText("");

        advice = adviceEditText.getText().toString();
        adviceEditText.setText("");

        age = ageEditText.getText().toString();
        ageEditText.setText("");

        gender = genderEditText.getText().toString();
        genderEditText.setText("");

        while(prescriptionLinearLayout.getChildCount()>0)
        {
            View view = prescriptionLinearLayout.getChildAt(0);
            EditText editText = view.findViewById(R.id.prescription_edittext);
            String prescriptionItem = editText.getText().toString();
            prescription.add(prescriptionItem);
            prescriptionLinearLayout.removeView(view);
        }

        sendMap = new HashMap();
        sendMap.put("id",String.valueOf(System.currentTimeMillis()));
        sendMap.put("email",email);
        sendMap.put("age",age);
        sendMap.put("gender",gender);
        sendMap.put("name",name);
        sendMap.put("symptoms",symptoms);
        sendMap.put("diagnosis",diagnosis);
        sendMap.put("advice",advice);
        sendMap.put("phone",phone);
        sendMap.put("prescription",prescription);
    }

    @Override
    protected void onStart() {
        if(recordingThread !=null && !isDetectionOn) {
            recordingThread.startRecording();
            isDetectionOn = true;
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(recordingThread !=null && isDetectionOn){
            recordingThread.stopRecording();
            isDetectionOn = false;
        }
        super.onStop();
    }

    private void initHotword() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            ai.kitt.snowboy.AppResCopy.copyResFromAssetsToSD(this);

            recordingThread = new ai.kitt.snowboy.audio.RecordingThread(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    ai.kitt.snowboy.MsgEnum message = ai.kitt.snowboy.MsgEnum.getMsgEnum(msg.what);
                    switch(message) {
                        case MSG_ACTIVE:
                            task-=1;
                            nextTask();

                            //HOTWORD DETECTED. NOW DO WHATEVER YOU WANT TO DO HERE
                            break;
                        case MSG_INFO:
                            break;
                        case MSG_VAD_SPEECH:
                            break;
                        case MSG_VAD_NOSPEECH:
                            break;
                        case MSG_ERROR:
                            break;
                        default:
                            super.handleMessage(msg);
                            break;
                    }
                }
            }, new ai.kitt.snowboy.audio.AudioDataSaver());
        }
    }

    private void onDetection() {
        nextTask();
    }

    private void nextTask() {

        task++;
        switch (task)
        {
            case 0 : addName();
                     break;
            case 1:  addAge();
                     break;
            case 2:  addGender();
                     break;
            case 3 : addSymptoms();
                     break;
            case 4 : addAdvice();
                     break;
            case 5 : addDiagnosis();
                     break;
            case 6: addPrescription();
                    break;
        }

    }

    private void initViews() {

        diagnosisEditText = findViewById(R.id.diagnosis_field);
        nameEditText = findViewById(R.id.name_field);
        adviceEditText = findViewById(R.id.advice_field);
        symptomsEditText = findViewById(R.id.symptoms_field);
        ageEditText = findViewById(R.id.age_field);
        genderEditText = findViewById(R.id.gender_field);
        phoneEditText = findViewById(R.id.phone);
        emailEditText = findViewById(R.id.email);

        prescriptionLinearLayout = findViewById(R.id.prescriptions_ll);

        addPrescriptionButton = findViewById(R.id.add_prescription_btn);
        addPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLayoutToLinLayout("");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            mGoogleSignInClient.signOut();
            updateUI();
        }
        else if(id == R.id.hotword)
        {
            initHotword();
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {

        Intent intent = new Intent(MainActivity.this,LogInActivity.class);
        startActivity(intent);
        finish();

    }

    private void initSTT()
    {
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,Locale.ENGLISH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //recognizerIntent.putExtra("android.speech.extra.DICTATION_MODE",true);
        startActivityForResult(recognizerIntent,STT_FLAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK)
        {
            if(requestCode == STT_FLAG)
            {
                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                QUERY = results.get(0);
                checkAction();
            }
        }

    }

    private void checkAction() {

        Handler handler=new Handler();
        if(QUERY.contains(Intents.CANCEL))
        {
            cancelIntents();
        }
        if(isIntentDetection)
        {
            isIntentDetection = false;
            try {
                USER_INTENT = Intents.manualIntentDetection(" "+QUERY.toLowerCase().trim()+" ");
                multiplexIntext();
            } catch (IntentNotFoundError intentNotFoundError) {
                Toast.makeText(this, "Invalid sentence!", Toast.LENGTH_SHORT).show();
                intentNotFoundError.printStackTrace();
            }
        }
        else if(isAddAdvice)
        {
            isAddAdvice = false;
            adviceEditText.setText(QUERY);
            if(diagnosisEditText.getText().toString().equals(""))
            {
                USER_INTENT = Intents.DIAGNOSIS;
                multiplexIntext();
            }
        }
        else if(isAddDiagnosis)
        {
            isAddDiagnosis = false;
            diagnosisEditText.setText(QUERY);
        }
        else if(isAddName)
        {
            isAddName = false;
            nameEditText.setText(QUERY);
            if(ageEditText.getText().toString().equals(""))
            {
                USER_INTENT = Intents.AGE;
                multiplexIntext();
            }
        }
        else if(isAddPrescription)
        {
            isAddPrescription = false;
            addLayoutToLinLayout(QUERY);
            USER_INTENT = Intents.PRESCRIPTION;
            multiplexIntext();
        }
        else if(isAddSymptoms)
        {
            isAddSymptoms = false;
            symptomsEditText.setText(QUERY);
            if(adviceEditText.getText().toString().equals(""))
            {
                USER_INTENT = Intents.ADVICE;
                multiplexIntext();
            }
        }
        else if(isAddAge)
        {
            isAddAge = false;
            ageEditText.setText(QUERY);
            if(genderEditText.getText().toString().equals(""))
            {
                USER_INTENT = Intents.GENDER;
                multiplexIntext();
            }
        }
        else if(isAddGender)
        {
            isAddGender =false;
            if(QUERY.toLowerCase().contains("f"))
                genderEditText.setText("female");
            else
                genderEditText.setText("male");
            if(symptomsEditText.getText().toString().equals(""))
            {
                USER_INTENT = Intents.SYMPTOMS;
                multiplexIntext();
            }
        }

    }

    public void addLayoutToLinLayout(String s)
    {
        final View view = getLayoutInflater().inflate(R.layout.prescription_layout,null,false);
        EditText editText = view.findViewById(R.id.prescription_edittext);
        Button button = view.findViewById(R.id.prescription_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prescriptionLinearLayout.removeView(view);
            }
        });
        editText.setText(s);
        prescriptionLinearLayout.addView(view);
    }

    private void multiplexIntext() {

        QUERY = QUERY.trim();

        if(USER_INTENT.equals(Intents.ADVICE))
            addAdvice();
        else if(USER_INTENT.equals(Intents.DIAGNOSIS))
            addDiagnosis();
        else if(USER_INTENT.equals(Intents.PRESCRIPTION))
            addPrescription();
        else if(USER_INTENT.equals(Intents.NAME))
            addName();
        else if(USER_INTENT.equals(Intents.SYMPTOMS))
            addSymptoms();
        else if(USER_INTENT.equals(Intents.AGE))
            addAge();
        else if(USER_INTENT.equals(Intents.GENDER))
            addGender();

    }

    private void cancelIntents() {
        isAddAdvice =false;
        isAddAge = false;
        isAddDiagnosis =false;
        isAddPrescription= false;
        isAddGender = false;
        isAddSymptoms =false;
        isAddName =false;

    }

    private void addAge() {
        TTSClass.speak(MainActivity.this, "Enter Age", new TTSClass.UtteranceListener() {
            @Override
            public void taskDone() {
            }
        });
        isAddAge = true;
        delayedSTT(1500l);
        task = 1;
    }

    private void delayedSTT(long l) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initSTT();
            }
        },l);
    }

    private void addGender()
    {
        TTSClass.speak(MainActivity.this, "Enter Gender", new TTSClass.UtteranceListener() {
            @Override
            public void taskDone() {
            }
        });
        task = 2;
        isAddGender = true;
        delayedSTT(1500l);
    }

    private void addPrescription() {
        TTSClass.speak(MainActivity.this, "Enter Prescription", new TTSClass.UtteranceListener() {
            @Override
            public void taskDone() {
            }
        });
        task = 6;
        isAddPrescription = true;
        delayedSTT(1500l);

    }

    private void addSymptoms()
    {
        TTSClass.speak(MainActivity.this, "Enter symptoms", new TTSClass.UtteranceListener() {
            @Override
            public void taskDone() {

            }
        });
        task = 3;
        isAddSymptoms = true;
        delayedSTT(1500l);
    }

    private void addDiagnosis()
    {
        TTSClass.speak(MainActivity.this, "Enter Diagnosis", new TTSClass.UtteranceListener() {
            @Override
            public void taskDone() {
            }
        });
        task = 5;
        isAddDiagnosis = true;
        delayedSTT(1500l);
    }

    private void addName()
    {
        TTSClass.speak(MainActivity.this, "Enter Name", new TTSClass.UtteranceListener() {
            @Override
            public void taskDone() {
            }
        });
        task = 0;
        isAddName = true;
        delayedSTT(1500l);
    }

    private void addAdvice()
    {
        TTSClass.speak(MainActivity.this, "Enter Advice", new TTSClass.UtteranceListener() {
            @Override
            public void taskDone() {

            }
        });
        task = 4;
        isAddAdvice = true;
        delayedSTT(1500l);
    }
}
