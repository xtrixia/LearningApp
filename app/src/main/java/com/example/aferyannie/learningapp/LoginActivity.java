package com.example.aferyannie.learningapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
//public class LoginActivity extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        return super.onCreateView(inflater, container, savedInstanceState);
//        return inflater.inflate(R.layout.activity_login, container, false);
//    }

    EditText inputNames;
    Button btnNames;
    DatabaseReference databaseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseNames = FirebaseDatabase.getInstance().getReference("scoreboard");
        inputNames = (EditText) findViewById(R.id.inputNames);
        btnNames = (Button) findViewById(R.id.btnNames);

        btnNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNames();
            }
        });
    }

    private void saveNames(){
        String name = inputNames.getText().toString().trim();
        Double score = 0.1;
        if(!TextUtils.isEmpty(name)){
            // Define current time using epoch timestamp.
            Long now = System.currentTimeMillis();
            String id = databaseNames.push().getKey();
            Name nickname = new Name(name, score, now);
            databaseNames.child(id).setValue(nickname);
            Toast.makeText(this, "Check Scoreboard", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this, "Please", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(LoginActivity.this, ScoreboardActivity.class);
        startActivity(intent);
    }

}
