package edu.uw.tcss450.chatapp_group1.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentWeatherMapBinding;

/**
 * A simple {@link Fragment} subclass.

 */
public class WeatherMapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_map, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentWeatherMapBinding binding = FragmentWeatherMapBinding.bind(getView());

//        binding.buttonConfirmLocation.setOnClickListener(button ->
//                Navigation.findNavController(getView()).navigate(
//                        WeatherMapFragmentDirections.actionWeatherMapFragmentToWeatherFragment())
//        );

    }
}