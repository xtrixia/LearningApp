package com.example.aferyannie.learningapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailFragment extends Fragment {
    private static final String TAG = DetailFragment.class.getSimpleName();

    Button btnBack;
    ImageView img;
    TextView txtNames, txtTimestamp, txtScores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null, false);

        Log.d(TAG, "FirebaseDatabase: onRetrieve detail data");
        Name data = (Name) getArguments().getSerializable("data");

        img = view.findViewById(R.id.img);
        txtNames = view.findViewById(R.id.txtNames);
        txtScores = view.findViewById(R.id.txtScores);
        txtTimestamp = view.findViewById(R.id.txtTimestamp);

        /** Set all value based on the clicked item.
         *  Get img, name, score, created_at from Firebase. */
        final String encodedImage = data.getImageBase64(); // get base64 from firebase.
        final String pureBase64Encoded = encodedImage.substring(encodedImage.indexOf(",") + 1);
        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT); // decoding base64 to an img.
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        img.setImageBitmap(decodedByte);

        txtNames.setText(getArguments().getString("nama"));
        txtScores.setText(String.format(data.getScore().toString(), "%d"));
        Long epoch = data.getCreated_at();
        Date date = new Date(epoch); // Implement epoch to Date.
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Set format date.
        String formattedDate = sdf.format(date);
        txtTimestamp.setText(formattedDate);

        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(new ScoreboardFragment(), R.id.fragment_container);
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
