package com.sparklinktech.stech.travelbox;


import android.content.Context;
import android.graphics.Paint;
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
import com.sparklinktech.stech.travelbox.models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataAdapter2 extends RecyclerView.Adapter<DataAdapter2.ViewHolder> implements Filterable
{
    private ArrayList<Product> mArrayList;
    private ArrayList<Product> mFilteredList;
    private Context ctx ;
    private Boolean wishlist_flag=false;
    String pid;

    DataAdapter2(ArrayList<Product> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.custom_row_product_cat,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {

        viewHolder.tv_product_name.setText(mFilteredList.get(i).getPname());
        viewHolder.tv_product_disc.setText(mFilteredList.get(i).getDscr());
        viewHolder.tv_product_offerprice.setText(mFilteredList.get(i).getOfferpr());
        viewHolder.tv_product_MRP.setText(mFilteredList.get(i).getMrp());
        viewHolder.tv_product_views.setText(mFilteredList.get(i).getViews());
        viewHolder.tv_product_wishlist.setText(mFilteredList.get(i).getWishlist());
        pid = mFilteredList.get(i).getPid();
        Picasso.with(Main_Activity.context).load(mFilteredList.get(i).getImg()).fit().into(viewHolder.iv);
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

        if(wishlist_flag)
        {
            viewHolder.ib.setImageResource(R.drawable.wishlist_red);

        }
        else
        {
            viewHolder.ib.setImageResource(R.drawable.wishlist_black);
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

                    ArrayList<Product> filteredList = new ArrayList<>();

                    for (Product androidVersion : mArrayList) {

                        if (androidVersion.getOfferpr().toLowerCase().contains(charString) || androidVersion.getPname().toLowerCase().contains(charString) || androidVersion.getDscr().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

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

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_product_name,tv_product_disc,tv_product_views,
                tv_product_offerprice,tv_product_MRP,tv_product_wishlist;
        private ImageView iv;
        private ImageButton ib;

        public ViewHolder(View view)
        {
            super(view);
            ctx = view.getContext();

            tv_product_name         = (TextView)view.findViewById(R.id.textViewProductName_details);
            tv_product_disc         = (TextView)view.findViewById(R.id.textViewDiscount_details);
            tv_product_offerprice   = (TextView)view.findViewById(R.id.textViewProductPrice_details);
            tv_product_MRP          = (TextView)view.findViewById(R.id.textViewProductMRP_details);
            tv_product_views        = (TextView)view.findViewById(R.id.tv_views);
            tv_product_wishlist = (TextView)view.findViewById(R.id.textViewp_pid);

            tv_product_MRP.setPaintFlags(tv_product_MRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            iv = (ImageView)view.findViewById(R.id.imageViewProductThumb_details);
            ib = (ImageButton) view.findViewById(R.id.ib_wishlist);
            //=============================================================================================



           //=============================================================================================


            iv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    Log.e("NAME    >>>   ",""+tv_product_name.getText().toString());
                }
            });

            ib.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    Log.e("WISHLIST   >>>   ",""+tv_product_wishlist.getText().toString());

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ConnectToServer.URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    CategoryInterface request = retrofit.create(CategoryInterface.class);
                    Call<JSONResponse> call;

                    if(wishlist_flag)
                    {
                        String wish = "0";
                        wishlist_flag = false ;
                        ib.setImageResource(R.drawable.wishlist_black);
                        call = request.setWishlist(pid,wish);
                    }
                    else
                    {
                        String wish = "1";
                        wishlist_flag = true ;
                        ib.setImageResource(R.drawable.wishlist_red);
                        call = request.setWishlist(pid,wish);
                    }


                    call.enqueue(new Callback<JSONResponse>()
                    {
                        @Override
                        public void onResponse(@NonNull Call<JSONResponse> call, @NonNull Response<JSONResponse> response)
                        {
                            Log.e("RESPONSE        >>>>",response.message());

                            // Log.e("JSON RESPONSE=====>>>>>",""+mArrayList.get(1));
                            // Log.e("JSON RESPONSE=====>>>>>","Name  <-->  "+mArrayList.get(2).getPname());
                        }

                        @Override
                        public void onFailure(@NonNull Call<JSONResponse> call, @NonNull Throwable t) {
                            Log.e("Error  ***",t.getMessage());
                        }
                    });
                }
            });

        }
    }

}
