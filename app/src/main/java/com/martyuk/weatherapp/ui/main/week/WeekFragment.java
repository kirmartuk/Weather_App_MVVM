package com.martyuk.weatherapp.ui.main.week;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.martyuk.weatherapp.MainViewModel;
import com.martyuk.weatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @BindView(R.id.week_fragment_forecast)
    ListView weekListView;
    @BindView(R.id.week)
    SwipeRefreshLayout swipeRefreshLayout;

    private WeekWeatherAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this, root);
        WeekViewModel weekViewModel = ViewModelProviders.of(getActivity()).get(WeekViewModel.class);

        swipeRefreshLayout.setOnRefreshListener(weekViewModel::loadWeekWeather);

        weekViewModel.getProgress().observe(getViewLifecycleOwner(),
                progress -> swipeRefreshLayout.setRefreshing(progress));

        weekViewModel.getDailyWeather().observe(getViewLifecycleOwner(), daily -> {
            Log.e("daily", daily.toString());
            adapter = new WeekWeatherAdapter(getContext(), daily.getDaily(), daily.getTimeStamp());
            weekListView.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);

            //update city name
            MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
            mainViewModel.loadCity();
        });

        return root;
    }
}
