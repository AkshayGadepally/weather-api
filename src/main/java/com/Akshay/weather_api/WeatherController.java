package com.Akshay.weather_api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather", description = "Weather Api endpoints with caching and rate limiting")
public class WeatherController {

    //Configuring Api value injection from application.properties into the string value


    @Autowired
    private WeatherService weatherService;

    @Autowired
    private RateLimiterService rateLimiterService;

    //Getting Current weather
    @Operation(
        summary = "Get current weather",
        description = "Get current weather information for a specific city. Results are cached for 15 minutes."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved weather data"),
        @ApiResponse(responseCode = "400", description = "Invalid city name"),
        @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
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
     @Operation(
        summary = "Get weather forecast",
        description = "Get weather forecast information for a specific city and number of days. Results are cached for 15 minutes."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved forecast data"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters"),
        @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
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


     @Operation(
        summary = "Check Rate limit status",
        description = "Check how many API requests you have remaining in the current hour"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully fetched rate limit status")
    })
    @GetMapping("/rate-limit-status")
    public ResponseEntity<Map<String,Object>> getRateLimitStatus(HttpServletRequest request){
        String ipAddress = getClientIp(request);
        long remainingRequests = rateLimiterService.getAvailableTokens(ipAddress);
        
        Map<String, Object> response = new HashMap<>();
        response.put("ip_address", ipAddress);
        response.put("remaining_requests", remainingRequests);
        response.put("rate_limiter", "100 requests per hour");
        response.put("status","active");

        return ResponseEntity.ok(response);
    }


    @Operation(
        summary = "Test endpoint",
        description = "Simple endpoint to test if the API is working"
    )
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test(){
        Map<String, String> response = new HashMap<>();
        response.put("message","Weather API is Working with PostgreSQL logging!");
        response.put("Status","Success");
        return ResponseEntity.ok(response);
    }

    public String getClientIp(HttpServletRequest request){
        String xfHeader = request.getHeader("X-Forworder-for");
        if(xfHeader == null || xfHeader.isEmpty()){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
}
