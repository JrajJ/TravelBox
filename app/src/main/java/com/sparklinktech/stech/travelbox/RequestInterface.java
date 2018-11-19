package com.sparklinktech.stech.travelbox;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface RequestInterface {

    @GET("woffers/json_get_product.php")
    Call<JSONResponse> getJSON();


}