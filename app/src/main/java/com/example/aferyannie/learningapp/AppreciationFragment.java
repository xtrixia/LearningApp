package com.example.aferyannie.learningapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by aferyannie on 08/01/19.
 */

public class AppreciationFragment extends Fragment {
    MediaPlayer appreciate;
    Button btnHome;
    TextView txtTitle, txtFor, txtForName, txtFor2, txtAverage, txtNotes;

    public AppreciationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appreciation, container, false);

        txtTitle = view.findViewById(R.id.txtTitle);
        txtFor = view.findViewById(R.id.txtFor);
        txtForName = view.findViewById(R.id.txtForName);
        txtFor2 = view.findViewById(R.id.txtFor2);
        txtAverage = view.findViewById(R.id.txtAverage);
        txtNotes = view.findViewById(R.id.txtNotes);

        /** Set values for name and score based on Firebase. */
        String name = getArguments().getString("Nama");
        txtForName.setText(name);
        String nilai = getArguments().getString("Nilai");
        txtAverage.setText(nilai);

        Log.i("Nama Anak", getArguments().getString("Nilai"));
        Log.i("Nilai Anak", getArguments().getString("Nama"));

        int nilaiInteger = Integer.parseInt(nilai);
        if (nilaiInteger > 5) {
            txtNotes.setText("SELAMAT! NILAIMU MENGESANKAN!");
            appreciate = MediaPlayer.create(getContext(), R.raw.yay);
            appreciate.start();
        } else {
            txtNotes.setText("TAPI NILAIMU MASI KURANG, AYO LATIHAN LAGI..");
            appreciate = MediaPlayer.create(getContext(), R.raw.yah);
            appreciate.start();
        }

        btnHome = view.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(new HomeFragment(), R.id.fragment_container);
            }
        });
        return view;
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

}
