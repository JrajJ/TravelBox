package com.sparklinktech.stech.travelbox;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.helpers.Space;
import com.sparklinktech.stech.travelbox.models.Product;
import com.sparklinktech.stech.travelbox.models.Store_;


import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Product_Activity extends AppCompatActivity
{
    // Progress Dialog Object
    private ProgressDialog mProgressDialog;
    TextView name_p,desc_p,price_p;
    ImageButton iv_p;
    private String pid,sid,wishlist;
    private Boolean wishlist_flag = false;
    private ArrayList<Product> mArrayList;
    private ArrayList<Store_> storeArrayList;

    private RecyclerView mRecyclerViewDetails,mRecyclerViewShop;
    private DataAdapter2 mAdapter;
    private StoreAdapter2 storeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar22);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_backspace_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null)
        {

            pid  = b.getString("Pid");
            Log.e("PID  <--> ",""+pid);

            sid  = b.getString("Sid");
            Log.e("SID  <--> ",""+sid);

            wishlist  = b.getString("Wishlist");
            Log.e("WISHLIST  <--> ",""+wishlist);

            if(wishlist.equals("1"))
            {
                wishlist_flag = true ;
                Log.e("HERER  <--> ","");

            }
            else
                {
                    wishlist_flag = false ;
                }

            initRecyclerViewDetails();

            loadJSONDetails();

            initRecyclerViewShops();

            loadJSONShops();

                    }


    }

    //=========================================================================================================

    private void initRecyclerViewDetails()                //Call >>> 1.1
    {
        mRecyclerViewDetails = (RecyclerView)findViewById(R.id.card_recycler_view_details);
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

    private void loadJSONDetails()                 //Call >>> 2.1
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Product_Activity.this);
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

            Call<JSONResponse> call = request.getProductDetails(pid);
            call.enqueue(new Callback<JSONResponse>()
            {
                @Override
                public void onResponse(@NonNull Call<JSONResponse> call, @NonNull Response<JSONResponse> response) {

                    JSONResponse jsonResponse = response.body();
                    Log.e("P JSON BODY  ===>>>>>",""+response.body());
                    assert jsonResponse != null;
                    mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                    //Log.e("P JSON RESPONSE ==>>>>>",""+mArrayList.get(0).getPname());



                    mAdapter = new DataAdapter2(mArrayList);
                    mRecyclerViewDetails.setAdapter(mAdapter);
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
            AlertDialog.Builder ab = new AlertDialog.Builder(Product_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    loadJSONDetails();
                }
            });

            ab.show();
        }
    }

    //=========================================================================================================

    private void initRecyclerViewShops()                //Call >>> 1.1
    {
        mRecyclerViewShop = (RecyclerView)findViewById(R.id.card_recycler_view_shop);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewShop.setLayoutManager(gridLayoutManager);


        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
        mRecyclerViewShop.addItemDecoration(new Space(1, 0, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }

//=========================================================================================================

    private void loadJSONShops()                 //Call >>> 2.1
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Product_Activity.this);
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

            Call<JSONResponse> call = request.getShop(sid);
            call.enqueue(new Callback<JSONResponse>()
            {
                @Override
                public void onResponse(@NonNull Call<JSONResponse> call, @NonNull Response<JSONResponse> response) {

                    JSONResponse jsonResponse = response.body();
                    Log.e("P JSON BODY  ===>>>>>",""+response.body());
                    assert jsonResponse != null;
                    storeArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getStore()));
                    //Log.e("P JSON RESPONSE ==>>>>>",""+mArrayList.get(0).getPname());



                    storeAdapter = new StoreAdapter2(storeArrayList);
                    mRecyclerViewShop.setAdapter(storeAdapter);
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
            AlertDialog.Builder ab = new AlertDialog.Builder(Product_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    loadJSONShops();
                }
            });

            ab.show();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {

        super.onBackPressed();
    }



    public void addToCart(View view)
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConnectToServer.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CategoryInterface request = retrofit.create(CategoryInterface.class);
        Call<JSONResponse> call;

            call = request.setWishlist(pid,"1");

        call.enqueue(new Callback<JSONResponse>()
        {
            @Override
            public void onResponse(@NonNull Call<JSONResponse> call, @NonNull Response<JSONResponse> response)
            {
                Log.e("RESPONSE        >>>>",response.message());
                Toast.makeText(getApplicationContext(), "Item Added to Cart.", Toast.LENGTH_SHORT).show();

                // Log.e("JSON RESPONSE=====>>>>>",""+mArrayList.get(1));
                // Log.e("JSON RESPONSE=====>>>>>","Name  <-->  "+mArrayList.get(2).getPname());
            }

            @Override
            public void onFailure(@NonNull Call<JSONResponse> call, @NonNull Throwable t)
            {
                Log.e("Error  ***",t.getMessage());
            }
        });
    }
}
