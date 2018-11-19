
package com.sparklinktech.stech.travelbox;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sparklinktech.stech.travelbox.models.Packagess;

public class PackagesFetch {

    @SerializedName("packagess")
    @Expose
    private List<Packagess> packagess = null;

    public List<Packagess> getPackagess() {
        return packagess;
    }

    public void setPackagess(List<Packagess> packagess) {
        this.packagess = packagess;
    }

}
