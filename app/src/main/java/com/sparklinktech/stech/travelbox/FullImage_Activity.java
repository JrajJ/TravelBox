package com.sparklinktech.stech.travelbox;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.ImageTouchGesture.CustomPagerAdapter;
import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.adapters.SlidingImage_Adapter;
import com.sparklinktech.stech.travelbox.models.Hotelimage;
import com.sparklinktech.stech.travelbox.models.Restaurant;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FullImage_Activity extends AppCompatActivity
{
    //=================================================================

    ScaleGestureDetector scaleGestureDetector;
    ImageView imageView;
    Matrix matrix;
    ViewPager viewPager;
    Drawable rightarrow, leftarrow;
    Button buttonnext, buttonprev;
    float saveScale;

    private static final int MAX_CLICK_DURATION = 300;
    private long startClickTime;

    //=================================================================


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.h1,
            R.drawable.h2,R.drawable.h3,R.drawable.h4};
    private ArrayList<Integer> ImagesArray;

    private ProgressDialog mProgressDialog;

    private ArrayList<Hotelimage> mArrayListHotelImagesFullScreen;
    private RecyclerView mRecyclerViewHotelImagesFullScreen;
    private ___HotelImagesAdapter mAdapterHotelImagesAdapterFullScreen;

    String hid =null;
    int imagecount=0;
    TextView _imagecount;
    @SuppressLint("Range")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        _imagecount = (TextView)findViewById(R.id.fullimage_totalPhotos);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null)
        {
            hid = b.getString("hid");
            Log.e("HID  <--> ", "" + hid);

            imagecount = Integer.valueOf(b.getString("imagecount"));
            Log.e("Image Count  <--> ", "" + imagecount);

            _imagecount.setText(""+imagecount+" PHOTOS");


        }
        buttonnext = (Button) findViewById(R.id.next);
        buttonprev = (Button) findViewById(R.id.prev);




        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //  viewPagerArrowIndicator = (ViewPagerArrowIndicator) findViewById(R.id.viewPagerArrowIndicator);
        imageView = (ImageView) findViewById(R.id.imageView);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(this,Hotels_Activity.mArrayListHotelImages);
        viewPager.setAdapter(customPagerAdapter);
        //  viewPagerArrowIndicator.bind(viewPager);
        //  viewPagerArrowIndicator.setArrowIndicatorRes(R.drawable.ic_arrow_left, R.drawable.ic_arrow_right);
        //  rightarrow = getResources().getDrawable(R.drawable.ic_arrow_right);
        // rightarrow.setColorFilter(getResources().getColor(R.color.RED), PorterDuff.Mode.SRC_ATOP);
        // leftarrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        // leftarrow.setColorFilter(getResources().getColor(R.color.RED), PorterDuff.Mode.SRC_ATOP);


        matrix = new Matrix();
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem + 1);
            }
        });
        buttonprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItem - 1);
            }
        });


        /*initRecyclerViewHotelImages();
        loadJSONHotelImages();*/
        //initSliderImagesView();

    }

  /*  private void initSliderImagesView()             //Call >>> SliderImagesView
    {
        ImagesArray = new ArrayList<Integer>();
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pagerfullimage);


        mPager.setAdapter(new SlidingImage_Adapter(FullImage_Activity.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicatorfullimage);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(4 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };


        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position)
            {
                currentPage = position;



            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }*/

    //=========================================================================================================
/*
    private void initRecyclerViewHotelImages()                //Call >>> 1.1
    {
        mRecyclerViewHotelImagesFullScreen = (RecyclerView) findViewById(
                R.id.card_recycler_view_Hotelimage_fullScreen);
        //Create new GridLayoutManager
        *//*GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);*//*//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewHotelImagesFullScreen.setLayoutManager(layoutManager);


        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
        //mRecyclerViewHotelImages.addItemDecoration(new Space(1, 0, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }

//=========================================================================================================

    private void loadJSONHotelImages()                 //Call >>> 2.1
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(FullImage_Activity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Sending Your Data");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConnectToServer.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            CategoryInterface request = retrofit.create(CategoryInterface.class);

            Call<RestaurantFetch> call = request.getHotelImages(hid);
            call.enqueue(new Callback<RestaurantFetch>()
            {
                @Override
                public void onResponse(@NonNull Call<RestaurantFetch> call, @NonNull Response<RestaurantFetch> response) {

                    RestaurantFetch jsonResponse = response.body();
                    *//*Log.e("P JSON BODY  ===>>>>>", "" + response.body());
                    assert jsonResponse != null;*//*
                    //mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                    //Log.e("P JSON RESPONSE ==>>>>>",""+mArrayList.get(0).getPname());
                    assert jsonResponse != null;


                    mArrayListHotelImagesFullScreen = new ArrayList<>(jsonResponse.getHotelimages());

                    mAdapterHotelImagesAdapterFullScreen = new ___HotelImagesAdapter(mArrayListHotelImagesFullScreen);
                    *//*total_images = mArrayListHotelImages.size();
                    url_image = new String[total_images];
*//*
                    *//*for (int j=0;j<total_images;j++)
                    {
                        url_image[j]=mArrayListHotelImages.get(j).getHotelImage();
                        //i.putExtra("image"+j,""+url_image[j]);
                    }*//*

                    //total_images_.setText(""+mArrayListHotelImages.size()+" PHOTOS > ");

                    // Toast.makeText(Hotels_Activity.this, ""+mArrayListHotelImages.size(), Toast.LENGTH_SHORT).show();
                    mRecyclerViewHotelImagesFullScreen.setAdapter(mAdapterHotelImagesAdapterFullScreen);
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        } else {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(FullImage_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loadJSONHotelImages();
                }
            });

            ab.show();
        }
    }*/


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        scaleGestureDetector.onTouchEvent(event);


        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            float SF = detector.getScaleFactor();
            SF = Math.max(0.1f, Math.min(SF, 0.5f));
            matrix.setScale(SF, SF);
            imageView.setImageMatrix(matrix);
            return true;
        }
    }

}