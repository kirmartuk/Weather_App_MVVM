package com.martyuk.weatherapp;

import android.content.Context;

public class Utils {
    public static int getImageRes(Context context, String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}
