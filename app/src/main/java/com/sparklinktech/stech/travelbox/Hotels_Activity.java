package com.sparklinktech.stech.travelbox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.animation.MyBounceInterpolator;
import com.sparklinktech.stech.travelbox.helpers.Space;
import com.sparklinktech.stech.travelbox.models.Hotelimage;
import com.sparklinktech.stech.travelbox.models.Hotelservice;
import com.sparklinktech.stech.travelbox.models.Product;
import com.sparklinktech.stech.travelbox.models.Restaurant;
import com.sparklinktech.stech.travelbox.models.Store_;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Hotels_Activity extends AppCompatActivity
{
    Boolean isRefreshing = false;
    int add_day = 0;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button checkin,checkout;
    int total_days=0,day_chekin=0,day_checkout=0,month_chekin=0,year_chekin=0,month_chekout=0;
    Boolean dob_flag= false;
    // Progress Dialog Object
    private ProgressDialog mProgressDialog;
    Menu mMenuActionBar;
    static String[] url_image=null;
    ImageView iv;
    TextView name_p, desc_p, price_p,total_images_,one_night;
    ImageButton iv_p;
    static int total_images;
    private String hid, sid, wishlist;
    private Boolean wishlist_flag = false;
    private ArrayList<Restaurant> mArrayList;
     static ArrayList<Hotelimage> mArrayListHotelImages;
    private ArrayList<Hotelservice> mArrayListHotelService;

    private ArrayList<Store_> storeArrayList;

    private RecyclerView mRecyclerViewDetails, mRecyclerViewHotelServices,
            mRecyclerViewHotelImages;
    private ___RestaurantDetailsAdapter mAdapter;
    private ___HotelServicesAdapter mAdapterHotelServicesAdapter;
    private ___HotelImagesAdapter mAdapterHotelImagesAdapter;
    Animation myAnim;




    private StoreAdapter2 storeAdapter;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHot);




