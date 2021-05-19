package edu.uw.tcss450.chatapp.ui.weather;

import java.io.Serializable;

public class WeatherData implements Serializable {
    private  String mTime;
    private  String mCity;
    private  double mTemperature;
    private  String mDescription;
    private  Double mMinTemp;
    private  Double mMaxTemp;

    public WeatherData(String desc, double temp, String city) {
        this.mDescription = desc;
        this.mTemperature = temp;
        this.mCity = city;
    }

    public WeatherData(String desc, double min, double max) {
        this.mDescription = desc;
        this.mMinTemp = min;
        this.mMaxTemp = max;
    }

    public WeatherData(String desc, String time, double temp) {
        this.mDescription = desc;
        this.mTemperature = temp;
        this.mTime = time;
    }

    public String getmTemperature() {
        return mTemperature + "°";
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
        return (int)Math.round(mMinTemp) + "°";
    }

    public String  getmMaxTemperature() {
        return (int)Math.round(mMaxTemp) + "°";
    }
}
