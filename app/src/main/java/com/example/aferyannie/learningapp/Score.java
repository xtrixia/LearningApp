package com.example.aferyannie.learningapp;

import java.io.Serializable;

/**
 * Created by aferyannie on 31/12/18.
 */

public class Score implements Serializable {
    private String score;
    private String name;
    private String id;

    public Score(String score, String name, String id) {
        this.score = score;
        this.name = name;
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
