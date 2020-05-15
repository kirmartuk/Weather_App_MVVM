package com.martyuk.weatherapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    public static int getImageRes(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }

    public static Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public static String getSystemLanguage(){
        String locale = Resources.getSystem().getConfiguration().locale.getLanguage();
        switch (locale){
            case "cs":
                return "cz";
        }
        return locale;
    }
}
