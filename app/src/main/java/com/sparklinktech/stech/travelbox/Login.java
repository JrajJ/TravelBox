
package com.sparklinktech.stech.travelbox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("login")
    @Expose
    private Boolean login;

    public Boolean getLogin()
    {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

}
