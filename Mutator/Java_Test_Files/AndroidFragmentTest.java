package com.tourism.app.fragments;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tourism.app.R;

import java.util.Locale;

public class AndroidFragmentTest extends Fragment  {


    public static AndroidFragmentTest newInstance() {
        return new AndroidFragmentTest();
    }

    TextToSpeech tts;
    TextView info_text;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_info_fragment, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.backround));

        //TODO:Text must be before
        info_text = view.findViewById(R.id.info_text);


        tts = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.getDefault());
                    tts.setSpeechRate((float)0.8);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
                tts.speak(info_text.getText().toString(),TextToSpeech.QUEUE_FLUSH, null,null); //To speak on init press

            }
        });






        ImageButton replayButton = view.findViewById(R.id.replay_button);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tts.speak(info_text.getText().toString(),TextToSpeech.QUEUE_FLUSH, null,null); //To speak on button press

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
        tts.stop();
    }
}
