package com.martyuk.weatherapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.martyuk.weatherapp.ui.main.network.IpRepository;
import com.martyuk.weatherapp.ui.main.network.pojo.IpPOJO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.martyuk.weatherapp.Utils.getRetrofit;
import static com.martyuk.weatherapp.Utils.getSystemLanguage;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> city = new MutableLiveData<>();

    public MainViewModel() {
    }

    public LiveData<String> getCity() {
        loadCity();
        return city;
    }

    public void setCity(String city) {
        this.city.setValue(city);
    }

    public void loadCity() {
        IpRepository ipRepository = getRetrofit(IpRepository.BASE_URL).create(IpRepository.class);
        ipRepository.getIpData(getSystemLanguage()).enqueue(new Callback<IpPOJO>() {
            @Override
            public void onResponse(Call<IpPOJO> call, Response<IpPOJO> response) {
                assert response.body() != null;
                city.postValue(response.body().getCity());
            }

            @Override
            public void onFailure(Call<IpPOJO> call, Throwable t) {
                city.postValue("—");
                Log.e("mainViewModel", t.toString());
            }
        });
    }
}
