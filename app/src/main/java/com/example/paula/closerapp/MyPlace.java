package com.example.paula.closerapp;

public class MyPlace {

    private int mImageDrawable;
    private String mName;
    private String mAddress;

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