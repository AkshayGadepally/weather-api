package com.Akshay.weather_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

    List<RequestLog> findByIpAddress(String ipAddress);

    List<RequestLog> findByCity(String city);

    List<RequestLog> findByRequestTimeBetween(LocalDateTime start,LocalDateTime end);

    @Query("SELECT r FROM RequestLog r WHERE r.ipAddress = ?1 AND r.requestTime > ?2")
    List<RequestLog> findRecentRequestById(String ipAddress,LocalDateTime since);

    @Query("SELECT r.city, COUNT(r) as count FROM RequestLog r GROUP BY r.city ORDER BY count DESC")
    List<Object[]> findPopularCities();

    long countBySuccess(Boolean success);
}
