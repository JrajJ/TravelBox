package com.sparklinktech.stech.travelbox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.adapters.SlidingImage_Adapter;
import com.sparklinktech.stech.travelbox.animation.MyBounceInterpolator;
import com.sparklinktech.stech.travelbox.helpers.Space;
import com.sparklinktech.stech.travelbox.models.Hotelimage;
import com.sparklinktech.stech.travelbox.models.Restaurant;
import com.sparklinktech.stech.travelbox.sharedpref.SessionManager;

import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sparklinktech.stech.travelbox.Hotels_Activity.MY_PERMISSIONS_REQUEST_LOCATION;

public class Main_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    Activity getMain_Activity;
    Handler mHandler;
    Boolean OK=false;
    Button btnStartProgress;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;

    static String[] url_image2=null;
Boolean isRefreshing=false,loadJSONHotelImagesFLAG=false;
    public static int total_images2;
    static ArrayList<Hotelimage> mArrayListHotelImages2;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    Animation myAnim;
    private View myLayout_inflater;
    private LayoutInflater layoutInflater;
    // Progress Dialog Object
    private ProgressDialog mProgressDialog;

    private ArrayList<Restaurant> mArrayList,mArrayListDealsForYou;

    private RestaurantAdapter mRestaurantAdapter;
    private DealsForYouAdapter mDealsForYouAdapter;
    private SpecialsAdapter mSpecialsAdapter;

    ___HotelImagesAdapter mAdapterHotelImagesAdapter;

    private RecyclerView mRecyclerViewRecommended,mRecyclerViewTrending,
            mRecyclerViewDealsForYou,mRecyclerViewCat,mRecyclerViewAllHotels,
            mRecyclerViewAllSpecials;

    String response;
    private Boolean mainActivityTemp = false,trendingTemp=false,
            bestDealsTemp=false,recommendedTemp=false;
    JSONObject globalJson= null;
    static Context context;
    private int id;
    Toolbar toolbar;
    JSONObject jsonObject;
    private Boolean navItemSelection = false ;
    private FrameLayout fl;

    //========================= Sliding Images Banner Add ================================================================================
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.h2,
            R.drawable.h3,R.drawable.h4,R.drawable.hh1,R.drawable.hh2};
    private ArrayList<String> ImagesArray2;
    private ArrayList<Integer> ImagesArray;
    ScrollView sv;
    NavigationView mNavigationView;
    SessionManager manager;
        Activity main_Activity;

    //=========================================================================================================


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl = (FrameLayout)findViewById(R.id.frame_main_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        manager = new SessionManager();



        setSupportActionBar(toolbar);

        context = Main_Activity.this;
        getMain_Activity = Main_Activity.this;
        myAnimation();

        //====================================================================

       /* if(canToggleGPS())
        {
            turnGPSOn();
        }*/

        //displayPromptForEnablingGPS(main_Activity);
        //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));


        //=========================================================================================================







        //=========================================================================================================
        // inflate (create) another copy of our custom layout

        layoutInflater = getLayoutInflater();

        //================  Navigation Drawer >> Navigation View  =========================================================================================
        mNavigationView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

                //mNavigationView.removeOnLayoutChangeListener( this );

                TextView tvName = (TextView) mNavigationView.findViewById(R.id.nav_text_name);
                String name = manager.getPreferences(getApplicationContext(),"username");
                tvName.setText(name);

                TextView tvEmail = (TextView) mNavigationView.findViewById(R.id.nav_text_email);
                String email = manager.getPreferences(getApplicationContext(),"useremail");
                tvEmail.setText(email);

                TextView tvContact = (TextView) mNavigationView.findViewById(R.id.nav_text_contact);
                String contact = manager.getPreferences(getApplicationContext(),"usercontact");
                tvContact.setText(contact);
            }
        });

        //==================  CollapsingToolbar  ==========================================================

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapse_toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
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
                   /* TransitionSet set_1 = new TransitionSet()

                            .addTransition(new Slide(Gravity.START))
                            .setInterpolator(isShow ? new LinearOutSlowInInterpolator() :
                                    new LinearOutSlowInInterpolator());
                    TransitionManager.beginDelayedTransition(appBarLayout,set_1);*/
                    collapsingToolbarLayout.setCollapsedTitleTextAppearance(
                            R.style.TextAppearance_MyApp_Title_Collapsed);
                    collapsingToolbarLayout.setTitle("Travel Box");
                    //collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
                    collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                    collapsingToolbarLayout.setCollapsedTitleTypeface(Typeface.MONOSPACE);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setExpandedTitleTextAppearance(
                            R.style.TextAppearance_MyApp_Title_Expanded);

                    collapsingToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM+Gravity.CENTER_HORIZONTAL);
                    collapsingToolbarLayout.setTitle("Travel Box");
                    //collapsingToolbarLayout.setExpandedTitleMargin(180,50,50,50);
                    collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
                    collapsingToolbarLayout.setExpandedTitleTypeface(Typeface.MONOSPACE);

