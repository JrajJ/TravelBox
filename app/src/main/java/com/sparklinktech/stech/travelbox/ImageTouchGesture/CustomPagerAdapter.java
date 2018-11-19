package com.sparklinktech.stech.travelbox.ImageTouchGesture;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sparklinktech.stech.travelbox.R;
import com.sparklinktech.stech.travelbox.models.Hotelimage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CustomPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int[] mResources = {
            R.drawable.h1,
            R.drawable.h2,
            R.drawable.h3,
            R.drawable.h4


    };
    ArrayList<Hotelimage> arrayList;



    public CustomPagerAdapter(Context context, ArrayList<Hotelimage> arrayList)
    {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;


    }


    @Override
    public int getCount()
    {
        if(arrayList != null)
        {
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.my_viewpager, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewTouch);
        Picasso.with(mContext).load(arrayList.get(position).getHotelImage())
                .into(imageView);

        //imageView.setImageResource(mResources[position]);

        container.addView(itemView);
        return itemView;
    }



    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchViewPagerIMageView imgDisplay;

        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.activity_main, container,
                false);

        imgDisplay = (TouchViewPagerIMageView) itemView.findViewById(R.id.imageView);
        imgDisplay.setImageResource(mResources[position]);
        ((ViewPager) container).addView(itemView);
        return itemView;
    }*/


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
