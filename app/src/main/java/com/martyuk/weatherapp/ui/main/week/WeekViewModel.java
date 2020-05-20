package com.martyuk.weatherapp.ui.main.week;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.martyuk.weatherapp.Passwords;
import com.martyuk.weatherapp.entities.WeekModel;
import com.martyuk.weatherapp.ui.main.network.IpRepository;
import com.martyuk.weatherapp.ui.main.network.WeatherRepository;
import com.martyuk.weatherapp.ui.main.network.pojo.IpPOJO;
import com.martyuk.weatherapp.ui.main.network.pojo.WeekWeatherDataPOJO;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.martyuk.weatherapp.MainActivity.units;
import static com.martyuk.weatherapp.Utils.checkLocation;
import static com.martyuk.weatherapp.Utils.getSystemLanguage;
import static com.martyuk.weatherapp.Utils.getRetrofit;

public class WeekViewModel extends AndroidViewModel {
    private MutableLiveData<WeekModel> dailyMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> progress = new MutableLiveData<>();

    public WeekViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<WeekModel> getDailyWeather() {
        loadWeekWeather();
        return dailyMutableLiveData;
    }

    public LiveData<Boolean> getProgress() {
        return progress;
    }

    public void setDailyWeather(MutableLiveData<WeekModel> dailyMutableLiveData) {
        this.dailyMutableLiveData = dailyMutableLiveData;
    }

    @SuppressLint("MissingPermission")
    public void loadWeekWeather() {
        progress.setValue(true);
        //get lat and lon from ip use system locale
        LocationManager locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        if (checkLocation(getApplication())) {
            if (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null) {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.e("week_location", location.toString());
                        makeRequestToServer((float) locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude(),
                                (float) locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }


                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }, null);
            } else {
                makeRequestToServer((float) locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude(),
                        (float) locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
            }
        } else {
            IpRepository ipRepository =
                    getRetrofit(IpRepository.BASE_URL).create(IpRepository.class);
            ipRepository.getIpData(getSystemLanguage()).enqueue(new Callback<IpPOJO>() {
                @Override
                public void onResponse(Call<IpPOJO> call, Response<IpPOJO> response) {
                    IpPOJO latLonData = response.body();
                    makeRequestToServer(latLonData.getLat(), latLonData.getLon());

                }

                @Override
                public void onFailure(Call<IpPOJO> call, Throwable t) {
                    progress.setValue(false);
                    Log.e("weekViewModel", t.toString());
                }
            });
        }


    }

    private void makeRequestToServer(float lat, float lon) {
        WeatherRepository weatherRepository =
                getRetrofit(WeatherRepository.BASE_URL).create(WeatherRepository.class);
        weatherRepository
                .loadWeekWeather(
                        lat,
                        lon,
                        units,
                        Passwords.WEATHER_PASSWORD,
                        getSystemLanguage())
                .enqueue(new Callback<WeekWeatherDataPOJO>() {
                    @Override
                    public void onResponse(Call<WeekWeatherDataPOJO> call,
                                           Response<WeekWeatherDataPOJO> response) {
                        WeekModel weekModel = new WeekModel(
                                response.body().getTimezone(),
                                response.body().getDaily());
                        dailyMutableLiveData.setValue(weekModel);
                    }

                    @Override
                    public void onFailure(Call<WeekWeatherDataPOJO> call, Throwable t) {
                        Log.e("weekViewModel", t.toString());
                        progress.setValue(false);

                    }
                });
    }
}
