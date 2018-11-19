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

import com.sparklinktech.stech.travelbox.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable
{
    private ArrayList<Product> mArrayList;
    private ArrayList<Product> mFilteredList;
    private Context ctx ;

      DataAdapter(ArrayList<Product> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder)
    {
        super.onViewAttachedToWindow(holder);
            }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row_product, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {
        viewHolder.tv_product_name.setText(mFilteredList.get(i).getPname());
        viewHolder.tv_product_disc.setText(mFilteredList.get(i).getDscr());
        viewHolder.tv_product_price.setText(mFilteredList.get(i).getOfferpr());
        viewHolder.tv_product_pid.setText(mFilteredList.get(i).getPid());
        viewHolder.tv_product_sid.setText(mFilteredList.get(i).getSid());
        viewHolder.tv_product_wishlist.setText(mFilteredList.get(i).getWishlist());
          Picasso.with(Main_Activity.context).load(mFilteredList.get(i).getImg()).fit().into(viewHolder.iv);
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

                    ArrayList<Product> filteredList = new ArrayList<>();
                    Log.e("ARRAY LIST       >>>>","    >>>>>> ELSE        "+mArrayList);

                    for (Product androidVersion : mArrayList) {

                        if (androidVersion.getOfferpr().toLowerCase().contains(charString) || androidVersion.getPname().toLowerCase().contains(charString) || androidVersion.getDscr().toLowerCase().contains(charString)) {
                            Log.e("ANDROID VER >>","     "+androidVersion.toString());

                            filteredList.add(androidVersion);
                        }
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
                mFilteredList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_product_name,tv_product_disc,
                tv_product_price,tv_product_pid,tv_product_sid,tv_product_wishlist;
        private ImageView iv;

        private ViewHolder(View view)
        {
            super(view);

            ctx = view.getContext();

            tv_product_name = (TextView)view.findViewById(R.id.textViewProductName);
            tv_product_disc = (TextView)view.findViewById(R.id.textViewDiscount);
            tv_product_price = (TextView)view.findViewById(R.id.textViewProductPrice);
            tv_product_pid = (TextView)view.findViewById(R.id.textViewProductPid);
            tv_product_sid = (TextView)view.findViewById(R.id.textViewProductSid);
            tv_product_wishlist = (TextView)view.findViewById(R.id.textViewWishlist);

            iv = (ImageView)view.findViewById(R.id.imageViewProductThumb);

            iv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent i = new Intent(Main_Activity.context,Product_Activity.class);
                    i.putExtra("Pid",""+tv_product_pid.getText().toString());
                    i.putExtra("Sid",""+tv_product_sid.getText().toString());
                    i.putExtra("Wishlist",""+tv_product_wishlist.getText().toString());
                    ctx.startActivity(i);

                    Log.e("NAME    >>>   ",""+tv_product_name.getText().toString());
                }
            });



        }

    }

}
