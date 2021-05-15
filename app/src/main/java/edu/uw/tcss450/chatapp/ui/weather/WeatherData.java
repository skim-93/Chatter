package edu.uw.tcss450.chatapp.ui.weather;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private  String mTime;
    private  String mCity;
    private  Double mTemperature;
    private  String mCondition;
    private  Double mMinTemp;
    private  Double mMaxTemp;
    private  String mDate;

    /**
     * constructer for the current weather data
     * @param temperature
     * @param city
     * @param condition
     */
    public WeatherData(Double temperature, String city, String condition) {
        this.mCity = city;
        this.mTemperature = temperature;
        this.mCondition = condition;
    }

    /**
     * constructer for the hourly weather data
     * @param time
     * @param temperature
     * @param condition
     */
    public WeatherData(String time, Double temperature, String condition) {
        this.mTime = time;
        this.mTemperature = temperature;
        this.mCondition = condition;

    }

    /**
     * constructer for the daily weather data
     * @param condition
     * @param minTemp
     * @param maxTemp
     * @param date
     */
    public WeatherData(String condition, Double minTemp, Double maxTemp, String date) {
        this.mCondition = condition;
        this.mMinTemp = minTemp;
        this.mMaxTemp = maxTemp;
        this.mDate = date;
    }


    public String getmCity() {
        return mCity;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTemperature() {
        return String.valueOf((int)Math.round(mTemperature));
    }

    public String getmCondition() {
        return mCondition;
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
}
