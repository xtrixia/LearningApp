package com.example.aferyannie.learningapp;

import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 1st Scoreboard;
 * 1) Name
 * 2) # of Attempt
 */

public class ScoreboardFragment extends Fragment {
    private static final String TAG = ScoreboardFragment.class.getSimpleName();
    DatabaseReference databaseNames;

    /**
     * Define listView scoreboard.
     */
    ListView listViewScores; // define listview from scoreboard.
    List<Score> nameList = new ArrayList<>(); // define list from Score class.
    String Name = "";
    String Score = "";
    String id = "";
    private ScoreList adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoreboard, null, false);

        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");
        listViewScores = view.findViewById(R.id.listViewScores);
        nameList = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "listViewScores: onFetch via Scoreboard");
        NavigationView navigationView;
        if (getActivity() != null) {
            navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
            navigationView.setCheckedItem(R.id.nav_scoreboard);

            databaseNames.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                        Iterable<DataSnapshot> NameData = scoreSnapshot.getChildren();
                        for (DataSnapshot name : NameData) {
                            Name = name.getKey();
                            Iterable<DataSnapshot> ScoreData = name.getChildren();
                            for (DataSnapshot score : ScoreData) {
                                Score = score.getKey();
                            }
                        }
                        id = scoreSnapshot.getKey();
                        Score score = new Score(Score, Name, id);
                        nameList.add(score);
                    }
                    adapter = new ScoreList(getActivity(), nameList);
                    listViewScores.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        /** Create onClick listener for each item in the listView. */
        listViewScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showFragment(new ScoreListFragment(), R.id.fragment_container, nameList.get(i));
                Log.d(TAG, "listViewScores: onItemClick index " + i);
            }
        });
    }

    public void showFragment(Fragment fragment, int fragmentResourceID, Score data) {
        if (fragment != null) {
            FragmentManager fragmentManager = this.getFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(fragmentResourceID, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
