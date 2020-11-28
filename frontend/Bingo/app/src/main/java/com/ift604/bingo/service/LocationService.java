package com.ift604.bingo.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import static androidx.core.content.ContextCompat.getSystemService;

public class LocationService {
    private final LocationManager _locationManager;
    private final LocationListener _locationListener;

    public LocationService(Context context, Activity activity, LocationListener locationListener)
    {
        _locationManager = getSystemService(context, LocationManager.class);
        _locationListener = locationListener;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
            permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
            ActivityCompat.requestPermissions(activity, permissions, 0);
        }
    }

    @SuppressLint("MissingPermission")
    public void startListening()
    {
        _locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, _locationListener);
    }
}
