package com.iiitr.shubham.prescriptions.Utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.util.Locale;

public class TTSClass {
    public interface UtteranceListener
    {
        void taskDone();
    }
    public static TextToSpeech mTTs;
    public static void speak(final Context context, final String s, final UtteranceListener listener)
    {
        mTTs = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(mTTs!=null && status == TextToSpeech.SUCCESS)
                {
                    mTTs.speak(s,TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });
        mTTs.setLanguage(Locale.getDefault());
        mTTs.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                listener.taskDone();
            }

            @Override
            public void onError(String utteranceId) {
                Toast.makeText(context, utteranceId+ " error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
