package com.martyuk.weatherapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.LocationServices;
import com.martyuk.weatherapp.ui.main.network.IpRepository;
import com.martyuk.weatherapp.ui.main.network.pojo.IpPOJO;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.martyuk.weatherapp.Utils.getRetrofit;
import static com.martyuk.weatherapp.Utils.getSystemLanguage;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<String> city = new MutableLiveData<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getCity() {
        loadCity();
        return city;
    }

    public void setCity(String city) {
        this.city.setValue(city);
    }

    public void loadCityByLatLon() {
        LocationManager lm = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission")
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert addresses != null;
        String cityName = (addresses.get(0).getLocality() == null)
                ? addresses.get(0).getPostalCode()
                : addresses.get(0).getLocality();
        if (cityName == null) {
            city.setValue("—");
        } else {
            city.setValue(cityName);
        }
    }

    public void loadCityByIp() {
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

    public void loadCity() {
        if (ActivityCompat.checkSelfPermission(getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            loadCityByIp();
        } else {
            loadCityByLatLon();
        }
    }
}