//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_backspace_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);*/
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       // getSupportActionBar(toolbar).setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Status bar :: Transparent
        Window window = this.getWindow();


           // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
           // window.setStatusBarColor(getResources().getColor(R.color.green_light));
           // window.setBackgroundDrawable(getDrawable(R.drawable.actionbar_back_gradient));
           // window.setBackgroundDrawableResource(R.drawable.actionbar_back_gradient2);
            //window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.bg_screen1));
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbar_back_gradient));


        toolbar.setTitle("");
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {

            hid = b.getString("hid");
            Log.e("HID  <--> ", "" + hid);


            wishlist = b.getString("wishlist");
            Log.e("WISHLIST  <--> ", "" + wishlist);

            if (wishlist.equals("1")) {
                wishlist_flag = true;
                Log.e("HERER  <--> ", "");

            } else {
                wishlist_flag = false;
            }
        }
        total_images_ = (TextView)findViewById(R.id.total_images);
        one_night = (TextView)findViewById(R.id.one_night);

        checkin = (Button) findViewById(R.id.checkin);
        checkout = (Button) findViewById(R.id.checkout);


        initRecyclerViewHotelImages();
            loadJSONHotelImages();

            initRecyclerViewHotelDetails();
            loadJSONHotelDetails();

            initRecyclerViewHotelServices();
            loadJSONHotelServices();




            /*//==================  CollapsingToolbar  ==========================================================

            final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                    findViewById(R.id.collapse_toolbar_layout_hoteldetails);
            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout_HotelDetails);
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = true;
                int scrollRange = -1;

                @SuppressLint("NewApi")
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0)
                    {
                        ViewGroup transitionsContainer;
                   *//* TransitionSet set_1 = new TransitionSet()

                            .addTransition(new Slide(Gravity.START))
                            .setInterpolator(isShow ? new LinearOutSlowInInterpolator() :
                                    new LinearOutSlowInInterpolator());
                    TransitionManager.beginDelayedTransition(appBarLayout,set_1);*//*
                        collapsingToolbarLayout.setCollapsedTitleTextAppearance(
                                R.style.TextAppearance_MyApp_Title_Collapsed);
                        collapsingToolbarLayout.setTitle("");
                        //collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
                        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                        collapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.MONOSPACE);
                        isShow = true;
                    } else if(isShow) {
                        collapsingToolbarLayout.setExpandedTitleTextAppearance(
                                R.style.TextAppearance_MyApp_Title_Expanded);

                        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM+Gravity.CENTER_HORIZONTAL);
                        collapsingToolbarLayout.setTitle("");
                        //collapsingToolbarLayout.setExpandedTitleMargin(180,50,50,50);
                        collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);
                        collapsingToolbarLayout.setExpandedTitleTypeface(Typeface.MONOSPACE);

//carefull there should a space between double quote otherwise it wont work
                        isShow = false;
                    }
                }
            });
*/
        }

    private void myAnimation()
    {
        //=====================  Animation   ===============================================
        myAnim = AnimationUtils.loadAnimation(this, R.anim.anim1);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        /*MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);*/
    }
    //=========================================================================================================

    private void initRecyclerViewHotelImages()                //Call >>> 1.1
    {
        mRecyclerViewHotelImages = (RecyclerView) findViewById(
                R.id.card_recycler_view_Hotelimagess);
        //Create new GridLayoutManager
        /*GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);*///reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerViewHotelImages.setLayoutManager(layoutManager);


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
            mProgressDialog = new ProgressDialog(Hotels_Activity.this);
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
                    /*Log.e("P JSON BODY  ===>>>>>", "" + response.body());
                    assert jsonResponse != null;*/
                    //mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                    //Log.e("P JSON RESPONSE ==>>>>>",""+mArrayList.get(0).getPname());

                    assert jsonResponse != null;
                    mArrayListHotelImages = new ArrayList<>(jsonResponse.getHotelimages());

                    mAdapterHotelImagesAdapter = new ___HotelImagesAdapter(mArrayListHotelImages);
                    total_images = mArrayListHotelImages.size();
                    url_image = new String[total_images];

                    for (int j=0;j<total_images;j++)
                    {
                        url_image[j]=mArrayListHotelImages.get(j).getHotelImage();
                        //i.putExtra("image"+j,""+url_image[j]);
                    }

                    total_images_.setText(""+mArrayListHotelImages.size()+" PHOTOS ");

                   // Toast.makeText(Hotels_Activity.this, ""+mArrayListHotelImages.size(), Toast.LENGTH_SHORT).show();
                    mRecyclerViewHotelImages.setAdapter(mAdapterHotelImagesAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        } else {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(Hotels_Activity.this);
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
    }

    //=========================================================================================================


//=========================================================================================================

    private void initRecyclerViewHotelDetails()                //Call >>> 1.1
    {
        mRecyclerViewDetails = (RecyclerView) findViewById(R.id.card_recycler_view_Hoteldetails);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewDetails.setLayoutManager(gridLayoutManager);


        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
        mRecyclerViewDetails.addItemDecoration(new Space(1, 0, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }

//=========================================================================================================

    private void loadJSONHotelDetails()                 //Call >>> 2.1
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Hotels_Activity.this);
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

            Call<RestaurantFetch> call = request.getHotelDetails(hid);
            call.enqueue(new Callback<RestaurantFetch>()
            {
                @Override
                public void onResponse(@NonNull Call<RestaurantFetch> call, @NonNull Response<RestaurantFetch> response) {

                    RestaurantFetch jsonResponse = response.body();
                    /*Log.e("P JSON BODY  ===>>>>>", "" + response.body());
                    assert jsonResponse != null;*/
                    //mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                    //Log.e("P JSON RESPONSE ==>>>>>",""+mArrayList.get(0).getPname());

                    assert jsonResponse != null;
                    mArrayList = new ArrayList<>(jsonResponse.getRestaurant());
                    mAdapter = new ___RestaurantDetailsAdapter(mArrayList);
                    mRecyclerViewDetails.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        } else {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(Hotels_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loadJSONHotelDetails();
                }
            });

            ab.show();
        }
    }

    //=========================================================================================================

    private void initRecyclerViewHotelServices()                //Call >>> 1.1
    {
        mRecyclerViewHotelServices = (RecyclerView) findViewById(R.id.card_recycler_view_HotelServices);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.HORIZONTAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewHotelServices.setLayoutManager(gridLayoutManager);


        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
       // mRecyclerViewHotelServices.addItemDecoration(new Space(1, 0, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }

//=========================================================================================================

    private void loadJSONHotelServices()                 //Call >>> 2.1
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Hotels_Activity.this);
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

            Call<RestaurantFetch> call = request.getHotelServices(hid);
            call.enqueue(new Callback<RestaurantFetch>()
            {
                @Override
                public void onResponse(@NonNull Call<RestaurantFetch> call,
                                       @NonNull Response<RestaurantFetch> response) {

                    RestaurantFetch jsonResponse = response.body();
                    /*Log.e("P JSON BODY  ===>>>>>", "" + response.body());
                    assert jsonResponse != null;*/
                    //mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                    //Log.e("P JSON RESPONSE ==>>>>>",""+mArrayList.get(0).getPname());

                    assert jsonResponse != null;
                    mArrayListHotelService = new ArrayList<>(jsonResponse.getHotelservices());
                    mAdapterHotelServicesAdapter = new ___HotelServicesAdapter(mArrayListHotelService);
                    mRecyclerViewHotelServices.setAdapter(mAdapterHotelServicesAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t)
                {
                    Log.e("Error", t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        } else {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(Hotels_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loadJSONHotelServices();
                }
            });

            ab.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hotel_menu, menu);
        mMenuActionBar = menu;
        if(isRefreshing)
        {
            mMenuActionBar.getItem(0).setActionView(null);


        }

        if(wishlist_flag)
            menu.getItem(0).setIcon(R.drawable.wishlist_red);
        else
            menu.getItem(0).setIcon(R.drawable.wishlist_white);



        return true;
    }

    private void updateActionBar()
    {
        if (mMenuActionBar != null)
        {
            MenuItem menuItem = mMenuActionBar.findItem(R.id.hotel_menu_wishlist);
            if (menuItem != null)
            {
                isRefreshing = true;

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                 iv = (ImageView) inflater.inflate(R.layout.iv_wishlist,null);


                Animation rotation = AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.anim1);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
                rotation.setInterpolator(interpolator);
                rotation.setRepeatCount(0);
                iv.startAnimation(rotation);
                if(wishlist_flag)
                {
//                    menuItem.getActionView().startAnimation(myAnim);
                    menuItem.setIcon(R.drawable.wishlist_red);
                    iv.setImageResource(R.drawable.wishlist_red);


                }
                else
                {
                    menuItem.setIcon(R.drawable.wishlist_white);
                    iv.setImageResource(R.drawable.wishlist_white);

                }
                //refresh();
                // item.setVisible(false);
                //menu.getItem(0).setActionView(iv_book);
                rotation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {





                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {

                        invalidateOptionsMenu();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {                }
                });
                MenuItemCompat.setActionView(menuItem,iv);



                //menuItem.setIcon(R.drawable.wishlist_red);
               // invalidateOptionsMenu();
            }
        }
    }

//=========================================================================================================






    //=========================================================================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;



            case R.id.hotel_menu_wishlist:
                wishlistFlag();
                break;

            case R.id.share:


            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey Check Out TRAVEL BOX Android App At: https://sparklinktech.com/downloads/TravelBox.apk");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public void order(View view)
    {

        Intent i = new Intent(Hotels_Activity.this,ExtraServices.class);
        i.putExtra("hid",hid);
        startActivity(i);
    }


    public void wishlistFlag()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectToServer.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CategoryInterface request = retrofit.create(CategoryInterface.class);
        Call<RestaurantFetch> call;

        if(wishlist_flag)
        {
            String wish = "0";
            wishlist_flag = false ;
            updateActionBar();
            call = request.setWishlistHotels(hid,wish);
        }
        else
        {
            String wish = "1";
            wishlist_flag = true ;
            updateActionBar();
            call = request.setWishlistHotels(hid,wish);
        }


        call.enqueue(new Callback<RestaurantFetch>()
        {
            @Override
            public void onResponse(@NonNull Call<RestaurantFetch> call, @NonNull Response<RestaurantFetch> response)
            {
                Log.e("RESPONSE        >>>>",response.message());

                // Log.e("JSON RESPONSE=====>>>>>",""+mArrayList.get(1));
                // Log.e("JSON RESPONSE=====>>>>>","Name  <-->  "+mArrayList.get(2).getPname());
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t) {
                Log.e("Error  ***",t.getMessage());
            }
        });
    }

    public void checkindtp(View view)
    {
        // Get Current Date
        /*final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);*/

        Calendar c = Calendar.getInstance();
        //c.add(Calendar.YEAR, -2); // subtract 2 years from now
        //datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c.add(Calendar.MONTH, 6); // add 4 years to min date to have 2 years after now
        //datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth)
                    {
                        year_chekin = year;
                        Log.e("Year Current  >>>", " " + mYear);
                        Log.e("Year Selected >>>", " " + year);
                        Log.e("Year Checkin  >>>", " " + year_chekin);

                        //Toast.makeText(Registration_.this, "dayOfMonth + \"-\" + (monthOfYear + 1) + \"-\" + year", Toast.LENGTH_SHORT).show();
if((mYear-1)==year_chekin)
{

    checkin.setText("Check In : 11 AM\n" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
    dob_flag = true;
    //Toast.makeText(getApplicationContext(),
    //       "Checkin Month >> "+(monthOfYear+1), Toast.LENGTH_SHORT).show();
    day_chekin = dayOfMonth;
    month_chekin = monthOfYear + 1;

    checkout.setEnabled(true);
}
else
    {

    Toast.makeText(Hotels_Activity.this, "Please Select Current Year", Toast.LENGTH_SHORT).show();
}
                    }
                }, mYear, mMonth-6, mDay);


        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+1000);


        datePickerDialog.show();
    }




    public void checkoutdtp(View view)
    {
        try {
            if (!checkout.isEnabled()) {
                Toast.makeText(this, "Please Select Check-In Date First.", Toast.LENGTH_SHORT).show();
            } else {
                // Get Current Date
        /*final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);*/
                int dom = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int monthCurrentDay = Calendar.getInstance().get(Calendar.MONTH);
                monthCurrentDay = monthCurrentDay + 1;
                Log.e("MONTH >>>", " " + monthCurrentDay);

                //Toast.makeText(this, "MONTH >>> "+month, Toast.LENGTH_SHORT).show();
                Calendar c = Calendar.getInstance();
                Calendar c2 = Calendar.getInstance();
                //c.add(Calendar.YEAR, -2); // subtract 2 years from now
                //datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());




                if (monthCurrentDay == 1 || monthCurrentDay == 3 || monthCurrentDay == 5
                        || monthCurrentDay == 7 || monthCurrentDay == 8 || monthCurrentDay == 10 || monthCurrentDay == 12) {
                    add_day = 32;
                    Log.e("add_day >>>", "32 >>> ");
                    //Toast.makeText(this, "ADD_DAY >>> 32", Toast.LENGTH_SHORT).show();

                } else if (monthCurrentDay == 4 || monthCurrentDay == 6 || monthCurrentDay == 9 || monthCurrentDay == 11) {
                    add_day = 31;
                    Log.e("add_day >>>", "31 >>> ");

                    //Toast.makeText(this, "ADD_DAY >>> 31", Toast.LENGTH_SHORT).show();

                } else if (monthCurrentDay == 2) {
                    int yr = Calendar.getInstance().get(Calendar.YEAR);

                    GregorianCalendar cal = new GregorianCalendar();

                    if (cal.isLeapYear(yr)) {
                        add_day = 29;
                        Log.e("add_day >>>", "28 >>> ");

                        //Toast.makeText(this, "ADD_DAY >>> 29", Toast.LENGTH_SHORT).show();

                    } else {
                        add_day = 30;
                        Log.e("add_day >>>", "30 >>> ");

                        //Toast.makeText(this, "ADD_DAY >>> 30", Toast.LENGTH_SHORT).show();

                    }
                }
                if (month_chekin >= monthCurrentDay) {
                    //      10 - 9
                    int temp = month_chekin - monthCurrentDay;
                    Log.e("TEMP >>>", "MONTH_CHEKIN-MONTH >>> " + month_chekin + "-" + monthCurrentDay + " = " + temp);
                    if (temp > 0) {
                        switch (temp) {
                            case 1:
                                c.add(Calendar.DAY_OF_YEAR, (day_chekin - dom) + (add_day));

                                Log.e("[1] C.DAY_OF_YEAR >>>", "(day_chekin - dom)+(add_day) >>> (" + day_chekin + " - " + dom + ") + ( "
                                        + add_day + ")");
                                break;

                            case 2:
                                c.add(Calendar.DAY_OF_YEAR, (day_chekin - dom) + (add_day + (add_day - 2)));

                                Log.e("[2] C.DAY_OF_YEAR >>>", "(day_chekin - dom)+(add_day) >>> (" + day_chekin + " - " + dom + ") + ( "
                                        + add_day + ")");
                                break;

                            case 3:
                                c.add(Calendar.DAY_OF_YEAR, (day_chekin - dom) + (add_day + add_day) + (add_day - 3));

                                Log.e("[3] C.DAY_OF_YEAR >>>", "(day_chekin - dom)+(add_day) >>> (" + day_chekin + " - " + dom + ") + ( "
                                        + add_day + ")");
                                break;

                            case 4:
                                c.add(Calendar.DAY_OF_YEAR, (day_chekin - dom) + (add_day * 3) + (add_day - 5));

                                Log.e("[4] C.DAY_OF_YEAR >>>", "(day_chekin - dom)+(add_day) >>> (" + day_chekin + " - " + dom + ") + ( "
                                        + add_day + ")");
                                break;

                            case 5:
                                c.add(Calendar.DAY_OF_YEAR, (day_chekin - dom) + (add_day));

                                Log.e("[5] C.DAY_OF_YEAR >>>", "(day_chekin - dom)+(add_day) >>> (" + day_chekin + " - " + dom + ") + ( "
                                        + add_day + ")");
                                break;

                            case 6:
                                c.add(Calendar.DAY_OF_YEAR, (day_chekin - dom) + (add_day));

                                Log.e("[6] C.DAY_OF_YEAR >>>", "(day_chekin - dom)+(add_day) >>> (" + day_chekin + " - " + dom + ") + ( "
                                        + add_day + ")");
                                break;
                        }


            /*c2.add(Calendar.MONTH, 1);
            Log.e("C.D_O_M > "+Calendar.DAY_OF_MONTH,"30 ");
            Log.e("C.MONTH > "+Calendar.MONTH,"<< "+month_chekin);*/


                    } else {

                        c.add(Calendar.DAY_OF_YEAR, (day_chekin - dom) + 1);
                        Log.e("[0] C.DAY_OF_YEAR >>>", "(day_chekin - dom) + 1 >>> (" + day_chekin + " - " + dom + ") + 1");

                /*c2.add(Calendar.DAY_OF_MONTH, 30);
                Log.e("C.D_O_M > "+Calendar.DAY_OF_MONTH,"30 ");
                Log.e("C.MONTH > "+Calendar.MONTH,"<< "+month_chekin);*/

                    }
                } else {
                    c.add(Calendar.YEAR, Calendar.YEAR + 1);
                }
                //datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //Toast.makeText(Registration_.this, "dayOfMonth + \"-\" + (monthOfYear + 1) + \"-\" + year", Toast.LENGTH_SHORT).show();
                                day_checkout = dayOfMonth;
                                checkout.setText("Check Out : 10 AM\n" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                dob_flag = true;
                                if (day_checkout > day_chekin)
                                {
                                    total_days = day_checkout - day_chekin;
                                    if(total_days==1)
                                    {
                                        one_night.setText("" + total_days + " Night");
                                    }
                                    else {
                                        one_night.setText("" + total_days + " Nights");
                                    }
                                }
                                else
                                {
                                    total_days = day_checkout+(add_day-day_chekin-1);
                                    one_night.setText("" + total_days + " Nights");
                                }


                                // Toast.makeText(getApplicationContext(),
                                //        "Checkout >> "+dayOfMonth, Toast.LENGTH_SHORT).show();


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

                //if(mMonth)
                c2.set(Calendar.MONTH, month_chekin - 1);
                c2.set(Calendar.DAY_OF_MONTH, day_chekin);
                c2.add(Calendar.DAY_OF_MONTH, 14);
                datePickerDialog.getDatePicker().setMaxDate(c2.getTimeInMillis());


                datePickerDialog.show();
            }
        }
        catch (Exception e)
        {
            Log.e("EXCP >>>> ", ""+e);

        }
    }



    //========================================================================================
}
