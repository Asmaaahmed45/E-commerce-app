package com.example.freshmarket;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;



public class PicassoClient {
    static String imgs;



    public static void downloadImage(Context c, String url, ImageView img)
    {

        if(url != null && url.length()>0)
        {
            Picasso.with(c).load(url).placeholder(R.drawable.logo).into(img);
        }
        else
        {
            Picasso.with(c).load(R.drawable.logo).into(img);
        }
    }
}