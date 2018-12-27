package com.example.aferyannie.learningapp;

import java.io.Serializable;

/**
 * Created by aferyannie on 04/10/18.
 */

public class Name implements Serializable { // Serializable is used for passing object between running processes.
    private String name;
    private Double score;
    private Long created_at;
    private String imageBase64;

    public Name() {

    }

    public Name(String name, Double score, Long created_at, String imageBase64) {
        this.name = name;
        this.score = score;
        this.created_at = created_at;
        this.imageBase64 = imageBase64;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public String getImageBase64() {
        return imageBase64;
    }

}
