package edu.uw.tcss450.chatapp.ui.weather;


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

import java.util.List;


public class WeatherListViewModel extends AndroidViewModel {

    private MutableLiveData<List<WeatherData>> mFiveDayForecast;
    private MutableLiveData<List<WeatherData>> m24HourForecast;
    private MutableLiveData<WeatherData> mCurrentWeather;

    public WeatherListViewModel(@NonNull Application application) {
        super(application);
        mCurrentWeather = new MutableLiveData<>();
        m24HourForecast = new MutableLiveData<>();
        mFiveDayForecast = new MutableLiveData<>();
    }

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

    private void handleFiveDayForecastResult(final JSONObject result) {
    }

    private void handle24HourForecastResult(final JSONObject result) {
        //JSONArray forecasts = result.getJSONArray()
    }

    private void handleCurrentWeatherResult(final JSONObject result) {
        String weatherDescription = null, temperature = null, cityName = null;
        if (result.has("Weather Description")) {
            try {
                weatherDescription = (String) result.getString("Weather Description");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Weather List view model", "Error! missing weather description");
            return;
        }

        if (result.has("Temperature")) {
            try {
                temperature = (String) result.getString("Temperature");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Weather List view model", "Error! missing temperature");
            return;
        }

        if (result.has("City name")) {
            try {
                cityName = (String) result.getString("City name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Weather List view model", "Error! missing temperature");
            return;
        }
         mCurrentWeather.setValue(new WeatherData(weatherDescription,temperature,cityName));
    }

    private void handleError(final VolleyError error) {
        Log.e("Weather List View Model", error.networkResponse.toString());
    }
}
