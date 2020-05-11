package com.martyuk.weatherapp.ui.main.today;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.martyuk.weatherapp.Passwords;
import com.martyuk.weatherapp.entities.TodayModel;
import com.martyuk.weatherapp.entities.WeatherHourlyModel;
import com.martyuk.weatherapp.ui.main.network.WeatherData;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.martyuk.weatherapp.MainActivity.units;
import static com.martyuk.weatherapp.Utils.getImageRes;


public class TodayViewModel extends AndroidViewModel {
    MutableLiveData<TodayModel> today = new MutableLiveData<TodayModel>();
    MutableLiveData<Boolean> progressBar = new MutableLiveData<>();

    public TodayViewModel(@NonNull Application application) {
        super(application);
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
    public void loadCurrentWeather() {
        progressBar.setValue(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherRepository weatherRepository = retrofit.create(WeatherRepository.class);
        weatherRepository.loadTodayWeather(56.045279f, 92.969643f, units, Passwords.WEATHER_PASSWORD)
                .enqueue(new Callback<TodayWeatherDataPOJO>() {
                    @Override
                    public void onResponse(@NonNull Call<TodayWeatherDataPOJO> call, @NonNull Response<TodayWeatherDataPOJO> response) {
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
                                loadTodayHourly(currentWeather, cal.get(Calendar.HOUR_OF_DAY))
                        );

                        today.postValue(receivedTodayModel);
                        progressBar.postValue(false);
                    }

                    @Override
                    public void onFailure(Call<TodayWeatherDataPOJO> call, Throwable t) {
                        Log.e("error", t.toString());
                        progressBar.postValue(false);

                    }
                });
    }

}
