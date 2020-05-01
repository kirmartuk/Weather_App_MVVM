package com.martyuk.weatherapp.ui.main.today;

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

import com.martyuk.weatherapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayFragment extends Fragment {


    @BindView(R.id.fragment_today_temperature)
    TextView temperature;
    @BindView(R.id.fragment_today_timeUpdate)
    TextView timeUpdate;
    @BindView(R.id.fragment_today_sunImage)
    ImageView sunImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.bind(this, root);
        TodayViewModel model = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        model.getModel().observe(getViewLifecycleOwner(), model1 -> {
            Log.e("model", model1.toString());
            temperature.setText(String.valueOf(model1.getTemperature()));
            timeUpdate.setText(model1.getTimeStamp());
            sunImage.setImageResource(model1.getImageResource());

        });
        return root;
    }


}
