package com.ift604.bingo.dal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationProvider {
    private static Location location;
    private static FusedLocationProviderClient fusedLocationProviderClient;

    private static LocationProvider instance;

    public static LocationProvider getInstance(Context context, Activity activity)
    {
        if (instance == null)
            instance = new LocationProvider(context, activity);
        return instance;
    }

    private LocationProvider(Context context, Activity activity)
    {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        location = new Location(LocationManager.GPS_PROVIDER);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
            permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
            ActivityCompat.requestPermissions(activity, permissions, 0);
        } else
            startListening();
    }

    @SuppressLint("MissingPermission")
    public static void startListening() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                changeLocation(location);
            }
        });
    }

    public static void changeLocation(Location newLocation) {
        location = newLocation;
    }

    public static Location getLocation() {
        return location;
    }
}
