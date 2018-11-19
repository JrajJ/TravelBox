package com.sparklinktech.stech.travelbox;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.sparklinktech.stech.travelbox.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.sparklinktech.stech.travelbox.Main_Activity.context;

public class DealsForYouAdapter extends RecyclerView.Adapter<DealsForYouAdapter.ViewHolder> implements Filterable
{
    private ArrayList<Restaurant> mArrayList;
    private ArrayList<Restaurant> mFilteredList;
    private Context ctx ;

    DealsForYouAdapter(ArrayList<Restaurant> arrayList)
    {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull DealsForYouAdapter.ViewHolder holder)
    {
        super.onViewAttachedToWindow(holder);
    }

    @NonNull
    @Override
    public DealsForYouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.custom_dealsforyou, viewGroup, false);

        return new DealsForYouAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DealsForYouAdapter.ViewHolder viewHolder, int i)
    {
        Log.e("Name >>> ",""+mFilteredList.get(i).getName());
        Log.e("Address >>> ",""+mFilteredList.get(i).getAddress());
        Log.e("Contact >>> ",""+mFilteredList.get(i).getContactNo());
        Log.e("Rating >>> ",""+mFilteredList.get(i).getRatings());

        viewHolder.tv_Restaurant_name.setText(mFilteredList.get(i).getName());
        viewHolder.tv_Restaurant_address.setText(mFilteredList.get(i).getAddress());
        viewHolder.tv_Restaurant_contact.setText(mFilteredList.get(i).getContactNo());
        viewHolder.tv_Restaurant_rating.setText(mFilteredList.get(i).getRatings());

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.e("width   =   ",""+width);
        Log.e("height   =   ",""+height);


        Picasso.with(ctx).load(R.drawable.hh2).resize(width/2,width/2)
                .into(viewHolder.iv);
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
                tv_Restaurant_contact,tv_Restaurant_rating;

ImageView iv ;
        private ViewHolder(View view)
        {
            super(view);

            ctx = view.getContext();
                iv=view.findViewById(R.id.iv_dealsforyou);
            tv_Restaurant_name = (TextView)view.findViewById(R.id.textViewDealsName);
            tv_Restaurant_address = (TextView)view.findViewById(R.id.textViewDealsAddress);
            tv_Restaurant_contact = (TextView)view.findViewById(R.id.textViewDealsContact);
            tv_Restaurant_rating = (TextView)view.findViewById(R.id.textViewDealsRatings);




        }

    }

}