//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


//========================  Drawer Layout  =================================================================
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("JASRAJ");

        //displayLocationSettingsRequest(context);
        checkLocationPermission();

    }

    private void myAnimation()
    {
        //=====================  Animation   ===============================================
        myAnim = AnimationUtils.loadAnimation(this, R.anim.anim3);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        /*MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);*/
    }




    //=========================================================================================================


    private void loadJSON____AllHotels()                 //<<<< Hotels >>>>//
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Main_Activity.this);
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

            Call<RestaurantFetch> call = request.getAllRestaurant();
            call.enqueue(new Callback<RestaurantFetch>()
            {
                @Override
                public void onResponse(@NonNull Call<RestaurantFetch> call, @NonNull Response<RestaurantFetch> response) {

                    RestaurantFetch jsonResponse = response.body();
                    //Log.e("JSON BODY=====>>>>>",""+response.body());


                    Log.e("JSON CALL=====>>>>>",""+call.toString());
                    Log.e("JSON BODY=====>>>>>",""+response.body());
                    assert jsonResponse != null;
//                    Log.e("JSON BODY2=====>>>>>",""+ jsonResponse.getService().toString());
                    mArrayList= new ArrayList<>(jsonResponse.getRestaurant());


                    //mArrayList.addAll(Arrays.asList(jsonResponse.getRestaurant()));
                    //Log.e("JSON RESPONSE=====>>>>>",""+mArrayList.get(1).getPname());

                    mRestaurantAdapter = new RestaurantAdapter(mArrayList);
                    //mAdapter.notifyDataSetChanged();
                    mRecyclerViewAllHotels.setAdapter(mRestaurantAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t) {
                    Log.e("Error",t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        }
        /*else
        {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(Main_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    initRecyclerView____AllHotels();
                }
            });

            ab.show();
        }*/
    }

    //=========================================================================================================

    private void initRecyclerView____AllHotels()       //<<<< Hotels >>>>//
    {
        mRecyclerViewAllHotels = (RecyclerView)findViewById(R.id.card_recycler_view_all_hotels);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                3,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewAllHotels.setLayoutManager(gridLayoutManager);

        /*//Crete new EndlessScrollListener to endless recyclerview loading
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount)
            {
                if (!productsAdapter.loading)
                    feedData();
            }
        };*/
        //to give loading item full single row
        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (productsAdapter.getItemViewType(position)) {
                    case ProductsAdapter.PRODUCT_ITEM:
                        return 1;
                    case ProductsAdapter.LOADING_ITEM:
                        return 2; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });*/
        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
       // mRecyclerViewAllHotels.addItemDecoration(new Space(4, 15, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }

    //=========================================================================================================


    private void loadJSON____DealsForYou()                 //<<<< DealsForYou >>>>//
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Main_Activity.this);
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

            Call<RestaurantFetch> call = request.getAllRestaurant();
            call.enqueue(new Callback<RestaurantFetch>()
            {
                @Override
                public void onResponse(@NonNull Call<RestaurantFetch> call, @NonNull Response<RestaurantFetch> response) {

                    RestaurantFetch jsonResponse = response.body();
                    //Log.e("JSON BODY=====>>>>>",""+response.body());


                    Log.e("JSON CALL=====>>>>>",""+call.toString());
                    Log.e("JSON BODY=====>>>>>",""+response.body());
                    assert jsonResponse != null;
//                    Log.e("JSON BODY2=====>>>>>",""+ jsonResponse.getService().toString());
                    mArrayListDealsForYou= new ArrayList<>(jsonResponse.getRestaurant());


                    //mArrayList.addAll(Arrays.asList(jsonResponse.getRestaurant()));
                    //Log.e("JSON RESPONSE=====>>>>>",""+mArrayList.get(1).getPname());

                    mDealsForYouAdapter = new DealsForYouAdapter(mArrayListDealsForYou);
                    //mAdapter.notifyDataSetChanged();
                    mRecyclerViewDealsForYou.setAdapter(mDealsForYouAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t) {
                    Log.e("Error",t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        }
        /*else
        {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(Main_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    initRecyclerView____DealsForYou();
                }
            });

            ab.show();
        }*/
    }

    //=========================================================================================================

    private void initRecyclerView____DealsForYou()       //<<<< DealsForYou >>>>//
    {
        mRecyclerViewDealsForYou = (RecyclerView)findViewById(R.id.card_recycler_view_dealsforyou);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                2,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewDealsForYou.setLayoutManager(gridLayoutManager);

        /*//Crete new EndlessScrollListener to endless recyclerview loading
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount)
            {
                if (!productsAdapter.loading)
                    feedData();
            }
        };*/
        //to give loading item full single row
        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (productsAdapter.getItemViewType(position)) {
                    case ProductsAdapter.PRODUCT_ITEM:
                        return 1;
                    case ProductsAdapter.LOADING_ITEM:
                        return 2; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });*/
        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
     //   mRecyclerViewDealsForYou.addItemDecoration(new Space(2, 0, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }
    //=========================================================================================================
    //=========================================================================================================


    private void loadJSON____Specials()                 //<<<< Specials >>>>//
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Main_Activity.this);
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

            Call<RestaurantFetch> call = request.getAllRestaurant();
            call.enqueue(new Callback<RestaurantFetch>()
            {
                @Override
                public void onResponse(@NonNull Call<RestaurantFetch> call, @NonNull Response<RestaurantFetch> response) {

                    RestaurantFetch jsonResponse = response.body();
                    //Log.e("JSON BODY=====>>>>>",""+response.body());


                    Log.e("JSON CALL=====>>>>>",""+call.toString());
                    Log.e("JSON BODY=====>>>>>",""+response.body());
                    assert jsonResponse != null;
//                    Log.e("JSON BODY2=====>>>>>",""+ jsonResponse.getService().toString());
                    mArrayListDealsForYou= new ArrayList<>(jsonResponse.getRestaurant());


                    //mArrayList.addAll(Arrays.asList(jsonResponse.getRestaurant()));
                    //Log.e("JSON RESPONSE=====>>>>>",""+mArrayList.get(1).getPname());

                    mSpecialsAdapter = new SpecialsAdapter(mArrayListDealsForYou);
                    //mAdapter.notifyDataSetChanged();
                    mRecyclerViewAllSpecials.setAdapter(mSpecialsAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t) {
                    Log.e("Error",t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        }
        /*else
        {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(Main_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    initRecyclerView____DealsForYou();
                }
            });

            ab.show();
        }*/
    }

    //=========================================================================================================

    private void initRecyclerView____Specials()       //<<<< Specials >>>>//
    {
        mRecyclerViewAllSpecials = (RecyclerView)findViewById(R.id.card_recycler_view_specials);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.HORIZONTAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewAllSpecials.setLayoutManager(gridLayoutManager);

        /*//Crete new EndlessScrollListener to endless recyclerview loading
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount)
            {
                if (!productsAdapter.loading)
                    feedData();
            }
        };*/
        //to give loading item full single row
        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (productsAdapter.getItemViewType(position)) {
                    case ProductsAdapter.PRODUCT_ITEM:
                        return 1;
                    case ProductsAdapter.LOADING_ITEM:
                        return 2; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });*/
        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
        mRecyclerViewAllSpecials.addItemDecoration(new Space(1, 0, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }
    //=========================================================================================================

    private void loadFrameLayout()
    {
        View myLayout = layoutInflater.inflate(R.layout.category_layout, fl, false);
        mRecyclerViewCat = (RecyclerView)myLayout.findViewById(R.id.card_recycler_view_filtercat);

        mRecyclerViewCat.getRecycledViewPool().setMaxRecycledViews(10, 10);

        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                2,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewCat.setLayoutManager(gridLayoutManager);

        mRecyclerViewCat.addItemDecoration(new Space(20, 0, false, 0));


        fl.addView(myLayout);


    }

//=========================================================================================================
private void loadJSONHotelImages()                 //Call >>> 2.1
{
    if (CheckNetwork.isInternetAvailable(getApplicationContext()))
    {
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(Main_Activity.this);
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

        Call<RestaurantFetch> call = request.getHotelImages("3");
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
                mArrayListHotelImages2 = new ArrayList<>(jsonResponse.getHotelimages());

                mAdapterHotelImagesAdapter = new ___HotelImagesAdapter(mArrayListHotelImages2);
                total_images2 = mArrayListHotelImages2.size();
                url_image2 = new String[total_images2];

                for (int j=0;j<total_images2;j++)
                {
                    url_image2[j]=mArrayListHotelImages2.get(j).getHotelImage();
                    Log.e("url_image2[j]  ===>>>>>", "" + url_image2[j]);
                    //i.putExtra("image"+j,""+url_image[j]);
                }


                initSliderImagesView();
                // Toast.makeText(Hotels_Activity.this, ""+mArrayListHotelImages.size(), Toast.LENGTH_SHORT).show();
               // mRecyclerViewHotelImages.setAdapter(mAdapterHotelImagesAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantFetch> call, @NonNull Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
        mProgressDialog.dismiss();
    } else {
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        /*AlertDialog.Builder ab = new AlertDialog.Builder(Main_Activity.this);
        ab.setTitle("No Internet Connection");
        ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        ab.setNeutralButton("Reload", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                if (CheckNetwork.isInternetAvailable(getApplicationContext()))
                {
                    Intent i = new Intent(Main_Activity.this,Main_Activity.class);
                    startActivity(i);
                    finish();
                }
                else
                    {
                        Toast.makeText(Main_Activity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ab.show();*/
    }
}

    //=========================================================================================================


//=========================================================================================================


    private void loadFrameLayoutBack()
    {
        //myLayout_inflater = layoutInflater.inflate(R.layout.content_main_frame, fl, false);
        //initSliderImagesView_inflater();



        //initRecyclerViewAllProduct_inflater();
        //loadJSONAllProducts_inflater();

        //fl.addView(myLayout_inflater);



    }
//=========================================================================================================

    private void initSliderImagesView()             //Call >>> SliderImagesView
    {
        ImagesArray2 = new ArrayList<String>();
        for(int i=0;i<total_images2;i++)
        {
            ImagesArray2.add(url_image2[i]);
            Log.e("url_image2[i] >>>>> ", "" + url_image2[i]);
        }

      mPager = (ViewPager) findViewById(R.id.pager);
      mPager.setAdapter(new SlidingImage_Adapter(Main_Activity.this,ImagesArray2));



        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable()
        {
            public void run()
            {
                if (currentPage == NUM_PAGES)
                {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 0, 10000);



    }


    //=========================================================================================================

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            Log.e("BackPRESS   >>   ","Drawer  ->>    "+navItemSelection);

            drawer.closeDrawer(GravityCompat.START);
        }
        else if(navItemSelection)
        {
            Log.e("BackPRESS   >>   ","Frame Back->>   "+navItemSelection);
            navItemSelection = false;
            //loadFrameLayoutBack();
            mainActivityTemp = false ;
            trendingTemp = false;
            recommendedTemp  = false;
            bestDealsTemp = false;
            toolbar.setTitle(getString(R.string.app_name));
        }
        else
        {
            Log.e("BackPRESS   >>   ","Finish->>   "+navItemSelection);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {

                            Main_Activity.this.finish();


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });





            final AlertDialog alert = builder.create();
            alert.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0)
                {
                    alert.getButton(
                            AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    alert.getButton(
                            AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }
            });

            alert.show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
       // menu.clear();
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        if(isRefreshing)
    menu.getItem(1).setActionView(null);



        Log.e("<<<<<<<<<<<<<<< ","3");
        return true;
    }
//=========================================================================================================
/*public void completeRefresh() {
    refreshItem.getActionView().clearAnimation();
    refreshItem.setActionView(null);
}*/



    //=========================================================================================================

    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.cart)
        {

           // Toast.makeText(context, ""+item.toString(), Toast.LENGTH_SHORT).show();
//            item.getActionView().startAnimation(myAnim);
            isRefreshing = true;

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            ImageView iv = (ImageView) inflater.inflate(R.layout.iv_book,null);


            Animation rotation = AnimationUtils.loadAnimation(
                    getApplicationContext(), R.anim.anim1);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
            rotation.setInterpolator(interpolator);
            //rotation.setRepeatCount(0);
            iv.startAnimation(rotation);

            //refresh();
           // item.setVisible(false);
            //menu.getItem(0).setActionView(iv_book);
            rotation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation)
                {            }

                @Override
                public void onAnimationEnd(Animation animation)
                {
                    isRefreshing = false;
                    invalidateOptionsMenu();
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {                }
            });
            MenuItemCompat.setActionView(item,iv);
       navItemSelection = true;


            return true;

        }
        if (id == R.id.search)
        {
            isRefreshing = true;

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            ImageView iv = (ImageView) inflater.inflate(R.layout.iv_search,null);


            Animation rotation = AnimationUtils.loadAnimation(
                    getApplicationContext(), R.anim.anim1);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 10);
            rotation.setInterpolator(interpolator);
            //rotation.setRepeatCount(0);
            iv.startAnimation(rotation);

            //refresh();
            // item.setVisible(false);
            //menu.getItem(0).setActionView(iv_book);
            rotation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation)
                {            }

                @Override
                public void onAnimationEnd(Animation animation)
                {
                    isRefreshing = false;
                    invalidateOptionsMenu();
                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {                }
            });
            MenuItemCompat.setActionView(item,iv);
            navItemSelection = true;

           /* Intent i =new Intent(Main_Activity.this,Search_Activity.class);
            startActivity(i);*/

            return true;

        }



        return super.onOptionsItemSelected(item);
    }


    //=========================================================================================================

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {

        Boolean signout =false;
        navItemSelection = true ;
        mainActivityTemp  = true;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.cat_comp_accesories)
        {
            this.id = 1;
            toolbar.setTitle(getString(R.string.cat_comp_accesories));
        }

        else if (id == R.id.cat_jewel_watches_eye)
        {
            this.id = 2;
            toolbar.setTitle(getString(R.string.cat_jewel_watches_eye));

        }

        else if (id == R.id.cat_ayur_med)
        {
            this.id = 3;
            toolbar.setTitle(getString(R.string.cat_ayur_med));

        }

        else if (id == R.id.cat_food_snaks)
        {
            this.id = 4;
            toolbar.setTitle(getString(R.string.cat_food_snaks));

        }

        else if (id == R.id.cat_footwear)
        {
            this.id = 5;
            toolbar.setTitle(getString(R.string.cat_footwear));

        }

        else if (id == R.id.cat_cloth_accesories)
        {
            this.id = 6;
            toolbar.setTitle(getString(R.string.cat_cloth_accesories));

        }

        else if (id == R.id.cat_handbg_lugg)
        {
            this.id = 7;
            toolbar.setTitle(getString(R.string.cat_handbg_lugg));

        }*/

        if (id == R.id.my_wishlist)
        {
            signout = true;
            //toolbar.setTitle(getString(R.string.my_wishlish));
            /*Intent i =new Intent(getApplicationContext(),Wishlist_Activity.class);
            startActivity(i);*/

        }

        /*else if (id == R.id.signout)
        {
            signout = true;
            Toast.makeText(context, "Sign Out Successfully!!!", Toast.LENGTH_SHORT).show();
        }
*/
        /*if(!signout)
        {
            loadFrameLayout();
            loadJSONCAT();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //=========================================================================================================

    private void initSliderImagesView_inflater()
    {/*
        ImagesArray = new ArrayList<String>();
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) myLayout_inflater.findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(Main_Activity.this,ImagesArray));*/


        /*CirclePageIndicator indicator = (CirclePageIndicator)
                myLayout_inflater.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);*/

        /*NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable()
        {
            public void run()
            {
                if (currentPage == NUM_PAGES)
                {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(Update);
            }
        }, 5000, 5000);
*/
        /*// Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int pos)
            {

            }
        });*/

    }




    //=========================================================================================================

    private void initRecyclerViewAllProduct_inflater()         //Call >>> RecyclerViewBestDeals_inflater
    {
        mRecyclerViewDealsForYou = (RecyclerView) myLayout_inflater.findViewById(R.id.card_recycler_view_all_products);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                2,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewDealsForYou.setLayoutManager(gridLayoutManager);

        /*//Crete new EndlessScrollListener to endless recyclerview loading
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount)
            {
                if (!productsAdapter.loading)
                    feedData();
            }
        };*/
        //to give loading item full single row
        /*gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (productsAdapter.getItemViewType(position)) {
                    case ProductsAdapter.PRODUCT_ITEM:
                        return 1;
                    case ProductsAdapter.LOADING_ITEM:
                        return 2; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        });*/
        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
        mRecyclerViewDealsForYou.addItemDecoration(new Space(2, 5, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }


//=========================================================================================================

//=========================================================================================================
/*

    private void loadJSONAllProducts_inflater()                 //Call >>> JSONBestDeals_inflater
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Main_Activity.this);
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

            Call<JSONResponse> call = request.getAllProduct();
            call.enqueue(new Callback<JSONResponse>()
            {
                @Override
                public void onResponse(@NonNull Call<JSONResponse> call, @NonNull Response<JSONResponse> response) {

                    JSONResponse jsonResponse = response.body();
                    Log.e("JSON BODY=====>>>>>",""+response.body());
                    assert jsonResponse != null;
                    mArrayList2 = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                    Log.e("JSON RESPONSE=====>>>>>",""+mArrayList2.get(1).getPname());

                    mAdapter = new DataAdapter(mArrayList2);
                    mRecyclerViewAllProduct.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<JSONResponse> call, @NonNull Throwable t) {
                    Log.e("Error",t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        }
        else
        {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(Main_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    loadJSONBestDeals_inflater();
                }
            });

            ab.show();
        }
    }
*/

    //=========================================================================================================


    //======================     Location Permission      ======================================================================
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Main_Activity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })

                        .create()
                        .show();



            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    //==========================================================================================
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

