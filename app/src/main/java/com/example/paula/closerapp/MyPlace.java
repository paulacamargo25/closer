package com.example.paula.closerapp;

public class MyPlace {

    // Store the id of the  movie poster
    private int mImageDrawable;
    // Store the name of the movie
    private String mName;
    // Store the release date of the movie
    private String mAddress;

    // Constructor that is used to create an instance of the Movie object
    public MyPlace(int mImageDrawable, String mName, String mAddress) {
        this.mImageDrawable = mImageDrawable;
        this.mName = mName;
        this.mAddress = mAddress;
    }

    public int getmImageDrawable() {
        return mImageDrawable;
    }

    public void setmImageDrawable(int mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }
}