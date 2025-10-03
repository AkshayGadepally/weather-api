package com.Akshay.weather_api;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    private final String CURRENT_WEATHER_URL = "http://api.weatherapi.com/v1/current.json";
    private final String FORECAST_URL = "http://api.weatherapi.com/v1/forecast.json";

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RequestLogRepository requestLogRepository;

    public WeatherResponse getCurrentWeather(String city){
        long startTime = System.currentTimeMillis();


        RequestLog log = new RequestLog(city, "api/weather/current/", "localhost");

        try{
            String url = String.format("%s?key=%s&q=%s&aqi=no", CURRENT_WEATHER_URL, apiKey, city);
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

            log.setSuccess(true);
            log.setResponseTimeMs(System.currentTimeMillis() - startTime);
            return response;
        }
        catch(Exception e){
            log.setSuccess(false);
            log.setErrorMessage(e.getMessage());
            log.setResponseTimeMs(System.currentTimeMillis() - startTime);

            throw new RuntimeException("Could not get weather for "+ city, e);
        }
        finally {
            requestLogRepository.save(log);
        }
    }

    public ForecastResponse getForecastWeather(String city, int days){
        long startTime = System.currentTimeMillis();


        RequestLog log = new RequestLog(city, "api/weather/forecast", "localhost");

        try{
            String url = String.format("%s?key=%s&q=%s&days=%d&aqi=no&alerts=no",FORECAST_URL, apiKey,city,days);
            ForecastResponse response =  restTemplate.getForObject(url, ForecastResponse.class);

            log.setSuccess(true);
            log.setResponseTimeMs(System.currentTimeMillis()-startTime);
            return response;
        }
        catch(Exception e){
            log.setSuccess(false);
            log.setErrorMessage(e.getMessage());
            log.setResponseTimeMs(System.currentTimeMillis()-startTime);

            throw new RuntimeException("Could not get Weather for " + city, e);
        }
        finally{
            requestLogRepository.save(log);
        }
    }
}
