package com.martyuk.weatherapp.ui.main.week;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.martyuk.weatherapp.R;
import com.martyuk.weatherapp.ui.main.network.Daily;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.martyuk.weatherapp.Utils.getImageRes;

public class WeekWeatherAdapter extends ArrayAdapter<Daily> {
    private Context mContext;
    private List<Daily> mDailyList;
    private String mTimeZone;

    public WeekWeatherAdapter(@NonNull Context context, List<Daily> daily, String timeZone) {
        super(context, 0, daily);
        this.mContext = context;
        this.mDailyList = daily;
        this.mTimeZone = timeZone;
    }

    @BindView(R.id.week_item_day)
    TextView day;
    @BindView(R.id.week_item_description)
    TextView description;
    @BindView(R.id.week_item_image)
    ImageView image;
    @BindView(R.id.week_item_max)
    TextView max;
    @BindView(R.id.week_item_min)
    TextView min;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem =
                (convertView == null)
                        ? LayoutInflater.from(mContext)
                        .inflate(R.layout.week_item, parent, false)
                        : convertView;
        Daily daily = mDailyList.get(position);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(daily.getDt() * 1000L);
        cal.setTimeZone(TimeZone.getTimeZone(mTimeZone));

        ButterKnife.bind(this, listItem);

        day.setText(dateFormat.format(cal.getTime()));
        description.setText(daily.getWeather().get(0).getDescription());
        image.setImageResource(
                getImageRes(
                        mContext, "ic_" + daily.getWeather().get(0).getIcon()
                )
        );
        max.setText(String.valueOf((int) daily.getTemp().getMax()));
        min.setText(String.valueOf((int) daily.getTemp().getMin()));

        return listItem;
    }
}
