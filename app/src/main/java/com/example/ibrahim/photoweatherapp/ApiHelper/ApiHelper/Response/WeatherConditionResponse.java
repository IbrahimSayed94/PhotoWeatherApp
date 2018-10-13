package com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public class WeatherConditionResponse
{
    @SerializedName("description")
    @Expose
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
} // class of WeatherConditionResponse
