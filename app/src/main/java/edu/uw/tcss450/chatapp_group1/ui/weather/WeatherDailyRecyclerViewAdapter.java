package edu.uw.tcss450.chatapp_group1.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentWeatherDailyCardBinding;

public class WeatherDailyRecyclerViewAdapter extends
        RecyclerView.Adapter<WeatherDailyRecyclerViewAdapter.WeatherViewHolder> {

    private final List<WeatherData> mWeathers;

    public WeatherDailyRecyclerViewAdapter(List<WeatherData> items) {
        this.mWeathers = items;
    }

    @NonNull
    @Override
    public WeatherDailyRecyclerViewAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherDailyRecyclerViewAdapter.WeatherViewHolder(LayoutInflater
                .from(parent.getContext())

                .inflate(R.layout.fragment_weather_daily_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.setWeather(mWeathers.get(position));

    }

    @Override
    public int getItemCount() {
        return mWeathers.size();

    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the daily weather Recycler View.
     */
    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentWeatherDailyCardBinding binding;

        private WeatherData mWeather;

        public WeatherViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherDailyCardBinding.bind(view);
        }


        /**
         * helps to set the components of the dailyweather
         * @param weatherDaily
         */
        void setWeather(final WeatherData weatherDaily) {
            mWeather = weatherDaily;
            mWeather = weatherDaily;
            binding.textMinTemp.setText(mWeather.getmMinTemperature());
            binding.textMaxTemp.setText(mWeather.getmMaxTemperature());
            binding.textCondition.setText(mWeather.getmCondition());
            binding.textWeatherDate.setText(mWeather.getmDate());
        }
    }
}
