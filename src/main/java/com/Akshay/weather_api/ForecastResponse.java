package com.Akshay.weather_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ForecastResponse {
    private WeatherResponse.Location location;
    private Forecast forecast;

    public WeatherResponse.Location getLocation(){
        return location;
    }
    public void setLocation(WeatherResponse.Location location){
        this.location = location;
    }

    public Forecast getForecast(){
        return forecast;
    }
    public void setForecast(Forecast forecast){
        this.forecast = forecast;
    }
    public static class Forecast{
        @JsonProperty("forecastday")
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getDays(){
            return forecastday;
        }
        public void setDay(List<ForecastDay> forecastday){
            this.forecastday = forecastday;
        }
    }

    public static class ForecastDay{
        private String date;
        private Day day;

        public String getDate(){
            return date;
        }
        public void setDate(String date){
            this.date = date;
        }

        public Day getDay(){
            return day;
        }
        public void setDay(){
            this.day = day;
        }
    }

    public static class Day{
        @JsonProperty("maxtemp_c")
        private double maxtemp_c;

        @JsonProperty("mintemp_c")
        private double mintemp_c;

        @JsonProperty("daily_chance_of_rain")
        private int daily_chance_of_rain;

        private WeatherResponse.Condition condition;

        public double getMaxtemp_c(){
            return maxtemp_c;
        }
        public void setMaxtemp_c(double maxtemp_c){
            this.maxtemp_c = maxtemp_c;
        }
        public double getMintemp_c(){
            return mintemp_c;
        }
        public void setMintemp_c(double mintemp_c){
            this.mintemp_c = mintemp_c;
        }
        public int getDaily_chance_of_rain(){
            return daily_chance_of_rain;
        }
        public void setDaily_chance_of_rain(int daily_chance_of_rain){
            this.daily_chance_of_rain = daily_chance_of_rain;
        }
        public WeatherResponse.Condition getCondition(){
            return condition;
        }
        public void setCondition(WeatherResponse.Condition condition){
            this.condition = condition;
        }
    }
}
