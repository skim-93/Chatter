package edu.uw.tcss450.chatapp.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.chatapp.R;
import edu.uw.tcss450.chatapp.databinding.FragmentWeatherListBinding;

/**
 * A simple {@link Fragment} subclass.

 */
public class WeatherListFragment extends Fragment {
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
        binding.buttonMap.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherListFragmentDirections.actionNavigationWeatherToWeatherMapFragment())
        );
    }
}