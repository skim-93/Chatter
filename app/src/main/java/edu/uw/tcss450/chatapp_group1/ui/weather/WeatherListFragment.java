package edu.uw.tcss450.chatapp_group1.ui.weather;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentWeatherListBinding;

/**
 * A simple {@link Fragment} subclass.

 */
public class WeatherListFragment extends Fragment {
    private WeatherListViewModel mModel;
    private FragmentWeatherListBinding binding;

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
        binding = FragmentWeatherListBinding.bind(getView());

        binding.mapButton.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        WeatherListFragmentDirections.actionNavigationWeatherToLocationFragment())
        binding.buttonZipcodeSearch.setOnClickListener(button ->
                updateZipcodeSearchBar()
        );

        binding.mapButton.setOnClickListener(button -> {
            NavDirections directions = WeatherListFragmentDirections.actionNavigationWeatherToWeatherMapFragment();
            Navigation.findNavController(getView()).navigate(directions);
        });

        binding.enterZipcode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Check to see if zipcode is valid
                    String zipcode = binding.enterZipcode.getText().toString();
                    if (!isValidZipcode(zipcode)) {
                        Toast toast = Toast.makeText(getContext(), "Invalid Zipcode!", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        mModel.getCurrentWeather(zipcode);
                        mModel.get24HourForecast(zipcode);
                        mModel.getFiveDayForecast(zipcode);
                    }
                    hideKeyboard(getActivity());
                    updateZipcodeSearchBar();
                    return true;
                }
                return false;
            }
        });

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

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void updateZipcodeSearchBar() {
        // Toggle zipcode so the search bar disappears/appears
        mModel.toggleZipcode();
        if (mModel.getZipcodeSearchBarState()) {
            binding.enterZipcode.setVisibility(View.VISIBLE);
        } else {
            binding.enterZipcode.setVisibility(View.GONE);
        }
    }

    private static boolean isValidZipcode(String zipcode) {
        if (zipcode.length() != 5 || !onlyDigits(zipcode, zipcode.length())) {
            return false;
        }
        return true;
    }

    private static boolean onlyDigits(String str, int n) {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) >= '0'
                    && str.charAt(i) <= '9') {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
}