package com.martyuk.weatherapp;

import android.Manifest;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.martyuk.weatherapp.ui.main.SectionsPagerAdapter;
import com.martyuk.weatherapp.ui.main.today.TodayViewModel;
import com.martyuk.weatherapp.ui.main.tomorrow.TomorrowViewModel;
import com.martyuk.weatherapp.ui.main.week.WeekViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.button_switch_sign)
    Button switcher;
    @BindView(R.id.title)
    TextView title;

    public static String units = "metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager);
        //access permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                0);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getCity().observe(this, city -> {
            title.setText(city);
            Log.e("mainActivity", city);
        });

        switcher.setText("°C");
        switcher.setOnClickListener(v -> {
            String sign = (units.equals("metric")) ? "°F" : "°C";
            units = (units.equals("metric")) ? "imperial" : "metric";
            switcher.setText(sign);
            TomorrowViewModel tomorrowView = ViewModelProviders.of(this).get(TomorrowViewModel.class);
            TodayViewModel todayViewModel = ViewModelProviders.of(this).get(TodayViewModel.class);
            WeekViewModel weekViewModel = ViewModelProviders.of(this).get(WeekViewModel.class);
            todayViewModel.loadCurrentWeather();
            tomorrowView.loadTomorrowWeather();
            weekViewModel.loadWeekWeather();
        });

    }

}