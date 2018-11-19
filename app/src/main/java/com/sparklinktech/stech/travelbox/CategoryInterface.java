package com.sparklinktech.stech.travelbox;




import com.sparklinktech.stech.travelbox.models.Packagess;
import com.sparklinktech.stech.travelbox.models.Restaurant;

import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.POST;


interface CategoryInterface {
    @FormUrlEncoded
    @POST("travelbox/json_get_logindetails.php")
    Call<Login> getLoginDetails(@Field("name") String name,
                                @Field("address") String address,
                                @Field("email") String email,
                                @Field("gender") String gender,
                                @Field("dob") String dob,
                                @Field("contact") String contact);

    @GET("solar/json_get_category.php")
    Call<JSONResponse> getJSON();

    @FormUrlEncoded
    @POST("solar/json_get_logindetails.php")
    Call<Login> getLoginDetails(@Field("uid") String uid);

    @GET("solar/json_get_recommended.php")
    Call<JSONResponse> getRecommended();

    @GET("solar/json_get_trending.php")
    Call<JSONResponse> getTrending();

    @GET("solar/json_get_bestdeals.php")
    Call<JSONResponse> getBestDeals();

    @FormUrlEncoded
    @POST("solar/json_set_wishlist.php")
    Call<JSONResponse> setWishlist(@Field("pid") String pid, @Field("wishlist") String wishlist);

    @FormUrlEncoded
    @POST("solar/json_get_category.php")
    Call<JSONResponse> getStringScalar(@Field("cat") String cat);

    @FormUrlEncoded
    @POST("travelbox/json_set_wishlist.php")
    Call<RestaurantFetch> setWishlistHotels(@Field("hid") String hid, @Field("wishlist") String wishlist);

    @FormUrlEncoded
    @POST("solar/json_get_productdetails.php")
    Call<JSONResponse> getProductDetails(@Field("pid") String pid);


    @FormUrlEncoded
    @POST("solar/json_get_shop.php")
    Call<JSONResponse> getShop(@Field("sid") String sid);

    @FormUrlEncoded
    @POST("travelbox/json_get_hoteldetails.php")
    Call<RestaurantFetch> getHotelDetails(@Field("hid") String hid);

    @FormUrlEncoded
    @POST("travelbox/json_get_packages.php")
    Call<PackagesFetch> getPackages(@Field("hid") String hid);


    @FormUrlEncoded
    @POST("travelbox/json_get_hotelimages.php")
    Call<RestaurantFetch> getHotelImages(@Field("hid") String hid);


    @FormUrlEncoded
    @POST("travelbox/json_get_hotelservices.php")
    Call<RestaurantFetch> getHotelServices(@Field("hid") String hid);


    @POST("solar/json_get_product.php")
    Call<JSONResponse> getAllProduct();

    @POST("travelbox/json_get_restaurant.php")
    Call<RestaurantFetch> getAllRestaurant();



    @POST("solar/json_get_wishlist.php")
    Call<JSONResponse> getAllWishlistItems();


   @GET("solar/json_get_category.php")
   Call<CategoryData> getStringScalar();



}