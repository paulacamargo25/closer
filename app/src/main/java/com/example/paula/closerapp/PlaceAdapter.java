package com.example.paula.closerapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class PlaceAdapter extends ArrayAdapter<MyPlace>
{
    private Context mContext;
    private List<MyPlace> placesList = new ArrayList<>();

    public PlaceAdapter(@NonNull Context context, @LayoutRes ArrayList<MyPlace> list) {
        super(context, 0 , list);
        mContext = context;
        placesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_listview,parent,false);

        MyPlace currentPlace = placesList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.thumbnail);
        Bitmap myPictureBitmap = currentPlace.getmImageDrawable();
        //if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            //myPictureBitmap = Bitmap.createScaledBitmap(myPictureBitmap, image.getWidth(),image.getHeight(),true);
        //}
        image.setImageBitmap(myPictureBitmap);



        TextView name = (TextView) listItem.findViewById(R.id.textView);
        name.setText(currentPlace.getmName());

        TextView release = (TextView) listItem.findViewById(R.id.textView2);
        release.setText(currentPlace.getmAddress());

        return listItem;
    }

}