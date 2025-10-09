package com.Akshay.weather_api;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI weatherApiOpenApi(){
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development Server");

        Contact contact = new Contact();
        contact.setName("Akshay");
        contact.setEmail("gadepallyakshay1999@gmail.com");
        contact.setUrl("https://github.com/AkshayGadepally");

        License license = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                    .title("Weather API")
                    .version("1.0")
                    .contact(contact)
                    .description("A production ready Weather API application with caching, rate limiting and request logging")
                    .license(license);
    
        return new OpenAPI().info(info).servers(List.of(localServer));
    }
}
