package com.Akshay.weather_api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cache")
@Tag(name = "Cache Management", description = "Endopoints to manage and monitor Redis Cache")
public class CacheController {
    
    @Autowired
    private CacheManager cacheManager;

    // For adding cache stats
    @Operation(summary = "Stats endopoint", description = "View information about active caches") 
    @GetMapping("/stats")
    public ResponseEntity<Map<String,Object>> getCacheStats(){
        Map<String,Object> stats = new HashMap<>();

        stats.put("cacheNames",cacheManager.getCacheNames());

        Map<String,Object> cacheSizes = new HashMap<>();

        for(String cacheName : cacheManager.getCacheNames()){
            Cache cache = cacheManager.getCache(cacheName);
            if(cache != null){
                cacheSizes.put(cacheName, "Active");
            }
        }

        stats.put("cacheStatus", cacheSizes);

        return ResponseEntity.ok(stats);
    }

    // Dor clearing a particular cache
    @Operation(summary = "Clear specific cache", description = "Clear all entries from a specific cache")
    @DeleteMapping("/api/{cacheName}")
    public ResponseEntity<Map<String, String>> clearCache(@PathVariable String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if(cache != null){
            cache.clear();
            HashMap<String ,String> response= new HashMap<>();
            response.put("message", "Cache '" + cacheName + "' cleared successfully.");
            response.put("ststus","success");
            return ResponseEntity.ok(response);
        }
            HashMap<String ,String> response= new HashMap<>();
            response.put("message", "Cache '" + cacheName + "' cleared successfully.");
            response.put("ststus","success");
            return ResponseEntity.badRequest().body(response);
    }

    @Operation(summary = "Clear all caches", description = "Clear all cached data from Redis")
    @DeleteMapping("/clear-all")
    public ResponseEntity<Map<String,String>> clearAllCaches(){
        for(String cacheName : cacheManager.getCacheNames()){
            Cache cache = cacheManager.getCache(cacheName);
            if(cache != null){
                cache.clear();
            }
        }
        HashMap<String, String> response = new HashMap<>();
        response.put("message","All caches cleared successfully.");
        response.put("status","success");
        return ResponseEntity.ok(response);
    }

}
