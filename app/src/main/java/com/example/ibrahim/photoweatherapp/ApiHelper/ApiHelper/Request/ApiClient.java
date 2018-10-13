package com.example.ibrahim.photoweatherapp.ApiHelper.ApiHelper.Request;

import android.content.Context;

import com.example.ibrahim.photoweatherapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public class ApiClient
{
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;
    private static Context mContext;


    public static Retrofit getClient(Context context) {
        mContext=context;
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request();
                                HttpUrl url = request.url()
                                        .newBuilder()
                                        .addQueryParameter("APPID",mContext.getString(R.string.weathermap_app_id))
                                        .build();
                                request = request.newBuilder().url(url).build();
                                return chain.proceed(request);
                            }
                        }).build();

        if (retrofit==null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofit;
    }
} // class of ApiClient
