package com.example.aferyannie.learningapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
* Do not forget to check!
* case btnStart -> NumeralsActivity
* case btnStart1 -> UppercaseActivity (belum ada)
* case btnStart2 -> LowercaseActivity (belum ada).
* */

public class HomeActivity extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, null, false);

        Button btnStart = (Button) view.findViewById(R.id.btnStart);
        Button btnStart1 = (Button) view.findViewById(R.id.btnStart1);
        Button btnStart2 = (Button) view.findViewById(R.id.btnStart2);

        btnStart.setOnClickListener(this);
        btnStart1.setOnClickListener(this);
        btnStart2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // ButtonStart for numerals.
            case R.id.btnStart:
                showFragment(new NumeralsActivity(),R.id.fragment_container);
                break;
            // ButtonStart for alphabets_upper.
            case R.id.btnStart1:
                showFragment(new Fragment(),R.id.fragment_container);
                break;
            // ButtonStart for alphabets_lower.
            case R.id.btnStart2:
                showFragment(new Fragment(),R.id.fragment_container);
                break;
        }
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
