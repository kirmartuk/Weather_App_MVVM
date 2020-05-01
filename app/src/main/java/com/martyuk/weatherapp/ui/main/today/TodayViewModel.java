package com.martyuk.weatherapp.ui.main.today;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.martyuk.weatherapp.Passwords;
import com.martyuk.weatherapp.R;
import com.martyuk.weatherapp.Utils;
import com.martyuk.weatherapp.ui.main.network.CurrentWeatherPOJO;
import com.martyuk.weatherapp.ui.main.network.WeatherDataPOJO;
import com.martyuk.weatherapp.ui.main.network.WeatherRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.martyuk.weatherapp.Utils.chooseImageByWeatherCode;

public class TodayViewModel extends AndroidViewModel {
    MutableLiveData<TodayModel> today = new MutableLiveData<TodayModel>();

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

    public void loadCurrentWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherRepository weatherRepository = retrofit.create(WeatherRepository.class);
        weatherRepository.loadCurrentWeather("Krasnoyarsk", Passwords.WEATHER_PASSWORD)
                .enqueue(new Callback<WeatherDataPOJO>() {
                    @Override
                    public void onResponse(Call<WeatherDataPOJO> call, Response<WeatherDataPOJO> response) {
                        Log.e("ok", response.body().toString());
                        CurrentWeatherPOJO currentWeather = response.body().getCurrent();
                        TodayModel tmp = new TodayModel(currentWeather.getObservation_time(),
                                (int) currentWeather.getTemperature(),
                                chooseImageByWeatherCode(currentWeather.getWeather_code()));
                        today.postValue(tmp);
                    }

                    @Override
                    public void onFailure(Call<WeatherDataPOJO> call, Throwable t) {
                        Log.e("error", t.getMessage());
                        Log.e("error", t.toString());

                    }
                });
    }

}
