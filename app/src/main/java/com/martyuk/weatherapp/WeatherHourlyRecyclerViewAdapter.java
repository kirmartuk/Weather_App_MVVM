package com.martyuk.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.martyuk.weatherapp.entities.WeatherHourlyModel;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherHourlyRecyclerViewAdapter extends RecyclerView.Adapter<WeatherHourlyRecyclerViewAdapter.ViewHolder> {
    private List<WeatherHourlyModel> models;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_recycler_item, parent, false);
        return new ViewHolder(itemView);
    }

    public WeatherHourlyRecyclerViewAdapter(List<WeatherHourlyModel> models) {
        this.models = models;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherHourlyModel model = models.get(position);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(model.getTime() * 1000L);
        cal.setTimeZone(TimeZone.getTimeZone(model.getTimeZone()));

        holder.imageView.setImageResource(model.getImageResource());
        holder.temperature.setText(String.valueOf(model.getTemperature()));
        holder.time.setText(cal.get(Calendar.HOUR_OF_DAY) + ":00");

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hourly_recycler_view_item_image)
        ImageView imageView;
        @BindView(R.id.hourly_recycler_view_item_temperature)
        TextView temperature;
        @BindView(R.id.hourly_recycler_view_item_time)
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
