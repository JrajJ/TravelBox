
package com.sparklinktech.stech.travelbox.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Product2 {

    @SerializedName("product")
    @Expose
    private List<Product> product = null;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

}
