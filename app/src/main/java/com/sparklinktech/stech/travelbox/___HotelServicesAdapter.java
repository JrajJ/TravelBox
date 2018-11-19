package com.sparklinktech.stech.travelbox;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.travelbox.models.Hotelservice;
import com.sparklinktech.stech.travelbox.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ___HotelServicesAdapter extends RecyclerView.Adapter<___HotelServicesAdapter.ViewHolder> implements Filterable
{
    private ArrayList<Hotelservice> mArrayList;
    private ArrayList<Hotelservice> mFilteredList;
    private Context ctx ;
    private Boolean wishlist_flag=false;
    String hid;

    ___HotelServicesAdapter(ArrayList<Hotelservice> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.custom_row_hotel_services,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {

        viewHolder.Hotel_service_name.setText(mFilteredList.get(i).getServiceName());


        //hid = mFilteredList.get(i).getHid();
        Picasso.with(ctx)
                .load(mFilteredList.get(i).getServiceImage()).fit().into(viewHolder.iv_hotelservice);


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

                    ArrayList<Hotelservice> filteredList = new ArrayList<>();

                    for (Hotelservice androidVersion : mArrayList) {

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
                mFilteredList = (ArrayList<Hotelservice>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Hotel_service_name;
        private ImageView iv_hotelservice;

        public ViewHolder(View view) {
            super(view);
            ctx = view.getContext();

            Hotel_service_name = (TextView) view.findViewById(R.id.__Hotel_Service_Name);

            iv_hotelservice = (ImageView) view.findViewById(R.id.imageView__Hotel_Service_image);


            //=============================================================================================


        }
    }
}
