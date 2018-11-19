package com.sparklinktech.stech.travelbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.sparklinktech.stech.travelbox.Main_Activity.context;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> implements Filterable
{
    private ArrayList<Restaurant> mArrayList;
    private ArrayList<Restaurant> mFilteredList;
    private Context ctx ;

    RestaurantAdapter(ArrayList<Restaurant> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RestaurantAdapter.ViewHolder holder)
    {
        super.onViewAttachedToWindow(holder);
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.custom_restaurant, viewGroup, false);

        return new RestaurantAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantAdapter.ViewHolder viewHolder, int i)
    {
        Log.e("Hid >>> ",""+mFilteredList.get(i).getHid());
        Log.e("Name >>> ",""+mFilteredList.get(i).getName());
        Log.e("Address >>> ",""+mFilteredList.get(i).getAddress());
        Log.e("Contact >>> ",""+mFilteredList.get(i).getContactNo());
        Log.e("Rating >>> ",""+mFilteredList.get(i).getRatings());
        Log.e("Longi >>> ",""+mFilteredList.get(i).getLongi());
        Log.e("Latti >>> ",""+mFilteredList.get(i).getLatti());
        Log.e("Wishlist >>> ",""+mFilteredList.get(i).getWishlist());

        viewHolder.tv_Restaurant_name.setText(mFilteredList.get(i).getName());
        viewHolder.tv_Restaurant_address.setText(mFilteredList.get(i).getAddress());
        viewHolder.tv_Restaurant_contact.setText(mFilteredList.get(i).getContactNo());
        viewHolder.tv_Restaurant_rating.setText(mFilteredList.get(i).getRatings());
        viewHolder.tv_Restaurant_hid.setText(mFilteredList.get(i).getHid());
        viewHolder.tv_Restaurant_longi.setText(mFilteredList.get(i).getLongi());
        viewHolder.tv_Restaurant_latti.setText(mFilteredList.get(i).getLatti());
        viewHolder.tv_Restaurant_Wishlist.setText(mFilteredList.get(i).getWishlist());

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.e("width   =   ",""+width);
        Log.e("height   =   ",""+height);


        Picasso.with(ctx).load(R.drawable.hh1).resize((width/3)-50,(width/4)-20)
                .into(viewHolder.hotel_image);

    }

    @Override
    public int getItemCount()
    {
        return mFilteredList.size();
    }


    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
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
                    Log.e("ARRAY LIST       >>>>",      "    >>>>>> IF    "+mArrayList);
                    Log.e("FILTERED LIST    >>>>",  "    >>>>>> IF    "+mFilteredList);
                    mFilteredList = mArrayList;

                } else {
                    Log.e("FILETER STRING  **** ",      "     "+charString);

                    ArrayList<Restaurant> filteredList = new ArrayList<>();
                    Log.e("ARRAY LIST       >>>>","    >>>>>> ELSE        "+mArrayList);

                    for (Restaurant androidVersion : mArrayList) {

                        /*if (androidVersion.ge).toLowerCase().contains(charString) || androidVersion.getPname().toLowerCase().contains(charString) || androidVersion.getDscr().toLowerCase().contains(charString)) {
                            Log.e("ANDROID VER >>","     "+androidVersion.toString());

                            filteredList.add(androidVersion);
                        }*/
                    }
                    Log.e("Filtered LIST >>","     "+filteredList.toString());

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

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_Restaurant_name,tv_Restaurant_address,
                tv_Restaurant_contact,tv_Restaurant_rating,
                tv_Restaurant_hid,tv_Restaurant_longi,tv_Restaurant_latti,
                tv_Restaurant_Wishlist;

        ImageView map,hotel_image;


        private ViewHolder(View view)
        {
            super(view);

            ctx = view.getContext();

            tv_Restaurant_name      = (TextView)view.findViewById(R.id.textViewRestaurantName);
            tv_Restaurant_address   = (TextView)view.findViewById(R.id.textViewRestaurantAddress);
            tv_Restaurant_contact   = (TextView)view.findViewById(R.id.textViewRestaurantContact);
            tv_Restaurant_rating    = (TextView)view.findViewById(R.id.textViewRestaurantRatings);
            tv_Restaurant_hid       = (TextView)view.findViewById(R.id.textViewHotelid);
            tv_Restaurant_longi     = (TextView)view.findViewById(R.id.textViewHotelLongitude);
            tv_Restaurant_latti     = (TextView)view.findViewById(R.id.textViewHotelLattitude);
            tv_Restaurant_Wishlist  = (TextView)view.findViewById(R.id.textViewRestaurantWishlistt);

            map                     = view.findViewById(R.id.openmap);
            hotel_image             = view.findViewById(R.id.hotel_img_main);



            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(CheckNetwork.isInternetAvailable(ctx))
                    {
                Intent i = new Intent(ctx,Hotels_Activity.class);
                i.putExtra("hid",     tv_Restaurant_hid.getText().toString().trim());
                i.putExtra("wishlist",tv_Restaurant_Wishlist.getText().toString().trim());

                    ctx.startActivity(i);

                }
                else {
                        Toast.makeText(ctx, "No Internet Connection", Toast.LENGTH_SHORT).show();}
                }
            });






        }

    }

}
