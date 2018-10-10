package com.example.aferyannie.learningapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NicknameActivity extends Fragment {
    DatabaseReference databaseNames;

    // Define nickname input field.
    EditText inputNames;
    Button btnNames;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nickname, null, false);

        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");
        inputNames = (EditText) view.findViewById(R.id.inputNames);
        btnNames = (Button) view.findViewById(R.id.btnNames);

        btnNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNames();
                showFragment(new HomeActivity(),R.id.fragment_container);
            }
        });
        return view;
    }

    private void saveNames() {
        String name = inputNames.getText().toString().trim();
        Double score = 0.1;
        if (!TextUtils.isEmpty(name)) {
            // Define current time using epoch timestamp.
            Long now = System.currentTimeMillis();

            String id = databaseNames.push().getKey();
            Name nickname = new Name(name, score, now);
            databaseNames.child(id).setValue(nickname);
            Toast.makeText(getContext(), "Check Scoreboard", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Please", Toast.LENGTH_LONG).show();
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
