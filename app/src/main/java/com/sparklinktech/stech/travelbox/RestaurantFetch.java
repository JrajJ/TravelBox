
package com.sparklinktech.stech.travelbox;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sparklinktech.stech.travelbox.models.Hotelimage;
import com.sparklinktech.stech.travelbox.models.Hotelservice;
import com.sparklinktech.stech.travelbox.models.Restaurant;

public class RestaurantFetch
{

    @SerializedName("restaurant")
    @Expose
    private List<Restaurant> restaurant = null;

    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }



    @SerializedName("hotelservices")
    @Expose
    private List<Hotelservice> hotelservices = null;

    public List<Hotelservice> getHotelservices() {
        return hotelservices;
    }

    public void setHotelservices(List<Hotelservice> hotelservices) {
        this.hotelservices = hotelservices;
    }




    @SerializedName("hotelimages")
    @Expose
    private List<Hotelimage> hotelimages = null;

    public List<Hotelimage> getHotelimages() {
        return hotelimages;
    }

    public void setHotelimages(List<Hotelimage> hotelimages) {
        this.hotelimages = hotelimages;
    }

}
