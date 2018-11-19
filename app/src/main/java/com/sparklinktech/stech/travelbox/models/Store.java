
package com.sparklinktech.stech.travelbox.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Store {

    @SerializedName("store")
    @Expose
    private List<Store_> store = null;

    public List<Store_> getStore() {
        return store;
    }

    public void setStore(List<Store_> store) {
        this.store = store;
    }

}
