package com.example.aferyannie.learningapp;

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

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Locale;
import java.util.Random;

/**
* Do not forget to set!
* COUNTDOWN_IN_MILLIS = 90000
* (90 seconds).
* */

public class CategoryActivity extends Fragment {
    private static final long COUNTDOWN_IN_MILLIS = 16000; // lebihin satu detik (trial)
    private static final String TAG_AUDIO = "AUDIO_LOG";
    private static final String TAG_TIMER = "TIMER_LOG";

    private TextView txtCategory;
    private TextView txtPronounce;
    public static Button btnSound;
    private TextView txtTimer;

    private ColorStateList colorDefaultCountdown;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    MediaPlayer pronounce;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_category, null, false);

        if(MainActivity.main_menu.isPlaying()){
            MainActivity.main_menu.pause();
            Log.d(TAG_AUDIO, "main_menu:onPause");
        }

        txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        txtPronounce = (TextView) view.findViewById(R.id.txtPronounce);

        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.containsKey("Angka")){
                KategoriAngka();
            } else if(bundle.containsKey("HurufKapital")){
                KategoriHurufKapital();
            } else{
                KategoriHurufKecil();
            }
        }

        btnSound = (Button) view.findViewById(R.id.btnSound);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG_AUDIO, "pronounce:onEnable");
                if (pronounce.isPlaying()) {
                    pronounce.stop();
                }
                SoundClick();
            }
        });
        txtTimer = (TextView) view.findViewById(R.id.txtTimer);
        colorDefaultCountdown = txtTimer.getTextColors();

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountdown();
        return view;
    }

    public void SoundClick() {
                try {
                    pronounce.start();
                    Log.d(TAG_AUDIO, "pronounce:onStart");
                    pronounce.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            Log.d(TAG_AUDIO, "pronounce:onComplete");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG_AUDIO, "pronounce:onError");
                }
            }

    public void KategoriAngka(){
        // get arguments from bundle in HomeActivity.
        Bundle bundle = getArguments();
        int num = bundle.getInt("Angka");
        txtCategory.setText(Integer.toString(num));
        switch(num){
            case 0:
                txtPronounce.setText("Nol");
                // initialize audio resource.
                pronounce = MediaPlayer.create(getContext(), R.raw.nol);
                break;
            case 1:
                txtPronounce.setText("Satu");
                pronounce = MediaPlayer.create(getContext(), R.raw.satu);
                break;
            case 2:
                txtPronounce.setText("Dua");
                pronounce = MediaPlayer.create(getContext(), R.raw.dua);
                break;
            case 3:
                txtPronounce.setText("Tiga");
                pronounce = MediaPlayer.create(getContext(), R.raw.tiga);
                break;
            case 4:
                txtPronounce.setText("Empat");
                pronounce = MediaPlayer.create(getContext(), R.raw.empat);
                break;
            case 5:
                txtPronounce.setText("Lima");
                pronounce = MediaPlayer.create(getContext(), R.raw.lima);
                break;
            case 6:
                txtPronounce.setText("Enam");
                pronounce = MediaPlayer.create(getContext(), R.raw.enam);
                break;
            case 7:
                txtPronounce.setText("Tujuh");
                pronounce = MediaPlayer.create(getContext(), R.raw.tujuh);
                break;
            case 8:
                txtPronounce.setText("Delapan");
                pronounce = MediaPlayer.create(getContext(), R.raw.delapan);
                break;
            case 9:
                txtPronounce.setText("Sembilan");
                pronounce = MediaPlayer.create(getContext(), R.raw.sembilan);
                break;
        }
    }

    public void KategoriHurufKecil(){
        Bundle bundle = getArguments();
        String[] charsLower = bundle.getStringArray("HurufKecil");
        txtCategory.setText(charsLower[(int) (Math.random() * 10)]);
        txtPronounce.setVisibility(View.GONE);
    }

    public void KategoriHurufKapital(){
        Bundle bundle = getArguments();
        String[] charsUpper = bundle.getStringArray("HurufKapital");
        txtCategory.setText(charsUpper[(int) (Math.random() * 10)]);
        txtPronounce.setVisibility(View.GONE);
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
            public void onFinish() {timeLeftInMillis = 0;
                updateCountdown();
                countDownTimer.cancel();
                Log.d(TAG_TIMER, "countDownTimer:onFinish");
                pronounce.stop();
                Log.d(TAG_AUDIO, "pronounce:onDisable");
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
            if(timeLeftInMillis < 3000){
                FancyToast.makeText(getContext(), "Waktu Habis", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        } else {
            txtTimer.setTextColor(colorDefaultCountdown);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.main_menu.start();
        Log.d(TAG_AUDIO, "main_menu:onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

}
