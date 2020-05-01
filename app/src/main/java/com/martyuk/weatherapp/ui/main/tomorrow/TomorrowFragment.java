package com.martyuk.weatherapp.ui.main.tomorrow;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.martyuk.weatherapp.R;
import com.martyuk.weatherapp.ui.main.today.TodayModel;
import com.martyuk.weatherapp.ui.main.today.TodayViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TomorrowFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.send)
    Button send;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        ButterKnife.bind(this, root);
        TodayViewModel model = ViewModelProviders.of(getActivity()).get(TodayViewModel.class);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("button", "work");
                model.loadCurrentWeather();
            }
        });


        return root;
    }
}
