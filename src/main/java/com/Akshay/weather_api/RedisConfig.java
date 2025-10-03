package com.Akshay.weather_api;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {
    
    //key pair
    RedisSerializationContext.SerializationPair<String> keyPair = RedisSerializationContext.SerializationPair
                                                                    .fromSerializer(new StringRedisSerializer());

    //value pair                                                                
    RedisSerializationContext.SerializationPair<Object> valuePair = RedisSerializationContext.SerializationPair
                                                                    .fromSerializer(new GenericJackson2JsonRedisSerializer());

    //cacheManager - default interface that manages and provides access to caches    
    //RedisConnectionFactory - factory interface for creating connections to a Redis data store    
    //RedisCacheConfiguration - configuration settings for Redis caches
    //RedisCacheManager - implementation of CacheManager that uses Redis as the underlying cache store                                                        
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory){

        //default caching settings
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration
                                                 .defaultCacheConfig()
                                                 .entryTtl(Duration.ofMinutes(15))
                                                 .serializeKeysWith(keyPair)
                                                 .serializeValuesWith(valuePair)
                                                 .disableCachingNullValues();
        
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        cacheConfigurations.put("Current Weather",
                                  RedisCacheConfiguration
                                  .defaultCacheConfig()
                                  .entryTtl(Duration.ofMinutes(15))
                                  .serializeKeysWith(keyPair)
                                  .serializeValuesWith(valuePair)
        );

        cacheConfigurations.put("Forecast Weather", RedisCacheConfiguration
                                  .defaultCacheConfig()
                                  .entryTtl(Duration.ofMinutes(30))
                                  .serializeKeysWith(keyPair)
                                  .serializeValuesWith(valuePair)
        );

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
