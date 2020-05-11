package com.martyuk.weatherapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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