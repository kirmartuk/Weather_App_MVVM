package com.martyuk.weatherapp;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.martyuk.weatherapp.ui.main.network.IpRepository;
import com.martyuk.weatherapp.ui.main.network.pojo.IpPOJO;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.martyuk.weatherapp.Utils.checkLocation;
import static com.martyuk.weatherapp.Utils.getRetrofit;
import static com.martyuk.weatherapp.Utils.getSystemLanguage;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<String> city = new MutableLiveData<>();
    private LocationManager locationManager;

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

    @SuppressLint("MissingPermission")
    public void loadCityByLatLon() {
        Log.e("load_way", "lat lon");
        locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null);
        } else {
            loadCityByIp();
        }


    }

    public void loadCityByIp() {
        Log.e("load_way", "ip");
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
        locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if (checkLocation(getApplication())) {
            loadCityByLatLon();
        } else {
            loadCityByIp();
        }
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Log.e("location", location.toString());
            loadCityByLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }
    };

    private void loadCityByLocation(Location location) {
        Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 2);
        } catch (IOException e) {
            e.printStackTrace();
            loadCityByIp();
            return;
        }
        String cityName = null;
        assert addresses != null;
        if (!addresses.isEmpty()) {
            cityName = (addresses.get(0).getLocality() == null)
                    ? addresses.get(0).getPostalCode()
                    : addresses.get(0).getLocality();
        }
        if (cityName == null) {
            loadCityByIp();
        } else {
            city.setValue(cityName);
        }
    }

}

