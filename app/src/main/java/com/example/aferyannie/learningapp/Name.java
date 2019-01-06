package com.example.aferyannie.learningapp;

import java.io.Serializable;

/**
 * Created by aferyannie on 04/10/18.
 */

public class Name implements Serializable { // Serializable is used for passing object between running processes.
    private String score;
    private Long created_at;
    private String imageBase64;

    public Name(String score, Long created_at, String imageBase64) {
        this.score = score;
        this.created_at = created_at;
        this.imageBase64 = imageBase64;
    }

    public Name() {

    }

    public String getScore() {
        return score;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

}
