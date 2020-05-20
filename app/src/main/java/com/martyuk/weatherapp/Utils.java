package com.martyuk.weatherapp;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    /**
     * @param context
     * @param imageName
     * @return int image resource for openweathermap data
     */
    public static int getImageRes(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    /**
     * @param baseUrl
     * @return retrofit by base url
     */
    public static Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * some languages differ in Android os and API
     *
     * @return system language for openWeatherMap.org
     */
    public static String getSystemLanguage() {
        String locale = Resources.getSystem().getConfiguration().locale.getLanguage();
        switch (locale) {
            case "cs":
                return "cz";
            case "sq":
                return "al";
            case "iw":
                return "he";
            case "in":
                return "id";
            case "ko":
                return "kr";
            case "lv":
                return "la";
            case "nb":
                return "no";
            case "zn":
                return "zn_ch";
        }
        return locale;
    }

    public static boolean checkLocation(Application application) {
        LocationManager locationManager = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return ActivityCompat.checkSelfPermission(application,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && locationManager.isLocationEnabled();
        }
        return false;

    }

    public static void loadWeather() {

    }
}
