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
import android.widget.ImageView;
import android.widget.TextView;

import com.sparklinktech.stech.travelbox.models.Hotelimage;
import com.sparklinktech.stech.travelbox.models.Hotelservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ___HotelImagesAdapter extends RecyclerView.Adapter<___HotelImagesAdapter.ViewHolder> implements Filterable
{
    private ArrayList<Hotelimage> mArrayList;
    private ArrayList<Hotelimage> mFilteredList;
    private Context ctx;
    String[] url_image;


    private Boolean wishlist_flag=false;
    String hid;

    ___HotelImagesAdapter(ArrayList<Hotelimage> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        url_image = new String[arrayList.size()];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.custom_row_hotel_images,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {

        viewHolder.Hotel_service_name.setText(mFilteredList.get(i).getHid());


        hid = mFilteredList.get(i).getHid();
        Log.e("<><><><><><><><>< ",""+i);
        url_image[i]=mFilteredList.get(i).getHotelImage();
        Picasso.with(ctx)
                .load(mFilteredList.get(i).getHotelImage()).
                fit().into(viewHolder.iv_hotelservice);



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

                    ArrayList<Hotelimage> filteredList = new ArrayList<>();

                    for (Hotelimage androidVersion : mArrayList) {

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
                mFilteredList = (ArrayList<Hotelimage>) filterResults.values;
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

            Hotel_service_name = (TextView) view.findViewById(R.id.tv_hotelimage_hid);

            iv_hotelservice = (ImageView) view.findViewById(R.id.imageViewHotel____images);


            //=============================================================================================
            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent i = new Intent(ctx, FullImage_Activity.class);
                    i.putExtra("hid",""+hid);
                    i.putExtra("imagecount",""+mArrayList.size());
                    /*for (int j=0;j<mFilteredList.size();j++)
                    {
                        i.putExtra("image"+j,""+url_image[j]);
                    }*/


                    ctx.startActivity(i);
                }
            });

        }
    }
}
