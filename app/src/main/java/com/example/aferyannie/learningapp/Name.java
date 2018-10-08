package com.example.aferyannie.learningapp;

/**
 * Created by aferyannie on 04/10/18.
 */

public class Name {
//    private String nameId;
    private String name;
    private Double score;
    private Long created_at;

    public Name(){

    }

    public Name(String name, Double score, Long created_at) { // String nameId belum di-input.
//        this.nameId = nameId;
        this.name = name;
        this.score = score;
        this.created_at = created_at;
    }

//    public String getNameId() {
//        return nameId;
//    }

    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }

    public Long getCreated_at() {
        return created_at;
    }
}
