package com.example.aferyannie.learningapp;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class NicknameDialog extends DialogFragment {
    private static final String TAG = NicknameDialog.class.getSimpleName();

    DatabaseReference databaseNames;
    EditText inputNames;
    TextView btnNames, btnSkip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_nickname, null, false);

        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");
        inputNames = view.findViewById(R.id.inputNames);
        btnNames = view.findViewById(R.id.btnNames);
        btnSkip = view.findViewById(R.id.btnSkip);

        btnNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNames();
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "NicknameDialog: onSkip inserted data");
                FancyToast.makeText(getContext(), "Anda memilih untuk tidak menyimpan skor.",
                        FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                getDialog().dismiss();
                showFragment(new HomeFragment(),R.id.fragment_container);
            }
        });
        return view;
    }

    private void saveNames() {
        String name = inputNames.getText().toString().trim();
        // get arguments from bundle in ResultFragment.
        Bundle bundle = getArguments();
        // get bundle with key "Skor".
        double c = bundle.getDouble("Skor");
        if (!TextUtils.isEmpty(name)) {
                // Define current time using epoch timestamp.
                Long now = System.currentTimeMillis();

                String id = databaseNames.push().getKey();
                Name nickname = new Name(name, c, now);
                databaseNames.child(id).setValue(nickname);

                Log.d(TAG, "NicknameDialog: onSuccess inserted data");
                FancyToast.makeText(getContext(), "Skor telah sukses disimpan. Jangan lupa cek papan skor ya!",
                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                getDialog().dismiss();
                showFragment(new HomeFragment(), R.id.fragment_container);
        } else {
            Log.d(TAG, "NicknameDialog: onRetry fulfilled data");
            FancyToast.makeText(getContext(), "Isi nama untuk simpan skor.",
                   FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
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
