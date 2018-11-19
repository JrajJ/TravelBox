package com.sparklinktech.stech.travelbox;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.sparklinktech.stech.travelbox.models.Store_;

import java.util.ArrayList;

public class StoreAdapter2 extends RecyclerView.Adapter<StoreAdapter2.ViewHolder> implements Filterable
{
    private ArrayList<Store_> mArrayList;
    private ArrayList<Store_> mFilteredList;
    private Context ctx ;
    private String pid = null;

    StoreAdapter2(ArrayList<Store_> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row_product_store, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {

        viewHolder.tv_store_name.setText(mFilteredList.get(i).getSname());
        viewHolder.tv_store_owner.setText(mFilteredList.get(i).getOwner());
        viewHolder.tv_store_contact.setText(mFilteredList.get(i).getContact());
        viewHolder.tv_store_address.setText(mFilteredList.get(i).getAddr());
        viewHolder.tv_store_email.setText(mFilteredList.get(i).getEmail());



        /*viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, ""+viewHolder.tv_product_name, Toast.LENGTH_SHORT).show();
            }
        });*/
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

                    ArrayList<Store_> filteredList = new ArrayList<>();

                    for (Store_ storeinfo : mArrayList) {

                        if (storeinfo.getSname().toLowerCase().contains(charString) || storeinfo.getContact().toLowerCase().contains(charString) || storeinfo.getAddr().toLowerCase().contains(charString)) {

                            filteredList.add(storeinfo);
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
                mFilteredList = (ArrayList<Store_>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_store_name,tv_store_owner,tv_store_contact,tv_store_address,tv_store_email;

        private ViewHolder(View view)
        {
            super(view);
            ctx = view.getContext();

            tv_store_name      = (TextView)view.findViewById(R.id.textViewSHOP_NAME_details_store);
            tv_store_owner     = (TextView)view.findViewById(R.id.textViewOWNERName_details_store);
            tv_store_contact   = (TextView)view.findViewById(R.id.textViewContact_details_store);
            tv_store_address   = (TextView)view.findViewById(R.id.textViewAddress_details_store);
            tv_store_email     = (TextView)view.findViewById(R.id.textViewEmail_details_store);




        }
    }

}
