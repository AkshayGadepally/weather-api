package com.Akshay.weather_api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherResponse {
            private Location location;
            private Current current;

            public Location getLocation() {
                    return location;
                }
                public void setLocation (Location location){
                    this.location = location;
                }

                public Current getCurrent () {
                    return current;
                }
                public void setCurrent (Current current){
                    this.current = current;
                }

                public static class Location {
                    private String name;
                    private String country;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getCountry() {
                        return country;
                    }

                    public void setCountry(String country) {
                        this.country = country;
                    }
                }

                public static class Current {
                    @JsonProperty("temp_c")
                    private double temperature;

                    private int humidity;

                    @JsonProperty("wind_kph")
                    private double windSpeed;

                    private Condition condition;

                    public double getTemperature(){
                        return temperature;
                    }
                    public void setTemperature(double temperature){
                        this.temperature = temperature;
                    }
                    public int getHumidity(){
                        return humidity;
                    }
                    public void setHumidity(int humidity){
                        this.humidity = humidity;
                    }
                    public double getWindSpeed(){
                        return windSpeed;
                    }
                    public void setWindSpeed(double windSpeed){
                        this.windSpeed = windSpeed;
                    }
                    public Condition getCondition(){
                        return condition;
                    }
                    public void setCondition(Condition condition) {
                        this.condition = condition;
                    }
                }

                public static class Condition{
                    private String text;

                    public String getText(){
                        return text;
                    }
                    public void setText(String text){
                        this.text = text;
                    }
                }
}