
package com.sparklinktech.stech.travelbox.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sparklinktech.stech.travelbox.ConnectToServer.ConnectToServer;

public class Product {

    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("cat")
    @Expose
    private String cat;
    @SerializedName("3")
    @Expose
    private String _3;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("4")
    @Expose
    private String _4;
    @SerializedName("dscr")
    @Expose
    private String dscr;
    @SerializedName("5")
    @Expose
    private String _5;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("6")
    @Expose
    private String _6;
    @SerializedName("offerpr")
    @Expose
    private String offerpr;
    @SerializedName("7")
    @Expose
    private String _7;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("8")
    @Expose
    private String _8;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("9")
    @Expose
    private String _9;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("10")
    @Expose
    private String _10;
    @SerializedName("wishlist")
    @Expose
    private String wishlist;

    public String get0() {
        return _0;
    }

    public void set0(String _0) {
        this._0 = _0;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String get1() {
        return _1;
    }

    public void set1(String _1) {
        this._1 = _1;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String get2() {
        return _2;
    }

    public void set2(String _2) {
        this._2 = _2;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String get3() {
        return _3;
    }

    public void set3(String _3) {
        this._3 = _3;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String get4() {
        return _4;
    }

    public void set4(String _4) {
        this._4 = _4;
    }

    public String getDscr() {
        return dscr;
    }

    public void setDscr(String dscr) {
        this.dscr = dscr;
    }

    public String get5() {
        return _5;
    }

    public void set5(String _5) {
        this._5 = _5;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String get6() {
        return _6;
    }

    public void set6(String _6) {
        this._6 = _6;
    }

    public String getOfferpr() {
        return offerpr;
    }

    public void setOfferpr(String offerpr) {
        this.offerpr = offerpr;
    }

    public String get7() {
        return _7;
    }

    public void set7(String _7) {
        this._7 = _7;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String get8() {
        return _8;
    }

    public void set8(String _8) {
        this._8 = _8;
    }

    public String getImg() {
        String str = ConnectToServer.URL_GETIMAGES;
        return str+img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String get9() {
        return _9;
    }

    public void set9(String _9) {
        this._9 = _9;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String get10() {
        return _10;
    }

    public void set10(String _10) {
        this._10 = _10;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }

}