package com.example.aferyannie.learningapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
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

public class ScoreboardActivity extends AppCompatActivity {
//public class ScoreboardActivity extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.activity_scoreboard, container, false);
//    }

    DatabaseReference databaseNames;

    // Define listViewScores.
    ListView listViewScores;
    List<Name> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");

        listViewScores = (ListView) findViewById(R.id.listViewScores);

        nameList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseNames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList.clear();
                for(DataSnapshot scoreSnapshot: dataSnapshot.getChildren()){
                    Name name = scoreSnapshot.getValue(Name.class);
                    nameList.add(name);
                }
                NameList adapter = new NameList(ScoreboardActivity.this, nameList);
                listViewScores.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
