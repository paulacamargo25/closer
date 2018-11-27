package com.example.paula.closerapp;

import android.graphics.Bitmap;

public class MyPlace {

    private Bitmap mImageDrawable;
    private String mName;
    private String mAddress;

    public MyPlace(Bitmap mImageDrawable, String mName, String mAddress) {
        this.mImageDrawable = mImageDrawable;
        this.mName = mName;
        this.mAddress = mAddress;
    }

    public Bitmap getmImageDrawable() {
        return mImageDrawable;
    }

    public void setmImageDrawable(Bitmap mImageDrawable) {
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