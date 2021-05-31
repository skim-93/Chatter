package edu.uw.tcss450.chatapp_group1.ui.weather;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentWeatherListBinding;

/**
 * A simple {@link Fragment} subclass.

 */
public class WeatherListFragment extends Fragment {
    //viewmodel for the weather list
    private WeatherListViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherListViewModel.class);
        mModel.getCurrentWeather("98032");
        mModel.get24HourForecast("98032");
        mModel.getFiveDayForecast("98032");
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
                        WeatherListFragmentDirections.actionNavigationWeatherToLocationFragment())
        );

        mModel.addCurrentWeatherObserver(getViewLifecycleOwner(), weatherData -> {
            // Update UI components
            binding.textCity.setText(weatherData.getmCity());
            binding.textTemperature.setText(weatherData.getmTemperature());
            binding.textCondition.setText(weatherData.getmDescription());
        });
      
        mModel.add24HourForecastObserver(getViewLifecycleOwner(), weatherDataList -> {
            binding.hourlyListRoot.setAdapter(new WeatherHourlyRecyclerViewAdapter(weatherDataList));
        });

        mModel.addFiveDayForecastObserver(getViewLifecycleOwner(), weatherDataList -> {
            binding.dailyListRoot.setAdapter(new WeatherDailyRecyclerViewAdapter(weatherDataList));
        });
    }
}