package com.Akshay.weather_api;

import com.Akshay.weather_api.WeatherResponse;
import com.Akshay.weather_api.ForecastResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    //Configuring Api value injection from application.properties into the string value
    @Value("${weather.api.key}")
    private String apiKey;

    //Weatherapi endpoints
    private final String CURRENT_WEATHER_URL="http://api.weatherapi.com/v1/current.json";
    private final String FORECAST_URL="http://api.weatherapi.com/v1/forecast.json";

    private final RestTemplate restTemplate = new RestTemplate();

    //Getting Current weather
    @GetMapping("/current")
    public Object getCurrentWeather(@RequestParam String city){
        String url = String.format("%s?key=%s&q=%s&aqi=no", CURRENT_WEATHER_URL, apiKey, city);
        try{
            WeatherResponse weatherResponse = restTemplate.getForObject(url , WeatherResponse.class);
            return weatherResponse;
        } catch(Exception e){
            return "{\"error\":\"Could not get weather for "+ city+ "\"}";
        }
    }

    //Getting Weather Forecast
    @GetMapping("/forecast")
    public Object getForecast(@RequestParam String city, @RequestParam(defaultValue = "3") int days){
        String url =String.format("%s?key=%s&q=%s&days=%d&api=no&alerts=no", CURRENT_WEATHER_URL, apiKey, city, days);
        try{
            ForecastResponse forecastResponse = restTemplate.getForObject(url , ForecastResponse.class);
            return forecastResponse;
        }catch(Exception e){
            return "Error getting forecast data:" +e.getMessage();
        }
    }

    @GetMapping("/test")
    public String test(){
        return "Weather api is working";
    }
}
