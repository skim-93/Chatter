package edu.uw.tcss450.chatapp_group1.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentWeatherListBinding binding = FragmentWeatherListBinding.bind(getView());
        mWeathers = new ArrayList<>();
        for(int i = 0; i<= 23; i++){
            WeatherData listItem = new WeatherData(
                    "03:00 am", 66.9, "Sunny");
            mWeathers.add(listItem);
        }

        binding.hourlyListRoot.setAdapter(new WeatherHourlyRecyclerViewAdapter(mWeathers));
        mWeathers = new ArrayList<>();
        for(int i = 0; i<= 9; i++){
            WeatherData listItem = new WeatherData(
                    "Sunny", 56.0, 75.5, "05/13/21");
            mWeathers.add(listItem);
        }

        binding.dailyListRoot.setAdapter(new WeatherDailyRecyclerViewAdapter(mWeathers));

        binding.buttonZipcodeSearch.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherListFragmentDirections.actionNavigationWeatherToWeatherMapFragment())
        );
    }


}