package edu.uw.tcss450.chatapp_group1.ui.weather;

import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentWeatherHourlyCardBinding;

public class WeatherHourlyRecyclerViewAdapter extends
        RecyclerView.Adapter<WeatherHourlyRecyclerViewAdapter.WeatherViewHolder> {

    //store all of the weather data present
    private final List<WeatherData> mWeathers;
    public WeatherHourlyRecyclerViewAdapter(List<WeatherData> items) {
        this.mWeathers = items;
    }

    @NonNull
    @Override
    public WeatherHourlyRecyclerViewAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherHourlyRecyclerViewAdapter.WeatherViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_weather_hourly_card, parent, false));
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
     * of rows in the hourly Recycler View.
     */
    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentWeatherHourlyCardBinding binding;

        private WeatherData mWeather;

        public WeatherViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentWeatherHourlyCardBinding.bind(view);
        }

        /**
         * helps to set the hourly weather components
         * @param weather
         */
        void setWeather(final WeatherData weather) {
            mWeather = weather;
            binding.textTemperature.setText(""+mWeather.getmTemperature());
            binding.textCondition.setText(mWeather.getmDescription());
            binding.textWeatherTime.setText(mWeather.getmTime());
            if (mWeather.getmIcon().equals("01d") || mWeather.getmIcon().equals("01n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__1d));
            }
            else if (mWeather.getmIcon().equals("02d") || mWeather.getmIcon().equals("02n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__2d));
            }
            else if (mWeather.getmIcon().equals("03d") || mWeather.getmIcon().equals("03n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__3d));
            }
            else if (mWeather.getmIcon().equals("04d") || mWeather.getmIcon().equals("04n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__4d));
            }
            else if (mWeather.getmIcon().equals("09d") || mWeather.getmIcon().equals("09n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__9d));
            }
            else if (mWeather.getmIcon().equals("10d") || mWeather.getmIcon().equals("10n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__10d));
            }
            else if (mWeather.getmIcon().equals("11d") || mWeather.getmIcon().equals("11n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__11d));
            }
            else if (mWeather.getmIcon().equals("13d") || mWeather.getmIcon().equals("13n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__13d));
            }
            else if (mWeather.getmIcon().equals("50d") || mWeather.getmIcon().equals("50n")){
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__0d));
            }
            else{
                binding.imageWeather.setImageIcon(Icon.createWithResource(mView.getContext(),
                        R.drawable.ic__1d));
            }
        }
    }
}
