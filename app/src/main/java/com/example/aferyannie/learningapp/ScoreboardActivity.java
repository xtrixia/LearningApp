package com.example.aferyannie.learningapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardActivity extends Fragment {
    DatabaseReference databaseNames;

    // Define listView scoreboard.
    ListView listViewScores;
    List<Name> nameList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_scoreboard,null,false);

        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");
        listViewScores = (ListView) view.findViewById(R.id.listViewScores);
        nameList = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseNames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList.clear();
                for(DataSnapshot scoreSnapshot: dataSnapshot.getChildren()){
                    Name name = scoreSnapshot.getValue(Name.class);
                    nameList.add(name);
                }
                NameList adapter = new NameList(getActivity(), nameList);
                listViewScores.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
