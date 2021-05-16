package edu.uw.tcss450.chatapp.ui.weather;


public class WeatherPost {
    private final String mTime;
    private final String mDate;
    private final String mTemperature;
    private final String mCondition;
    private final String mMinTemperature;
    private final String mMaxTemperature;

    /**
     * Helper class for building Credentials.
     *
     * @author Charles Bryan
     */
    public static class Builder {
        private String mTime = "";
        private String mDate = "";
        private String mTemperature = "";
        private String mCondition = "";
        private String mMinTemperature = "";
        private String mMaxTemperature = "";

        public Builder(String time, String temperature) {
            this.mTime = time;
            this.mTemperature = temperature;
        }

        public Builder addCondition(final String val) {
            mCondition = val;
            return this;
        }

        public Builder addDate(final String val) {
            mDate = val;
            return this;
        }

        public Builder addMinTemperature(final String val) {
            mMinTemperature = val;
            return this;
        }

        public Builder addMaxTemperature(final String val) {
            mMaxTemperature = val;
            return this;
        }

        public WeatherPost build() {
            return new WeatherPost(this);
        }
    }

    private WeatherPost(final WeatherPost.Builder builder) {
        this.mDate = builder.mDate;
        this.mTemperature = builder.mTemperature;
        this.mCondition = builder.mCondition;
        this.mTime = builder.mTime;
        this.mMinTemperature = builder.mMinTemperature;
        this.mMaxTemperature = builder.mMaxTemperature;
    }

    public String getDate() {
        return mDate;
    }

    public String getmTemperature() {
        return mTemperature;
    }

    public String getmCondition() {
        return mCondition;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmMinTemperature() {
        return mMinTemperature;
    }

    public String getmMaxTemperature() {
        return mMaxTemperature;
    }
}
