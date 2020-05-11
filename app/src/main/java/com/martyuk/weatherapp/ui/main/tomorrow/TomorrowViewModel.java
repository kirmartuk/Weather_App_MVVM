package com.martyuk.weatherapp.ui.main.tomorrow;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.martyuk.weatherapp.MainActivity;
import com.martyuk.weatherapp.Passwords;
import com.martyuk.weatherapp.entities.TomorrowModel;
import com.martyuk.weatherapp.entities.WeatherHourlyModel;
import com.martyuk.weatherapp.ui.main.network.WeatherRepository;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.martyuk.weatherapp.MainActivity.units;
import static com.martyuk.weatherapp.Utils.getImageRes;

public class TomorrowViewModel extends AndroidViewModel {
    private MutableLiveData<TomorrowModel> tomorrow = new MutableLiveData<>();
    MutableLiveData<Boolean> progressBar = new MutableLiveData<>();

    public TomorrowViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<TomorrowModel> getTomorrow() {
        loadTomorrowWeather();
        return tomorrow;
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

    public void loadTomorrowWeather() {
        progressBar.postValue(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherRepository weatherRepository = retrofit.create(WeatherRepository.class);
        weatherRepository.loadTomorrowWeather(56.045279f, 92.969643f, units, Passwords.WEATHER_PASSWORD)
                .enqueue(new Callback<TomorrowWeatherDataPOJO>() {
                    @Override
                    public void onResponse(Call<TomorrowWeatherDataPOJO> call, Response<TomorrowWeatherDataPOJO> response) {
                        TomorrowWeatherDataPOJO pojo = response.body();
                        Log.e("tomorrow_pojo", pojo.toString());
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                        Calendar cal = Calendar.getInstance();
                        assert pojo != null;
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
                                loadTomorrowHourly(pojo)
                        );
                        tomorrow.setValue(model);
                        progressBar.postValue(false);
                    }

                    @Override
                    public void onFailure(Call<TomorrowWeatherDataPOJO> call, Throwable t) {

                    }
                });
    }
}
