package com.example.ibrahim.photoweatherapp.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Ibrahim on 10/13/2018.
 */

public class CurrentLocation extends Activity implements GoogleApiClient.ConnectionCallbacks, LocationListener {

private GoogleApiClient googleApiClient;
private LocationRequest mLocationRequest;
private LocationManager locationManager;
public static double currentlatitude,currentlongitude;
private Context context;

public CurrentLocation(Context context){
        this.context=context;
        }

public void getCurrentLocation(){
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (googleApiClient == null) {
        googleApiClient = new GoogleApiClient.Builder(context)
        .addConnectionCallbacks(this)
        .addApi(LocationServices.API)
        .build();
        googleApiClient.connect();
        }
        }

@Override
public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);

        Location userCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (userCurrentLocation != null) {
        currentlatitude = userCurrentLocation.getLatitude();
        currentlongitude = userCurrentLocation.getLongitude();
        }
        }
        googleApiClient.disconnect();
        googleApiClient=null;
        }

      @Override
     public void onConnectionSuspended(int i) {
        }


        @Override
     public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        }


} // class of CurrentLocation
