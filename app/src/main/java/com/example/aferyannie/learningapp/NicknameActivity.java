package com.example.aferyannie.learningapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

public class NicknameActivity extends Fragment {
    DatabaseReference databaseNames;

    EditText inputNames;
    Button btnNames;
    Button btnSkip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nickname, null, false);

        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");
        inputNames = (EditText) view.findViewById(R.id.inputNames);
        btnNames = (Button) view.findViewById(R.id.btnNames);
        btnSkip = (Button) view.findViewById(R.id.btnSkip);

        btnNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNames();
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(new HomeActivity(),R.id.fragment_container);
            }
        });
        return view;
    }

    private void saveNames() {
        String name = inputNames.getText().toString().trim();
        // get arguments from bundle in ResultActivity.
        Bundle bundle = getArguments();
        // get bundle with key "Skor".
        double c = bundle.getDouble("Skor");
        if (!TextUtils.isEmpty(name)) {
            // Define current time using epoch timestamp.
            Long now = System.currentTimeMillis();

            String id = databaseNames.push().getKey();
            Name nickname = new Name(name, c, now);
            databaseNames.child(id).setValue(nickname);

            // Initialize AlertDialog.
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            // Setting Dialog Title.
            alertDialog.setTitle("Berhasil");
            // Setting Dialog Message.
            alertDialog.setMessage("Skor telah sukses disimpan. Jangan lupa cek papan skor ya!");
            // Setting OK Button.
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    inputNames.setSelection(0);
                }
            });
            alertDialog.show();

            showFragment(new HomeActivity(),R.id.fragment_container);
        } else {
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
