package edu.uw.tcss450.chatapp_group1.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentWeatherListBinding;

/**
 * A simple {@link Fragment} subclass.

 */
public class WeatherListFragment extends Fragment {

    private WeatherListViewModel mModel;
    private List<WeatherData> mWeathers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherListViewModel.class);
        mModel.getCurrentWeather("98403");
        mModel.get24HourForecast("98403");
        mModel.getFiveDayForecast("98403");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentWeatherListBinding binding = FragmentWeatherListBinding.bind(getView());

        binding.buttonZipcodeSearch.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherListFragmentDirections.actionNavigationWeatherToWeatherMapFragment())
        );

        mModel.addCurrentWeatherObserver(getViewLifecycleOwner(), weatherData -> {
            // Update UI components
            Log.d("Weather List Fragment", "Weather description " + weatherData.getmDescription());
            Log.d("Weather List Fragment", "Temperature " + weatherData.getmTemperature());
            Log.d("Weather List Fragment", "City name " + weatherData.getmCity());
            String city = weatherData.getmCity();
            String temp = String.valueOf(weatherData.getmTemperature());
            String cond = weatherData.getmDescription();
            binding.textCity.setText(city);
            binding.textTemperature.setText(temp);
            binding.textCondition.setText(cond);
        });

        mModel.add24HourForecastObserver(getViewLifecycleOwner(), weatherDataList -> {
            for(int i = 0; i < weatherDataList.size(); i++) {
                Log.d("Weather List Fragment", "Weather description " + weatherDataList.get(i).getmDescription());
                Log.d("Weather List Fragment", "Temperature " + weatherDataList.get(i).getmTemperature());
                Log.d("Weather List Fragment", "time " + weatherDataList.get(i).getmTime());
            }
            binding.hourlyListRoot.setAdapter(new WeatherHourlyRecyclerViewAdapter(weatherDataList));
        });

        mModel.addFiveDayForecastObserver(getViewLifecycleOwner(), weatherDataList -> {
            for(int i = 0; i < weatherDataList.size(); i++) {
                // Update UI components
                Log.d("Weather List Fragment", "Weather description: " + weatherDataList.get(i).getmDescription());
                Log.d("Weather List Fragment", "Min temp: " + weatherDataList.get(i).getmMinTemperature());
                Log.d("Weather List Fragment", "Max temp: " + weatherDataList.get(i).getmMaxTemperature());
            }
            binding.dailyListRoot.setAdapter(new WeatherDailyRecyclerViewAdapter(weatherDataList));
        });
    }
}