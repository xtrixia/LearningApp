package com.example.aferyannie.learningapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by aferyannie on 04/10/18.
 */

//public class Name implements Serializable {
public class Name implements Parcelable {
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

    public Name(Parcel data){
        name = data.readString();
        score = data.readDouble();
        created_at = data.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(score);
        dest.writeLong(created_at);
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

    public static final Creator<Name> CREATOR = new Creator<Name>() {
        @Override
        public Name[] newArray(int size) {
            return new Name[size];
        }

        @Override
        public Name createFromParcel(Parcel source) {
            return new Name(source);
        }
    };
}
