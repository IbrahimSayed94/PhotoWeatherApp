package com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Request;

import com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Response.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public interface ApiInterface
{
    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(@Query("lat") String latitude,
                                            @Query("lon") String longitude,
                                            @Query("units") String unit);
} // Interface of
