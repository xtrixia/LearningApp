package com.example.aferyannie.learningapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogoutActivity extends Fragment {
    private static final String TAG = "FACEBOOK_LOG";

    private FirebaseAuth mAuth;

    Button logoutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_logout, null, false);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        logoutButton = (Button) view.findViewById(R.id.btnLogout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth != null){
                    LoginManager.getInstance().logOut();
                    mAuth.signOut();
                    Log.d(TAG, "signOut:success");
//                    Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

                    FancyToast.makeText(getContext(), "Logged out successfully.",
                            FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    updateUI();
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            updateUI();
        }
    }

    private void updateUI() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LoginActivity()).commit();
    }
}
