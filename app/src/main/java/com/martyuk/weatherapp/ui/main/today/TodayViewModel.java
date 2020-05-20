package com.martyuk.weatherapp.ui.main.today;

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
import com.martyuk.weatherapp.entities.TodayModel;
import com.martyuk.weatherapp.entities.WeatherHourlyModel;
import com.martyuk.weatherapp.ui.main.network.IpRepository;
import com.martyuk.weatherapp.ui.main.network.WeatherData;
import com.martyuk.weatherapp.ui.main.network.pojo.IpPOJO;
import com.martyuk.weatherapp.ui.main.network.pojo.TodayWeatherDataPOJO;
import com.martyuk.weatherapp.ui.main.network.WeatherRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.martyuk.weatherapp.MainActivity.units;
import static com.martyuk.weatherapp.Utils.checkLocation;
import static com.martyuk.weatherapp.Utils.getImageRes;
import static com.martyuk.weatherapp.Utils.getSystemLanguage;
import static com.martyuk.weatherapp.Utils.getRetrofit;


public class TodayViewModel extends AndroidViewModel {
    private MutableLiveData<TodayModel> today = new MutableLiveData<TodayModel>();
    private MutableLiveData<Boolean> progress = new MutableLiveData<>();

    public TodayViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getProgress() {
        return progress;
    }

    public void setModel(TodayModel model) {
        today.setValue(model);
    }

    public void postModel(TodayModel model) {
        today.postValue(model);
    }

    public LiveData<TodayModel> getModel() {
        loadCurrentWeather();
        return today;
    }

    /**
     * @param data        - POJO from request
     * @param currentHour
     * @return list with hourly forecast
     */
    public List<WeatherHourlyModel> loadTodayHourly(TodayWeatherDataPOJO data, int currentHour) {
        List<WeatherHourlyModel> hourly = new ArrayList<>();
        for (int i = 0; i < Math.abs(currentHour - 24); i++) {
            hourly.add(
                    new WeatherHourlyModel(
                            (int) data.getHourly().get(i).getTemp(),
                            getImageRes(getApplication().getBaseContext(),
                                    "ic_" + data.getHourly().get(i).getWeather().get(0).getIcon()),
                            data.getHourly().get(i).getDt(),
                            data.getTimezone()
                    ));
        }
        return hourly;
    }

    /**
     * load today current weather and hourly forecast from openweathermap.org
     */
    @SuppressLint("MissingPermission")
    public void loadCurrentWeather() {
        progress.setValue(true);
        LocationManager locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        if (checkLocation(getApplication())) {
            if (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) == null) {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.e("today_location", location.toString());
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
            //get lat and lon from ip use system locale
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
                .loadTodayWeather(
                        lat,
                        lon,
                        units,
                        Passwords.WEATHER_PASSWORD,
                        getSystemLanguage())
                .enqueue(new Callback<TodayWeatherDataPOJO>() {
                    @Override
                    public void onResponse(@NonNull Call<TodayWeatherDataPOJO> call,
                                           @NonNull Response<TodayWeatherDataPOJO> response) {
                        TodayWeatherDataPOJO currentWeather = response.body();
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm");
                        Calendar cal = Calendar.getInstance();

                        assert currentWeather != null;
                        cal.setTimeInMillis(currentWeather.getCurrent().getDt() * 1000L);
                        cal.setTimeZone(TimeZone.getTimeZone(currentWeather.getTimezone()));

                        WeatherData weatherData = currentWeather.getCurrent();
                        assert weatherData != null;
                        TodayModel receivedTodayModel = new TodayModel(
                                dateFormat.format(cal.getTime()),
                                (int) weatherData.getTemp(),
                                getImageRes(
                                        getApplication().getApplicationContext(),
                                        "ic_" + weatherData.getWeather().get(0).getIcon()
                                ),
                                (int) weatherData.getFeels_like(),
                                loadTodayHourly(currentWeather, cal.get(Calendar.HOUR_OF_DAY)),
                                weatherData.getWeather().get(0).getDescription()
                        );
                        today.postValue(receivedTodayModel);
                    }

                    @Override
                    public void onFailure(Call<TodayWeatherDataPOJO> call, Throwable t) {
                        Log.e("error", t.toString());
                        progress.setValue(false);
                    }
                });
    }

}
