package com.Akshay.weather_api;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    private final String CURRENT_WEATHER_URL = "http://api.weatherapi.com/v1/current.json";
    private final String FORECAST_URL = "http://api.weather.com/v1/forecast.json";

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RequestLogRepository requestLogRepository;

    public WeatherResponse getCurrentWeather(String city, HttpServletRequest request){
        long startTime = System.currentTimeMillis();
        String ipAddress = getClientIpAddress(request);

        RequestLog log = new RequestLog(city, "api/weather/current/", ipAddress);

        try{
            String url = String.format("%s?key=%s&q=%s", CURRENT_WEATHER_URL, apiKey, city);
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

            log.setSuccess(true);
            log.setResponseTimeMs(System.currentTimeMillis() - startTime);
            return response;
        }
        catch(Exception e){
            log.setSuccess(false);
            log.setErrorMessage(e.getMessage());
            log.setResponseTimeMs(System.currentTimeMillis() - startTime);

            throw new RuntimeException("Could not get weather for"+ city, e);
        }
        finally {
            requestLogRepository.save(log);
        }
    }

    public ForecastResponse getForecastWeather(String city, HttpServletRequest request){
        long startTime = System.currentTimeMillis();
        String ipAddress = getClientIpAddress(request);

        RequestLog log = new RequestLog(city, "api/weather/forecast", ipAddress);

        try{
            String url = String.format("%s?key=%s&q=%s",FORECAST_URL, apiKey,city);
            ForecastResponse response =  restTemplate.getForObject(url, ForecastResponse.class);

            log.setSuccess(true);
            log.setResponseTimeMs(System.currentTimeMillis()-startTime);
            return response;
        }
        catch(Exception e){
            log.setSuccess(false);
            log.setErrorMessage(e.getMessage());
            log.setResponseTimeMs(System.currentTimeMillis()-startTime);

            throw new RuntimeException("Could not get Weather for" + city, e);
        }
        finally{
            requestLogRepository.save(log);
        }
    }
}
