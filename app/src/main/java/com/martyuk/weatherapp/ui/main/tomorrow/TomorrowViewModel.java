package com.martyuk.weatherapp.ui.main.tomorrow;

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

import com.martyuk.weatherapp.Passwords;
import com.martyuk.weatherapp.entities.TomorrowModel;
import com.martyuk.weatherapp.entities.WeatherHourlyModel;
import com.martyuk.weatherapp.ui.main.network.IpRepository;
import com.martyuk.weatherapp.ui.main.network.WeatherRepository;
import com.martyuk.weatherapp.ui.main.network.pojo.IpPOJO;
import com.martyuk.weatherapp.ui.main.network.pojo.TomorrowWeatherDataPOJO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.martyuk.weatherapp.MainActivity.units;
import static com.martyuk.weatherapp.Utils.checkLocation;
import static com.martyuk.weatherapp.Utils.getImageRes;
import static com.martyuk.weatherapp.Utils.getSystemLanguage;
import static com.martyuk.weatherapp.Utils.getRetrofit;

public class TomorrowViewModel extends AndroidViewModel {
    private MutableLiveData<TomorrowModel> tomorrow = new MutableLiveData<>();
    private MutableLiveData<Boolean> progress = new MutableLiveData<>();

    public TomorrowViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<TomorrowModel> getTomorrow() {
        loadTomorrowWeather();
        return tomorrow;
    }

    public LiveData<Boolean> getProgress() {
        return progress;
    }

    private List<WeatherHourlyModel> loadTomorrowHourly(TomorrowWeatherDataPOJO data) {
        List<WeatherHourlyModel> hourlyModels = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        assert data != null;
        cal.setTimeInMillis(data.getHourly().get(0).getDt() * 1000L);
        cal.setTimeZone(TimeZone.getTimeZone(data.getTimezone()));

        for (int i = 24 - cal.get(Calendar.HOUR_OF_DAY); i < 48 - cal.get(Calendar.HOUR_OF_DAY); i++) {
            hourlyModels.add(
                    new WeatherHourlyModel(
                            (int) data.getHourly().get(i).getTemp(),
                            getImageRes(getApplication().getBaseContext(),
                                    "ic_" + data.getHourly().get(i).getWeather().get(0).getIcon()),
                            data.getHourly().get(i).getDt(),
                            data.getTimezone()
                    ));

        }
        return hourlyModels;

    }

    @SuppressLint("MissingPermission")
    public void loadTomorrowWeather() {
        progress.setValue(true);
        //get lat and lon from ip use system locale
        LocationManager locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        if (checkLocation(getApplication())) {
            if (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null) {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.e("tomorrow_location", location.toString());
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
            getRetrofit(IpRepository.BASE_URL)
                    .create(IpRepository.class)
                    .getIpData(getSystemLanguage())
                    .enqueue(new Callback<IpPOJO>() {
                        @Override
                        public void onResponse(Call<IpPOJO> call, Response<IpPOJO> response) {
                            IpPOJO latLonData = response.body();
                            makeRequestToServer(latLonData.getLat(), latLonData.getLon());
                        }

                        @Override
                        public void onFailure(Call<IpPOJO> call, Throwable t) {
                            progress.setValue(false);
                        }
                    });
        }


    }

    private void makeRequestToServer(float lat, float lon) {
        WeatherRepository weatherRepository = getRetrofit(WeatherRepository.BASE_URL)
                .create(WeatherRepository.class);
        weatherRepository
                .loadTomorrowWeather(
                        lat,
                        lon,
                        units,
                        Passwords.WEATHER_PASSWORD,
                        getSystemLanguage())
                .enqueue(new Callback<TomorrowWeatherDataPOJO>() {
                    @Override
                    public void onResponse(Call<TomorrowWeatherDataPOJO> call,
                                           Response<TomorrowWeatherDataPOJO> response) {
                        TomorrowWeatherDataPOJO pojo = response.body();
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(pojo.getDaily().get(1).getDt() * 1000L);
                        cal.setTimeZone(TimeZone.getTimeZone(pojo.getTimezone()));

                        TomorrowModel model = new TomorrowModel(
                                dateFormat.format(cal.getTime()),
                                (int) pojo.getDaily().get(1).getTemp().getMax(),
                                (int) pojo.getDaily().get(1).getTemp().getMin(),
                                getImageRes(
                                        getApplication().getBaseContext(),
                                        "ic_" + pojo.getDaily().get(1).getWeather().get(0).getIcon()
                                ),
                                loadTomorrowHourly(pojo),
                                pojo.getDaily().get(1).getWeather().get(0).getDescription()
                        );
                        tomorrow.setValue(model);
                    }

                    @Override
                    public void onFailure(Call<TomorrowWeatherDataPOJO> call, Throwable t) {
                        progress.setValue(false);
                    }
                });
    }
}
