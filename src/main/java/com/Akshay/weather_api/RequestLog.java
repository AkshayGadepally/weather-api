package com.Akshay.weather_api;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_logs")
public class RequestLog {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="city",nullable = false )
    private String city;

    @Column(name="endpoint", nullable=false)
    private String endpoint;

    @Column(name="ip_address")
    private String ipAddress;

    @Column(name="request_time", nullable=false)
    private LocalDateTime requestTime;

    @Column(name="response_time_ms")
    private Long responseTimeMs;

    @Column(name="success")
    private Boolean success;

    @Column(name="error_message")
    private String errorMessage;

    public RequestLog() {
        this.requestTime = LocalDateTime.now();
    }

    public RequestLog(String city, String endpoint, String ipaddress){
        this();
        this.city = city;
        this.endpoint = endpoint;
        this.ipAddress = ipaddress;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city=city;
    }

    public String getEndpoint(){
        return endpoint;
    }
    public void setEndPoint(String endpoint){
        this.endpoint=endpoint;
    }

    public String getIpAddress(){
        return ipAddress;
    }
    public void setIpAddress(String ipAddress){
        this.ipAddress=ipAddress;
    }

    public LocalDateTime getRequestTime(){
        return requestTime;
    }
    public void setRequestTime(LocalDateTime requestTime){
        this.requestTime=requestTime;
    }

    public Long getResponseTimeMs(){
        return responseTimeMs;
    }
    public void setResponseTimeMs(Long responseTimeMs){
        this.responseTimeMs=responseTimeMs;
    }

    public Boolean getSuccess(){
        return success;
    }
    public void setSuccess(Boolean success){
        this.success=success;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage=errorMessage;
    }

}

