package com.Akshay.weather_api;
import org.springframework.stereotype.Service;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class RateLimiterService {
    
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    // helps create a new bucket if there is none for this key
    public Bucket resolveBucket(String key){
        return cache.computeIfAbsent(key, k -> createNewBucket());
    }

    public Bucket createNewBucket(){
        Bandwidth limit = Bandwidth.builder().capacity(100).refillIntervally(100, Duration.ofHours(1)).build();
        return Bucket.builder().addLimit(limit).build();
    }

    public boolean tryConsume(String key){
        Bucket bucket = resolveBucket(key);
        return bucket.tryConsume(1);
    }

    public long getAvailableTokens(String key){
        Bucket bucket = resolveBucket(key);
        return bucket.getAvailableTokens();
    }
}
