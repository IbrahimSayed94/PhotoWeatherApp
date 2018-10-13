package com.example.ibrahim.photoweatherapp.Utils;

import android.app.Application;

import com.example.ibrahim.photoweatherapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public class App extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        LocaleHelper.setLocale(this, "ar");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/erasbd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }
} // class of App
