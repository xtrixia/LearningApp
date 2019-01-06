package com.example.aferyannie.learningapp;

import android.support.design.widget.TextInputLayout;
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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class NicknameDialog extends DialogFragment {
    private static final String TAG = NicknameDialog.class.getSimpleName();

    DatabaseReference databaseNames;
    TextInputLayout inputName;
    TextView btnName, btnSkip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_nickname, null, false);

        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");
        Log.d(TAG, "inputName: onDisplay");

        inputName = view.findViewById(R.id.inputName);
        btnName = view.findViewById(R.id.btnName);
        btnSkip = view.findViewById(R.id.btnSkip);

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNames();
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "inputName: onSkip save data");
                FancyToast.makeText(getContext(), "Anda memilih untuk tidak menyimpan skor.",
                        FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();

                getDialog().dismiss();
                showFragment(new HomeFragment(), R.id.fragment_container);
            }
        });
        return view;
    }

    private void saveNames() {
        String name = inputName.getEditText().getText().toString().trim();
        ArrayList<String> Score = getArguments().getStringArrayList("Score");
        ArrayList<String> Image = getArguments().getStringArrayList("Image");
        Bundle bundle = getArguments(); // Get arguments from bundle in ResultFragment.
        double c = bundle.getDouble("Skor"); // Get bundle with key "Skor".
        String jumlahBenar = String.valueOf(bundle.getInt("jumlahBenar"));
        jumlahBenar = "Total benar " + jumlahBenar + " dari 10";
        if (!TextUtils.isEmpty(name) && name.length() <= 10) {
            inputName.setError(null);
            Log.d(TAG, "inputName: onSuccess");

            Long now = System.currentTimeMillis(); // Define current time using epoch timestamp.
            String id = databaseNames.push().getKey();
            Name nickname;

            for (int i = 0; i < Score.size(); i++) {
                nickname = new Name(Score.get(i), now, Image.get(i));
                databaseNames.child(id).child(name).child(jumlahBenar).child(String.valueOf(i)).setValue(nickname);
            }

            FancyToast.makeText(getContext(), "Skor telah sukses disimpan. Jangan lupa cek papan skor ya!",
                    FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

            getDialog().dismiss();
            showFragment(new HomeFragment(), R.id.fragment_container);
        } else if (name.length() > 10) {
            inputName.setError("Nama mu terlalu panjang");
            Log.d(TAG, "inputName: onPending insert data");
            return;
        } else {
            inputName.setError("Isi nama untuk simpan skor");
            Log.d(TAG, "inputName: onPending insert data");
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
