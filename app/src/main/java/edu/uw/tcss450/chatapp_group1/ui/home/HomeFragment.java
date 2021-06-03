package edu.uw.tcss450.chatapp_group1.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentHomeBinding;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentSignInBinding;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;
import edu.uw.tcss450.chatapp_group1.ui.auth.register.EmailVerificationFragmentDirections;
import edu.uw.tcss450.chatapp_group1.ui.auth.signin.SignInFragmentDirections;
import edu.uw.tcss450.chatapp_group1.ui.weather.WeatherListViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private WeatherListViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mModel = new ViewModelProvider(getActivity()).get(WeatherListViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentHomeBinding binding = FragmentHomeBinding.bind(getView());
        mModel.addCurrentWeatherObserver(getViewLifecycleOwner(), weatherData -> {
            // Update UI components
            binding.textCity.setText(weatherData.getmCity());
            binding.textTemperature.setText(weatherData.getmTemperature());
            binding.textCondition.setText(weatherData.getmDescription());
        });
    }
}