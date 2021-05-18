package edu.uw.tcss450.chatapp.ui.weather;

import java.io.Serializable;
import java.util.List;

public class WeatherData implements Serializable {
    private  String mTime;
    private  String mCity;
    private  String mTemperature;
    private  String mDescription;
    private  Double mMinTemp;
    private  Double mMaxTemp;
    private List<WeatherData> mForecasts;

    public WeatherData(String desc, String temp, String city) { // Constructor for Current weather
        this.mDescription = desc;
        this.mTemperature = temp;
        this.mCity = city;
    }

    public WeatherData(List<WeatherData> forecast) { // Constructor for 5 day forecast and 24 hour forecast
        mForecasts = forecast;
    }

    public String getmTemperature() {
        return mTemperature;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmTime() {
        return mTime;
    }

    public String  getmMinTemperature() {
        return String.valueOf((int)Math.round(mMinTemp));
    }

    public String  getmMaxTemperature() {
        return String.valueOf((int)Math.round(mMaxTemp));
    }

    public List<WeatherData> getmForecasts() {return mForecasts;}
}
