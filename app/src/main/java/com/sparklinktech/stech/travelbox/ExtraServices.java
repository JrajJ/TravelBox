package com.sparklinktech.stech.travelbox;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.helpers.Space;
import com.sparklinktech.stech.travelbox.models.Packagess;
import com.sparklinktech.stech.travelbox.models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExtraServices extends AppCompatActivity
{
    ProgressDialog progressBar;
    ProgressDialog mProgressDialog;
    private Handler progressBarHandler = new Handler();
    RecyclerView mRecyclerViewPackages;
    ___PackagesDetailsAdapter mAdapter;
    private ArrayList<Packagess> mArrayList;
    String hid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra_services);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {

            hid = b.getString("hid");
            Log.e("HID  <--> ", "" + hid);



        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        progress();


    }


    //===================================================================================
    public void progress()
    {
        // creating progress bar dialog
        progressBar = new ProgressDialog(ExtraServices.this);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        Boolean ck = CheckNetwork.isInternetAvailable(getApplicationContext());
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

                        ExtraServices.this.runOnUiThread(new Runnable() {
                            public void run() {

                                initRecyclerViewPackages();
                                loadJSONPackages();



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



    private void initRecyclerViewPackages()                //Call >>> 1.1
    {
        mRecyclerViewPackages = (RecyclerView) findViewById(R.id.card_recycler_view_Packages);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewPackages.setLayoutManager(gridLayoutManager);


        //add on on Scroll listener
        //mRecyclerView.addOnScrollListener(endlessScrollListener);
        //add space between cards
        mRecyclerViewPackages.addItemDecoration(new Space(1, 0, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }

//=========================================================================================================

    private void loadJSONPackages()                 //Call >>> 2.1
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ExtraServices.this);
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
            Log.e("HID  <--> ", "" + hid);
            Call<PackagesFetch> call = request.getPackages(hid);
            call.enqueue(new Callback<PackagesFetch>()
            {
                @Override
                public void onResponse(@NonNull Call<PackagesFetch> call, @NonNull Response<PackagesFetch> response) {

                    PackagesFetch jsonResponse = response.body();
                    Log.e("P JSON BODY  ===>>>>>", "" + response.body());
                    /*assert jsonResponse != null;*/
                    //mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
//                    Log.e("P JSON RESPONSE ==>>>>>",""+mArrayList.get(0).getName());

                    assert jsonResponse != null;
                    //mArrayList = packagess.toArray();
                    mArrayList = new ArrayList<>(jsonResponse.getPackagess());
                    mAdapter = new ___PackagesDetailsAdapter(mArrayList);
                    mRecyclerViewPackages.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(@NonNull Call<PackagesFetch> call, @NonNull Throwable t) {
                    Log.e("Error", t.getMessage());
                }
            });
            mProgressDialog.dismiss();
        } else {
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ab = new AlertDialog.Builder(ExtraServices.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loadJSONPackages();
                }
            });

            ab.show();
        }
    }

}
