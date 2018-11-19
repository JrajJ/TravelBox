package com.sparklinktech.stech.travelbox;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.helpers.Space;
import com.sparklinktech.stech.travelbox.models.Product;
import com.sparklinktech.stech.travelbox.sharedpref.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search_Activity extends AppCompatActivity
{
    private View myLayout_inflater;
    private LayoutInflater layoutInflater;
    // Progress Dialog Object
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerViewAllProduct;
    private ArrayList<Product> mArrayList,mArrayList2;

    private DataAdapter mAdapter,mAdapter2,mAdapterAll;

    SearchView searchView;
    String response;
    private Boolean mainActivityTemp = false,trendingTemp=false,bestDealsTemp=false,recommendedTemp=false;
    JSONObject globalJson= null;
    static Context context;
    private int id;
    Toolbar toolbar;
    JSONObject jsonObject;
    private Boolean searchViewSelection = true ;
    private FrameLayout fl;

    //========================= Sliding Images Banner Add ================================================================================
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.h1,
            R.drawable.h2,R.drawable.h3,R.drawable.h4};
    private ArrayList<Integer> ImagesArray;
    ScrollView sv;
    NavigationView mNavigationView;
    SessionManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //  setSupportActionBar(toolbar);
        context = Search_Activity.this;

        initRecyclerViewAllProduct();
        loadJSONAllProduct();

        //sv.smoothScrollTo(0,200);

        //=========================================================================================================
        // inflate (create) another copy of our custom layout

        layoutInflater = getLayoutInflater();
    }


    private void loadJSONAllProduct()                 //Call >>> 2.1
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))
        {
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Search_Activity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Fetching Restaurants....");
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
                    //Log.e("JSON BODY=====>>>>>",""+response.body());


                    Log.e("JSON CALL=====>>>>>",""+call.toString());
                    Log.e("JSON BODY=====>>>>>",""+response.body());
                    assert jsonResponse != null;
//                    Log.e("JSON BODY2=====>>>>>",""+ jsonResponse.getService().toString());


                    mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));
                    //Log.e("JSON RESPONSE=====>>>>>",""+mArrayList.get(1).getPname());

                    mAdapter = new DataAdapter(mArrayList);
                    //mAdapter.notifyDataSetChanged();
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
            AlertDialog.Builder ab = new AlertDialog.Builder(Search_Activity.this);
            ab.setTitle("No Internet Connection");
            ab.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
            ab.setNeutralButton("Reload", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    initRecyclerViewAllProduct();
                }
            });

            ab.show();
        }
    }


    //=========================================================================================================

    private void initRecyclerViewAllProduct()
    {
        mRecyclerViewAllProduct = (RecyclerView)findViewById(R.id.card_recycler_view_all_products);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                2,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        mRecyclerViewAllProduct.setLayoutManager(gridLayoutManager);

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
        mRecyclerViewAllProduct.addItemDecoration(new Space(2, 5, false, 0));
        //Finally set the adapter
        // mRecyclerView.setAdapter(productsAdapter);
        //load first page of recyclerview
        // endlessScrollListener.onLoadMore(0, 0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem search = menu.findItem(R.id.search);
        //search.setIcon(R.drawable.ic_action_search);
        //   MenuItem cart = menu.findItem(R.id.cart);
         searchView = (SearchView) MenuItemCompat.getActionView(search);
        //ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        //searchIcon.setImageDrawable(
         //       ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_action_search));       // MenuItemCompat.getActionView(cart);

        searchView.setQueryHint("Search for Hotel or Location...      ");
        searchView.setFocusable(true);
        searchView.setIconified(false);
        //getMenuInflater().inflate(R.menu.main,menu);
        search(searchView);

        Log.e("<<<<<<<<<<<<<<< ","3");
        return true;
    }
//=========================================================================================================




    //=========================================================================================================

    private void search(SearchView searchView)
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                mAdapter.getFilter().filter(newText);

                return true;
            }
        });
    }


    //=========================================================================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement




        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed()
    {

        if(searchViewSelection)
        {
            Log.e("BackPRESS   >>   ","Frame Back->>   "+searchViewSelection);
            searchViewSelection = false;
            searchView.setIconifiedByDefault(true);
            searchView.setIconified(true);

            toolbar.setTitle(getString(R.string.app_name));
        }
        else
        {
            finish();
        }
    }
}
