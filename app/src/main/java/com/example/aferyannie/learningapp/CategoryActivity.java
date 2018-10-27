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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Locale;

/*
* Do not forget to set!
* COUNTDOWN_IN_MILLIS = 90000
* (90 seconds).
* */

public class CategoryActivity extends Fragment {
    private static final long COUNTDOWN_IN_MILLIS = 16000; // lebihin satu detik (trial)
    private TextView txtTimer;

    private TextView txtCategory;
    private TextView txtPronounce;
    private Button btnSound;

    private ColorStateList colorDefaultCountdown;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_category, null, false);

        txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        txtPronounce = (TextView) view.findViewById(R.id.txtPronounce);

        btnSound = (Button) view.findViewById(R.id.btnSound);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FancyToast.makeText(getContext(), "Not yet functional.",
                        FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });

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
//                Toast.makeText(getContext(), "Time's Up", Toast.LENGTH_SHORT).show();
                FancyToast.makeText(getContext(), "Time's Up", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                timeLeftInMillis = 0;
                updateCountdown();
                countDownTimer.cancel();
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
