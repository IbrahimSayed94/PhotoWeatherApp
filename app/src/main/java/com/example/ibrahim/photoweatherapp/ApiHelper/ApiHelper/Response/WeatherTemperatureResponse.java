package com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public class WeatherTemperatureResponse
{
    @SerializedName("temp")
    @Expose
    private Integer temperature;

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
} // class of WeatherTemperatureResponse
