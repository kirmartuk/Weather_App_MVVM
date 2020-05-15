package com.martyuk.weatherapp.ui.main.tomorrow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.martyuk.weatherapp.MainViewModel;
import com.martyuk.weatherapp.R;
import com.martyuk.weatherapp.WeatherHourlyRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TomorrowFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @BindView(R.id.fragment_tomorrow_hourly)
    RecyclerView hourly;
    @BindView(R.id.fragment_tomorrow_image)
    ImageView image;
    @BindView(R.id.fragment_tomorrow_max)
    TextView max;
    @BindView(R.id.fragment_tomorrow_min)
    TextView min;
    @BindView(R.id.fragment_tomorrow_timeUpdate)
    TextView date;
    @BindView(R.id.fragment_tomorrow_description)
    TextView description;
    @BindView(R.id.tomorrow)
    SwipeRefreshLayout swipeRefreshLayout;

    private WeatherHourlyRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        ButterKnife.bind(this, root);
        TomorrowViewModel tomorrowView = ViewModelProviders.of(getActivity()).get(TomorrowViewModel.class);

        swipeRefreshLayout.setOnRefreshListener(tomorrowView::loadTomorrowWeather);

        tomorrowView.getProgress().observe(getViewLifecycleOwner(),
                progress -> swipeRefreshLayout.setRefreshing(progress));

        tomorrowView.getTomorrow().observe(getViewLifecycleOwner(), tomorrowModel -> {
            Log.e("tomorrow", tomorrowModel.toString());
            image.setImageResource(tomorrowModel.getImageResource());
            max.setText(String.valueOf(tomorrowModel.getMax()));
            min.setText(String.valueOf(tomorrowModel.getMin()));
            date.setText(tomorrowModel.getTimeStamp());
            description.setText(tomorrowModel.getDescription());

            adapter = new WeatherHourlyRecyclerViewAdapter(tomorrowModel.getHourly());
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            hourly.setLayoutManager(mLayoutManager);
            hourly.setItemAnimator(new DefaultItemAnimator());
            hourly.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);

            //update city name
            MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
            mainViewModel.loadCity();
        });


        return root;
    }
}
