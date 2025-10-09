package com.Akshay.weather_api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor{
    
    @Autowired
    private RateLimiterService rateLimiterService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String ipAddress = getClientIp(request);

        if(rateLimiterService.tryConsume(ipAddress)){
            long remainingTokens =  rateLimiterService.getAvailableTokens(ipAddress);
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(remainingTokens));
            return true;
        }
        else{
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Too many requests. Rate limit : 100 requests per hour.\",\"status\":429}");
            return false;
        }
    }

    private String getClientIp(HttpServletRequest request){
        String xfHeader = request.getHeader("X-Forworder-for");
        if(xfHeader == null || xfHeader.isEmpty()){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
    
}

