package com.example.aferyannie.learningapp;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResultActivity extends Fragment {
    FirebaseAuth mAuth;
    TextView txtNyemangatin;
    Button btnNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_result, null, false);

        /** Initialize Firebase Auth. */
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        txtNyemangatin = (TextView) view.findViewById(R.id.txtNyemangatin); // nanti set tulisan sesuai hasil akurasi
        // x < 5 disuru latian lagi, 5 < x < 8 disuru ditingkatkan lagi, x > 8 disuru maintain dan rajin belajar

        btnNext = (Button) view.findViewById(R.id.btnNext);
        if(user == null){
            btnNext.setText("Thanks");
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null){
                    showFragment(new NicknameActivity(),R.id.fragment_container);
                } else {
                    showFragment(new HomeActivity(), R.id.fragment_container);
                }
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
