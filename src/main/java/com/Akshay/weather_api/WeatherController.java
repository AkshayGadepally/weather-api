package com.Akshay.weather_api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    //Configuring Api value injection from application.properties into the string value


    @Autowired
    private WeatherService weatherService;

    //Getting Current weather
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentWeather(@RequestParam String city){


        try{
            WeatherResponse weatherResponse = weatherService.getCurrentWeather(city);
            return ResponseEntity.ok(weatherResponse);
        } catch(Exception e){
            Map<String, String> error =  new HashMap<>();
            error.put("ERROR:", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    //Getting Weather Forecast
    @GetMapping("/forecast")
    public ResponseEntity<?> getForecast(@RequestParam String city, @RequestParam(defaultValue = "3") int days){
        try{
            if(days < 1 || days > 10){
                Map<String,String> error = new HashMap<>();
                error.put("ERROR: ","The number of days have to between 1 and 10");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            ForecastResponse forecastResponse = weatherService.getForecastWeather(city,days);
            return ResponseEntity.ok(forecastResponse);
        }catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("ERROR: ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test(){
        Map<String, String> response = new HashMap<>();
        response.put("message","Weather API is Working with PostgreSQL logging!");
        response.put("Status","Success");
        return ResponseEntity.ok(response);
    }
}
