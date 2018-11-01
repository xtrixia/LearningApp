package com.example.aferyannie.learningapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

        /** Initialize Firebase Auth. */
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        logoutButton = (Button) view.findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth != null){
                    LoginManager.getInstance().logOut();
                    mAuth.signOut();
                    Log.d(TAG, "signOut:success");
                    FancyToast.makeText(getContext(), "Anda berhasil keluar dari Facebook.",
                            FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    updateUI(currentUser);
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        /** Check if user is signed in (non-null) and update UI accordingly. */
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        TextView navbarNickname = getActivity().findViewById(R.id.nickname);
        TextView navbarEmail = getActivity().findViewById(R.id.email);
        ImageView navbarImageView = getActivity().findViewById(R.id.displaypicture);

        MainActivity.scoreboard.setVisible(false);

        navbarEmail.setVisibility(View.INVISIBLE);

        navbarNickname.setText(R.string.guest);
        navbarImageView.setImageResource(R.drawable.emoji_blank);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LoginActivity()).commit();
    }
}
