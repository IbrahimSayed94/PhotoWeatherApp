package com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public class WeatherResponse
{
    @SerializedName("weather")
    @Expose
    private ArrayList<WeatherConditionResponse> weatherCondition;

    @SerializedName("main")
    @Expose
    private WeatherTemperatureResponse weatherTemperature;

    public ArrayList<WeatherConditionResponse> getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(ArrayList<WeatherConditionResponse> weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public WeatherTemperatureResponse getWeatherTemperature() {
        return weatherTemperature;
    }

    public void setWeatherTemperature(WeatherTemperatureResponse weatherTemperature) {
        this.weatherTemperature = weatherTemperature;
    }
} // class  of WeatherResponse
