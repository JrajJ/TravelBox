package com.sparklinktech.stech.travelbox;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.models.Product;
import com.sparklinktech.stech.travelbox.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ___RestaurantDetailsAdapter extends RecyclerView.Adapter<___RestaurantDetailsAdapter.ViewHolder> implements Filterable
{
    private ArrayList<Restaurant> mArrayList;
    private ArrayList<Restaurant> mFilteredList;
    private Context ctx ;
    private Boolean wishlist_flag=false;
    String hid;

    ___RestaurantDetailsAdapter(ArrayList<Restaurant> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.custom_row_hotel_details,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {

        viewHolder.Hotel_name.setText(mFilteredList.get(i).getName());
        viewHolder.Hotel_address.setText(mFilteredList.get(i).getAddress());
        viewHolder.Hotel_ratings.setText(mFilteredList.get(i).getRatings());
        viewHolder.Hotel_longi.setText(mFilteredList.get(i).getLongi());
        viewHolder.Hotel_latti.setText(mFilteredList.get(i).getLatti());
        viewHolder.Hotel_wishlist.setText(mFilteredList.get(i).getWishlist());
        viewHolder.Hotel_contact.setText(mFilteredList.get(i).getContactNo());

        hid = mFilteredList.get(i).getHid();
        //Picasso.with(Main_Activity.context).load(R.drawable.h4).fit().into(viewHolder.iv);
        //viewHolder.ib.setImageResource(R.drawable.wishlist_black);


        String t = mFilteredList.get(i).getWishlist().toString().trim();
        Log.e(">>>>>>>>>>>>>>>>> ",""+t);

        if(t.equals("1"))
        {
            wishlist_flag = true;
        }
        else {
            wishlist_flag = false;
        }



        /*viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, ""+viewHolder.tv_product_name, Toast.LENGTH_SHORT).show();
            }
        });*/
    }



    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount()
    {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter()
    {

        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Restaurant> filteredList = new ArrayList<>();

                    for (Restaurant androidVersion : mArrayList) {

                       /* if (androidVersion.getOfferpr().toLowerCase().contains(charString) || androidVersion.getPname().toLowerCase().contains(charString) || androidVersion.getDscr().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }*/
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Restaurant>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView Hotel_name,Hotel_address,Hotel_ratings,
                Hotel_longi,Hotel_latti,Hotel_wishlist,Hotel_contact;
        private ImageButton gmap;

        public ViewHolder(View view)
        {
            super(view);
            ctx = view.getContext();

            Hotel_name         = (TextView)view.findViewById(R.id.__Hotel_Name);
            Hotel_address         = (TextView)view.findViewById(R.id.__Hotel_Addres);
            Hotel_ratings        = (TextView)view.findViewById(R.id.__Hotel_Ratings);
            Hotel_longi         = (TextView)view.findViewById(R.id.__Hotel_Longi);
            Hotel_latti         = (TextView)view.findViewById(R.id.__Hotel_Latti);
            Hotel_wishlist      =(TextView)view.findViewById(R.id.__Hotel_wishlist);
            Hotel_contact      =(TextView)view.findViewById(R.id.__Hotel_contact);
            gmap = (ImageButton) view.findViewById(R.id.openmap);

            //=============================================================================================



           //=============================================================================================




            gmap.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(CheckNetwork.isInternetAvailable(ctx))
                    {
                    Intent i =new Intent(ctx,MapFragment.class);
                    i.putExtra("lat",Hotel_latti.getText().toString().trim());
                    i.putExtra("lon",Hotel_longi.getText().toString().trim());
                    i.putExtra("name",Hotel_name.getText().toString().trim());

                    ctx.startActivity(i);
                }
                else
                    {
                        Toast.makeText(ctx, "No Internet Connection", Toast.LENGTH_SHORT).show();}
                }
            });
        }
    }

}
