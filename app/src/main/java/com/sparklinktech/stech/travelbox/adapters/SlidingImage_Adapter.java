package com.sparklinktech.stech.travelbox.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.sparklinktech.stech.travelbox.Main_Activity;
import com.sparklinktech.stech.travelbox.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter
{


    private ArrayList<String> IMAGES2;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context,ArrayList<String> IMAGES)
    {
        this.context = context;
        this.IMAGES2=IMAGES;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public int getCount()
    {
        return Main_Activity.total_images2/*IMAGES2.size()*/;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position)
    {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        Log.e("IMAGES     >>>    ",""+IMAGES2.get(position));
        Picasso.with(context).load(IMAGES2.get(position))
                .into(imageView);




        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader)
    {

    }

    @Override
    public Parcelable saveState()
    {
        return null;
    }



}