package com.example.aferyannie.learningapp;

/**
 * Created by aferyannie on 31/12/18.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreList extends ArrayAdapter {
    private Activity context;
    private List<Score> scoreList;

    public ScoreList(Activity context, List<Score> passedList) {
        super(context, R.layout.list_layout, passedList);
        this.context = context;
        this.scoreList = passedList;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView txtNames = listViewItem.findViewById(R.id.txtNames);
        TextView txtScores = listViewItem.findViewById(R.id.txtScores);

        Score score = scoreList.get(position);
        txtNames.setText(score.getName());
        txtScores.setText(score.getScore());

        return listViewItem;
    }
}
