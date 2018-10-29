package com.example.aferyannie.learningapp;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.util.Locale;

/**
* Do not forget to set!
* COUNTDOWN_IN_MILLIS = 90000
* (90 seconds).
* */

public class CategoryActivity extends Fragment {
    private static final long COUNTDOWN_IN_MILLIS = 31000; // lebihin satu detik (trial)
    private static final String TAG_AUDIO = "AUDIO_LOG";
    private static final String TAG_TIMER = "TIMER_LOG";

    private TextView txtTimer;

    private TextView txtCategory;
    private TextView txtPronounce;
    private Button btnSound;

    private ColorStateList colorDefaultCountdown;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    MediaPlayer pronounce1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_category, null, false);

        txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        txtPronounce = (TextView) view.findViewById(R.id.txtPronounce);

        pronounce1 = MediaPlayer.create(getContext(), R.raw.pronunciation_numbers);

        btnSound = (Button) view.findViewById(R.id.btnSound);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(pronounce1.isPlaying()){
                        pronounce1.stop();
                        pronounce1.release();
                        Log.d(TAG_AUDIO, "pronounce1:onStop");
                        pronounce1 = MediaPlayer.create(getContext(), R.raw.pronunciation_numbers);
                    }
                    pronounce1.start();
                    Log.d(TAG_AUDIO, "pronounce1:onStart");
                    pronounce1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            pronounce1.reset();
                            pronounce1.release();
                            Log.d(TAG_AUDIO, "pronounce1:onComplete");
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                    Log.d(TAG_AUDIO, "pronounce1:onError");
                }
            }
        });
        txtTimer = (TextView) view.findViewById(R.id.txtTimer);
        colorDefaultCountdown = txtTimer.getTextColors();

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountdown();
        return view;
    }

    private void startCountdown(){
        Log.d(TAG_TIMER, "countDownTimer:onStart");
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdown();
            }
            @Override
            public void onFinish() {
                FancyToast.makeText(getContext(), "Time's Up", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                timeLeftInMillis = 0;
                updateCountdown();
                countDownTimer.cancel();
                Log.d(TAG_TIMER, "countDownTimer:onFinish");
                pronounce1.stop();
                showFragment(new ResultActivity(),R.id.fragment_container);
            }
        }.start();
    }

    public void showFragment(Fragment fragment, int fragmentResourceID) {
        if (fragment != null) {
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(fragmentResourceID, fragment);
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
        }
    }

    private void updateCountdown(){
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        txtTimer.setText(timeFormatted);

        if(timeLeftInMillis < 11000){
            txtTimer.setTextColor(Color.RED);
        } else {
            txtTimer.setTextColor(colorDefaultCountdown);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

}
