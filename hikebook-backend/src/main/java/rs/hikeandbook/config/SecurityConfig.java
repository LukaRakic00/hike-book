package rs.hikeandbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/activities").permitAll()
                .requestMatchers("/api/trails").permitAll()
                .requestMatchers("/api/trails/{id}").permitAll()
                .requestMatchers("/api/explore/**").permitAll()
                .requestMatchers("/api/upload/**").permitAll()
                .requestMatchers("/api/photos/trail/{trailId}").permitAll()
                .requestMatchers("/api/photos/trail/{trailId}/all").permitAll()
                .requestMatchers("/api/photos/{id}").permitAll()
                // Protected endpoints
                .requestMatchers("/api/trails/favorites").authenticated()
                .requestMatchers("/api/trails/{id}/favorite").authenticated()
                .requestMatchers("/api/photos").authenticated()
                .requestMatchers("/api/photos/{id}/main").authenticated()
                .anyRequest().authenticated()
            );
        
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Dozvoli Render frontend i localhost za development
        configuration.setAllowedOriginPatterns(Arrays.asList(
            "https://*.onrender.com",
            "http://localhost:*",
            "https://localhost:*"
        ));
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 