//====================== Alert Dialog Prompt to Turn on GPS ======================================================================



    /*@Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Pause = ", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    protected void onResume() {
        super.onResume();

                /*AlertDialog.Builder ab = new AlertDialog.Builder(Main_Activity.this);
        ab.setTitle("No Internet Connection");
        ab.setIcon(android.R.drawable.ic_menu_revert);
        ab.setCancelable(false);
        ab.setNeutralButton("Reload", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                if (CheckNetwork.isInternetAvailable(getApplicationContext()))
                {
                    loadJSONHotelImages();

                    initRecyclerView____AllHotels();
                    loadJSON____AllHotels();

                    initRecyclerView____DealsForYou();
                    loadJSON____DealsForYou();

                    initRecyclerView____Specials();
                    loadJSON____Specials();
                }
                else
                    {

                        Toast.makeText(Main_Activity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ab.show();*/

                progress();




    }



    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e("", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(Main_Activity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    //===================================================================================
    public void progress()
    {
        // creating progress bar dialog
        progressBar = new ProgressDialog(Main_Activity.this);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        Boolean ck =CheckNetwork.isInternetAvailable(getApplicationContext());
        if (ck)
        {
            progressBar.setMessage("Loading...");
        }
        else
            {
                progressBar.setMessage("No Internet Connection");

        }

        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressBar.show();
        //reset progress bar and filesize status


        new Thread(new Runnable() {
            public void run() {

                while (!CheckNetwork.isInternetAvailable(getApplicationContext()))
                {

                    // performing operation

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Updating the progress bar
                    progressBarHandler.post(new Runnable() {
                        public void run()
                        {

                        }
                    });
                }
                // performing operation if file is downloaded,
                if (CheckNetwork.isInternetAvailable(getApplicationContext()))
                {
                    // sleeping for 1 second after operation completed
                    try {
                        Thread.sleep(1000);
                        OK=true;

                        Main_Activity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                if(!loadJSONHotelImagesFLAG)
                                {
                                    loadJSONHotelImages();
                                    loadJSONHotelImagesFLAG = true;
                                    Log.e("loadJSONHotelImagesFLAG",
                                            "------------------------"+loadJSONHotelImagesFLAG);
                                }


                                initRecyclerView____AllHotels();
                                loadJSON____AllHotels();

                                initRecyclerView____DealsForYou();
                                loadJSON____DealsForYou();

                                initRecyclerView____Specials();
                                loadJSON____Specials();
                            }
                        });
                        progressBar.dismiss();
//                        Thread.currentThread().stop();


                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    // close the progress bar dialog




                }
            }
        }).start();
    }

    //===================================================================================
//===================================================================================


}