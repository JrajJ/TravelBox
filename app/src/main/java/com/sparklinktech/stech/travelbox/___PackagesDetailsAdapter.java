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
import android.widget.TextView;
import android.widget.Toast;

import com.sparklinktech.stech.travelbox.NetworkCheck.CheckNetwork;
import com.sparklinktech.stech.travelbox.models.Packagess;
import com.sparklinktech.stech.travelbox.models.Restaurant;

import java.util.ArrayList;

public class ___PackagesDetailsAdapter extends RecyclerView.Adapter<___PackagesDetailsAdapter.ViewHolder> implements Filterable
{
    private ArrayList<Packagess> mArrayList;
    private ArrayList<Packagess> mFilteredList;
    private Context ctx ;
    private Boolean wishlist_flag=false;
    String hid;

    ___PackagesDetailsAdapter(ArrayList<Packagess> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.custom_row_packages,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {

        viewHolder._package_name.setText(mFilteredList.get(i).getName());
        viewHolder._package_descr.setText(mFilteredList.get(i).getDescr());
        viewHolder._package_price.setText(mFilteredList.get(i).getPrice());

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

                    ArrayList<Packagess> filteredList = new ArrayList<>();

                    for (Packagess androidVersion : mArrayList) {

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
                mFilteredList = (ArrayList<Packagess>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView _package_name,_package_descr,_package_price;


        public ViewHolder(View view)
        {
            super(view);
            ctx = view.getContext();

            _package_name         = (TextView)view.findViewById(R.id.__package_name);
            _package_descr         = (TextView)view.findViewById(R.id.__package_descr);
            _package_price        = (TextView)view.findViewById(R.id.__package_price);

            //=============================================================================================



           //=============================================================================================





        }
    }

}
