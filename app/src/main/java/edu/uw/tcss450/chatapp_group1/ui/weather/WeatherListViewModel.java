package edu.uw.tcss450.chatapp_group1.ui.weather;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherListViewModel extends AndroidViewModel {

    private MutableLiveData<List<WeatherData>> mFiveDayForecast;
    private MutableLiveData<List<WeatherData>> m24HourForecast;
    private MutableLiveData<WeatherData> mCurrentWeather;

    /** Toggle the zipcode search bar. False = Hidden, True = Visible */
    private MutableLiveData<Boolean> mToggleZipcode;

    public WeatherListViewModel(@NonNull Application application) {
        super(application);
        mCurrentWeather = new MutableLiveData<>();
        m24HourForecast = new MutableLiveData<>();
        mFiveDayForecast = new MutableLiveData<>();
        mToggleZipcode = new MutableLiveData<>();
        mToggleZipcode.setValue(false);
    }

    /**
     * Add an observer to this live data. This is a pass through method for
     * this classes MutableLiveData field of count.
     * @param owner the LifecycleOwner which controls the observer
     * @param observer the observer that will receive the events
     */
    public void addFiveDayForecastObserver(@NonNull LifecycleOwner owner,
                                           @NonNull Observer<? super List<WeatherData>> observer) {
        mFiveDayForecast.observe(owner, observer);
    }
    public void add24HourForecastObserver(@NonNull LifecycleOwner owner,
                                          @NonNull Observer<? super List<WeatherData>> observer) {
        m24HourForecast.observe(owner, observer);
    }

    public void addCurrentWeatherObserver(@NonNull LifecycleOwner owner,
                                          @NonNull Observer<? super WeatherData> observer) {
        mCurrentWeather.observe(owner, observer);
    }

    public void addToggleZipcodeObserver(@NonNull LifecycleOwner owner,
                                          @NonNull Observer<? super Boolean> observer) {
        mToggleZipcode.observe(owner, observer);
    }

    /**
     * Toggle the zipcode search bar.
     */
    public void toggleZipcode() {
        mToggleZipcode.setValue(!mToggleZipcode.getValue());
    }

    /**
     * Get state of the zipcode search bar visibility.
     */
    public boolean getZipcodeSearchBarState() {
        return mToggleZipcode.getValue();
    }

    /**
     * based on the given zipcode make a request to the server to get the weather data
     * for the current weather
     * @param zipcode
     */
    public void getCurrentWeather(String zipcode) {
        String url = "https://group1-tcss450-project.herokuapp.com/weather/current-weather/" + zipcode;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleCurrentWeatherResult,
                this::handleError);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * based on the given zipcode make a request to the server to get the weather data
     * for the hourly weather
     * @param zipcode
     */
    public void get24HourForecast(String zipcode) {
        String url = "https://group1-tcss450-project.herokuapp.com/weather/24-forecast/" + zipcode;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handle24HourForecastResult,
                this::handleError);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * based on the given zipcode make a request to the server to get the weather data
     * for the daily weather
     * @param zipcode
     */
    public void getFiveDayForecast(String zipcode) {
        String url = "https://group1-tcss450-project.herokuapp.com/weather/forecast/" + zipcode;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleFiveDayForecastResult,
                this::handleError);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * after recieving the weather data send the items to the WeatherData class
     * inorder to be constructed
     * @param result
     */
    private void handleFiveDayForecastResult(final JSONObject result) {
        List<WeatherData> weatherResults = new ArrayList<>();
        if (result.has("5 day forecasts")) {
            JSONArray forecasts = null;
            try {
                forecasts = result.getJSONArray("5 day forecasts");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject forecast;
                try {
                    forecast = (JSONObject) forecasts.get(i);
                    double minTemp = forecast.getDouble("Min Temperature");
                    double maxTemp = forecast.getDouble("Max Temperature");
                    String weatherDescription = forecast.getString("Weather Description");
                    String icon = forecast.getString("Icon");
                    String weatherDate = forecast.getString("Date");
                    WeatherData currentForecast = new WeatherData(weatherDate,weatherDescription, minTemp, maxTemp, icon);
                    weatherResults.add(currentForecast);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("Weather List view model", "Error! No daily forecasts");
        }
        mFiveDayForecast.setValue(weatherResults);
    }

    /**
     * after recieving the weather data send the items to the WeatherData class
     * inorder to be constructed
     * @param result
     */
    private void handle24HourForecastResult(final JSONObject result) {
        List<WeatherData> weatherResults = new ArrayList<>();
        if (result.has("daily-forecasts")) {
            JSONArray forecasts = null;
            try {
                forecasts = result.getJSONArray("daily-forecasts");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject forecast;
                try {
                    forecast = (JSONObject) forecasts.get(i);
                    double temperature = forecast.getDouble("Temperature");
                    String weatherDescription = forecast.getString("Weather Description");
                    String time = forecast.getString("Time");
                    String icon = forecast.getString("Icon");
                    WeatherData currentForecast = new WeatherData(weatherDescription,time,temperature,icon);
                    weatherResults.add(currentForecast);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("Weather List view model", "Error! No daily forecasts");
        }
        m24HourForecast.setValue(weatherResults);
    }

    /**
     * after recieving the weather data send the items to the WeatherData class
     * inorder to be constructed
     * @param result
     */
    private void handleCurrentWeatherResult(final JSONObject result) {
        String weatherDescription = null, cityName = null, icon = null;
        double temperature = 0;
        if (result.has("Weather Description") && result.has("Temperature") &&
                result.has("City name")) {
            try {
                weatherDescription = (String) result.getString("Weather Description");
                temperature = result.getDouble("Temperature");
                cityName = (String) result.getString("City name");
               // icon = (String) result.getString("Icon");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Weather List view model", "Error! missing information");
            return;
        }
        mCurrentWeather.setValue(new WeatherData(weatherDescription,temperature,cityName));
    }

    private void handleError(final VolleyError error) {
        Log.e("Weather List View Model", error.networkResponse.toString());
    }
}
