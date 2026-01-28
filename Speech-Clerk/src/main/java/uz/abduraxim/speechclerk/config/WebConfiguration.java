package uz.abduraxim.speechclerk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://my-java-app-1-0-0.onrender.com",
                        "http://127.0.0.1:8000/",   // frontend dev
                        "http://localhost:8000/",   // frontend dev
                        "chrome-extension://lppopbgcfggihbhlmbaaabkidignjkoj"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}