package com.martyuk.weatherapp.ui.main.today;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.martyuk.weatherapp.R;
import com.martyuk.weatherapp.WeatherHourlyRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.martyuk.weatherapp.MainActivity.*;

public class TodayFragment extends Fragment {


    @BindView(R.id.fragment_today_temperature)
    TextView temperature;
    @BindView(R.id.fragment_today_timeUpdate)
    TextView timeUpdate;
    @BindView(R.id.fragment_today_sunImage)
    ImageView sunImage;
    @BindView(R.id.fragment_today_current_hourly)
    RecyclerView currentHourly;
    @BindView(R.id.fragment_today_feelsLike)
    TextView feelsLike;
    @BindView(R.id.fragment_today_temperature_sign)
    TextView sign;
    @BindView(R.id.today_progressBar)
    ProgressBar progressBar;

    private WeatherHourlyRecyclerViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.bind(this, root);
        TodayViewModel model = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        model.progressBar.observe(getViewLifecycleOwner(), progress -> {
            Log.e("progress", progress.toString());
            if (progress) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
        model.getModel().observe(getViewLifecycleOwner(), model1 -> {
            Log.e("model", model1.toString());
            temperature.setText(String.valueOf(model1.getTemperature()));
            timeUpdate.setText(model1.getTimeStamp());
            sunImage.setImageResource(model1.getImageResource());
            feelsLike.setText(getString(R.string.feels_like) + " " + model1.getFeelsLike());
            sign.setText((units.equals("metric") ? "°C" : "°F"));

            adapter = new WeatherHourlyRecyclerViewAdapter(model1.getHourly());
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            currentHourly.setLayoutManager(mLayoutManager);
            currentHourly.setItemAnimator(new DefaultItemAnimator());
            currentHourly.setAdapter(adapter);

        });

        return root;
    }


}
