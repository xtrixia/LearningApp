package com.example.aferyannie.learningapp;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NavigationView navigationView;
        navigationView =(NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnStart = view.findViewById(R.id.btnStart);
        Button btnStart1 = view.findViewById(R.id.btnStart1);
        Button btnStart2 = view.findViewById(R.id.btnStart2);

        btnStart.setOnClickListener(this);
        btnStart1.setOnClickListener(this);
        btnStart2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        ArrayList<String> Score = new ArrayList<>();
        ArrayList<String> Image = new ArrayList<>();

        bundle = new Bundle();
        bundle.putInt("jumlahTest", 0); // total attempt = 0.
        bundle.putInt("jumlahBenar", 0); // total correct attempt = 0.
        bundle.putStringArrayList("Score", Score); // set accuracy score to bundle arraylist.
        bundle.putStringArrayList("Image", Image); // set image drawn to bundle arraylist.

        switch (view.getId()) {
            /** Button for numerals. */
            case R.id.btnStart:
                bundle.putString("Kategori", "Angka");
                CategoryFragment categoryAngka = new CategoryFragment();
                nextFragment(categoryAngka);
                break;
            /** Button for alphabets_upper. */
            case R.id.btnStart1:
                bundle.putString("Kategori", "HurufKapital");
                CategoryFragment categoryHurufKapital = new CategoryFragment();
                nextFragment(categoryHurufKapital);
                break;
            /** Button for alphabets_lower. */
            case R.id.btnStart2:
                bundle.putString("Kategori", "HurufKecil");
                CategoryFragment categoryHurufKecil = new CategoryFragment();
                nextFragment(categoryHurufKecil);
                break;
        }
    }

    public void nextFragment(CategoryFragment category) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        category = new CategoryFragment();
        category.setArguments(bundle);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, category);
        fragmentTransaction.commit();
    }

}
