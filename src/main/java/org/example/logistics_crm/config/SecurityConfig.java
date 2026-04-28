package org.example.logistics_crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // для Postman і REST API під час розробки
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        // відкриваємо доступ до всіх endpoint-ів
                        .requestMatchers("/api/**").permitAll()

                        // усе інше теж можна тимчасово відкрити
                        .anyRequest().permitAll()
                )

                // basic auth можна залишити, але зараз не обов’язково
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
