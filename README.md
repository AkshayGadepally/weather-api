☀️ Weather API - Production Ready Spring Boot Application
A comprehensive RESTful Weather API built with Spring Boot featuring Redis caching, PostgreSQL logging, rate limiting, and interactive Swagger documentation.
🚀 Features
Core Functionality

Current Weather - Get real-time weather data for any city
Weather Forecast - 1-10 day weather forecasts
City Search - Support for cities worldwide via WeatherAPI.com

Performance & Optimization

Redis Caching - Lightning-fast responses (~50ms for cached data)

Current weather: 15-minute TTL
Forecasts: 1-hour TTL


Request Logging - All requests logged to PostgreSQL with performance metrics
Rate Limiting - 100 requests per hour per IP (configurable)

Developer Experience

Swagger Documentation - Interactive API docs at /swagger-ui.html
Cache Management - Endpoints to view and clear cache
Rate Limit Status - Check remaining API calls
Comprehensive Error Handling - Clean JSON error responses


🛠️ Tech Stack
TechnologyPurposeSpring Boot 3.5.5REST API FrameworkPostgreSQLPrimary database for request loggingRedisCaching layer for performanceWeatherAPI.comWeather data providerBucket4jRate limiting implementationSwagger/OpenAPIAPI documentationMavenDependency management

📋 Prerequisites

Java 21 or higher
PostgreSQL 12+ installed and running
Redis installed and running
Maven 3.6+
WeatherAPI.com API Key (free tier available)


⚙️ Setup & Installation
1. Clone the Repository
bashgit clone https://github.com/yourusername/weather-api.git
cd weather-api
2. Configure PostgreSQL
sql-- Connect to PostgreSQL
psql -U postgres

-- Create database and user
CREATE DATABASE weather_db;
CREATE USER weather_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE weather_db TO weather_user;
3. Start Redis
bash# Linux/Mac
redis-server

# Windows (WSL)
redis-server --daemonize yes

# Verify Redis is running
redis-cli ping
# Should return: PONG
4. Configure Application
Edit src/main/resources/application.properties:
properties# Weather API Key (get free key at weatherapi.com)
weather.api.key=YOUR_API_KEY_HERE

# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/weather_db
spring.datasource.username=weather_user
spring.datasource.password=your_password

# Redis Configuration (default values)
spring.data.redis.host=localhost
spring.data.redis.port=6379
5. Build and Run
bash# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
The API will start on http://localhost:8080

📚 API Endpoints
Weather Endpoints
Get Current Weather
httpGET /api/weather/current?city=London
Response:
json{
  "location": {
    "name": "London",
    "country": "United Kingdom"
  },
  "current": {
    "temp_c": 15.1,
    "humidity": 82,
    "wind_kph": 20.5,
    "condition": {
      "text": "Light rain"
    }
  }
}
Get Weather Forecast
httpGET /api/weather/forecast?city=Paris&days=3
Parameters:

city (required) - City name
days (optional) - Number of forecast days (1-10, default: 3)

Check Rate Limit Status
httpGET /api/weather/rate-limit-status
Response:
json{
  "ip_address": "127.0.0.1",
  "remaining_requests": 95,
  "rate_limit": "100 requests per hour",
  "status": "active"
}
Cache Management
View Cache Statistics
httpGET /api/cache/stats
Clear All Caches
httpDELETE /api/cache/clear-all
# or via GET for browser
GET /api/cache/clear-all
Clear Specific Cache
httpDELETE /api/cache/clear/currentWeather

🧪 Testing
Using cURL
bash# Current weather
curl "http://localhost:8080/api/weather/current?city=Tokyo"

# Forecast
curl "http://localhost:8080/api/weather/forecast?city=Mumbai&days=5"

# Rate limit status
curl "http://localhost:8080/api/weather/rate-limit-status"

# Cache stats
curl "http://localhost:8080/api/cache/stats"
Using Swagger UI
Navigate to: http://localhost:8080/swagger-ui.html
Interactive API documentation where you can test all endpoints directly in your browser!

📊 Performance Metrics
Response Times

Without Cache: ~300-400ms (external API call)
With Cache: ~40-60ms (Redis lookup)
Performance Improvement: 6-8x faster

Caching Strategy
EndpointCache DurationReasoningCurrent Weather15 minutesWeather changes frequentlyForecast1 hourForecasts are more stable
Rate Limiting

Limit: 100 requests per hour per IP
Status Code: 429 (Too Many Requests)
Reset: Automatic hourly reset


🗄️ Database Schema
request_logs Table
Tracks all API requests with performance metrics:
sqlCREATE TABLE request_logs (
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    endpoint VARCHAR(255) NOT NULL,
    ip_address VARCHAR(50),
    request_time TIMESTAMP NOT NULL,
    response_time_ms BIGINT,
    success BOOLEAN,
    error_message TEXT
);
Sample Queries:
sql-- View recent requests
SELECT * FROM request_logs ORDER BY request_time DESC LIMIT 10;

-- Popular cities
SELECT city, COUNT(*) as requests 
FROM request_logs 
GROUP BY city 
ORDER BY requests DESC 
LIMIT 5;

-- Average response time
SELECT AVG(response_time_ms) as avg_response_time 
FROM request_logs 
WHERE success = true;

🔒 Security Features

Rate Limiting - Prevents API abuse (100 req/hour per IP)
Input Validation - Validates all user inputs
Error Handling - No sensitive data in error messages
CORS - Configurable cross-origin policies


🚀 Deployment
Environment Variables
For production, use environment variables instead of hardcoded values:
bashexport WEATHER_API_KEY=your_production_key
export DB_URL=jdbc:postgresql://prod-db:5432/weather_db
export DB_USERNAME=prod_user
export DB_PASSWORD=prod_password
export REDIS_HOST=prod-redis
export REDIS_PORT=6379
Docker Support (Optional)
dockerfileFROM openjdk:21-jdk-slim
COPY target/weather-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

📈 Future Enhancements
Potential features for future versions:

 User authentication & API keys
 Favorite cities management
 Historical weather data
 Weather alerts and notifications
 GraphQL API support
 Prometheus metrics
 Docker Compose setup
 CI/CD pipeline


🐛 Troubleshooting
Redis Connection Issues
bash# Check if Redis is running
redis-cli ping

# Start Redis
redis-server
PostgreSQL Connection Issues
bash# Check PostgreSQL status
sudo systemctl status postgresql

# Test connection
psql -U weather_user -d weather_db -h localhost
Port Already in Use
bash# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or change port in application.properties
server.port=8081

📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

👨‍💻 Author
Akshay

GitHub: AkshayGadepally
Email: gadepallyakshay1999@gmail.com


🙏 Acknowledgments

WeatherAPI.com - Weather data provider
Spring Boot - Application framework
Redis - Caching solution
PostgreSQL - Database system


📞 Support
For issues, questions, or contributions:

Open an issue on GitHub
Email: gadepallyakshay1999@gmail.com
Documentation: http://localhost:8080/swagger-ui.html


⭐ If you found this project helpful, please give it a star!
