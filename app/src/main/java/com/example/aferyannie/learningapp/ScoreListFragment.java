package com.example.aferyannie.learningapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * 2nd Scoreboard;
 * 1) Each attempt
 * 2) Score per attempt
 */

public class ScoreListFragment extends Fragment {
    private static final String TAG = ScoreListFragment.class.getSimpleName();
    DatabaseReference databaseNames;

    ListView listViewScores; // define listview from scorelist.
    List<Score> DataList; // define list from Score class.
    List<Name> DetailData; // define list from Name class.
    private String Label = "";
    private String Score = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoreboard, null, false);

        DetailData = new ArrayList<>();
        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");
        listViewScores = view.findViewById(R.id.listViewScores);
        DataList = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Score TargetData = (Score) getArguments().getSerializable("data");
        final String TargetName = TargetData.getName();
        final String TargetChild = TargetData.getScore();
        final String TargetId = TargetData.getId();
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "listViewScores: onFetch via ScoreList");
        databaseNames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataList.clear();
                for (DataSnapshot scoreSnapshot : dataSnapshot.child(TargetId).child(TargetName).child(TargetChild).getChildren()) {
                    Name data = scoreSnapshot.getValue(Name.class);
                    Score = data.getScore();
                    Label = scoreSnapshot.getKey();
                    int LabelInt = Integer.parseInt(Label) + 1;
                    Label = "Soal " + String.valueOf(LabelInt);
                    Score score = new Score(Score, Label, " ");
                    DataList.add(score);
                    DetailData.add(data);
                }
                ScoreList adapter = new ScoreList(getActivity(), DataList);
                listViewScores.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /** Create onClick listener for each item. */
        listViewScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showFragment(new DetailFragment(), R.id.fragment_container, DetailData.get(i), TargetName);
                Log.d(TAG, "listViewScores: onItemClick index " + i);
            }
        });
    }

    public void showFragment(Fragment fragment, int fragmentResourceID, Name data, String nama) {
        if (fragment != null) {
            FragmentManager fragmentManager = this.getFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString("nama", nama);
            bundle.putSerializable("data", data);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(fragmentResourceID, fragment);
//            fragmentTransaction.detach(fragment);
//            fragmentTransaction.attach(fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.commit();
        }
    }
}
