package com.martyuk.weatherapp.ui.main.network;

import com.martyuk.weatherapp.ui.main.network.pojo.IpPOJO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IpRepository {
    String BASE_URL = "http://ip-api.com/";

    @GET("json")
    Call<IpPOJO> getIpData(@Query("lang") String language);
}
