
package com.sparklinktech.stech.travelbox.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;

public class Hotelimage {

    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("hid")
    @Expose
    private String hid;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("hotel_image")
    @Expose
    private String hotelImage;

    public String get0() {
        return _0;
    }

    public void set0(String _0) {
        this._0 = _0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get1() {
        return _1;
    }

    public void set1(String _1) {
        this._1 = _1;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String get2() {
        return _2;
    }

    public void set2(String _2) {
        this._2 = _2;
    }

    public String getHotelImage() {
        String str = ConnectToServer.URL_GETHotelIMAGES;
        return str+hotelImage;
        //return hotelImage;
    }

    public void setHotelImage(String hotelImage) {
        this.hotelImage = hotelImage;
    }

}
