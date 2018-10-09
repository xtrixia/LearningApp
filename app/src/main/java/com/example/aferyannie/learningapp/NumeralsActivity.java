package com.example.aferyannie.learningapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/*
* Do not forget to set!
* COUNTDOWN_IN_MILLIS = 90000
* (90 seconds).
* */

public class NumeralsActivity extends Fragment {
    private static final long COUNTDOWN_IN_MILLIS = 20000; // Set to 20 seconds. (trial)
    private TextView txtTimer;

    private ColorStateList colorDefaultCountdown;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_numerals, null, false);

        txtTimer = (TextView) view.findViewById(R.id.txtTimer);
        colorDefaultCountdown = txtTimer.getTextColors();

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountdown();
        return view;
    }

    private void startCountdown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdown();
            }
            @Override
            public void onFinish() {
                Toast.makeText(getContext(), "Time's Up", Toast.LENGTH_SHORT).show();
                timeLeftInMillis = 0;
                updateCountdown();
                countDownTimer.cancel();
                showFragment(new LoginActivity(),R.id.fragment_container);
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

        if(timeLeftInMillis < 10000){
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
