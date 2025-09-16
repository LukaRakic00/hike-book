package rs.hikeandbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:.env", ignoreResourceNotFound = true)
public class EnvConfig {
    // Ova konfiguracija omogućava Spring Boot-u da čita .env fajl
} 