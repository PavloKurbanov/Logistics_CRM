package org.example.logistics_crm.config;

import org.example.logistics_crm.repository.ClientRepository;
import org.example.logistics_crm.repository.UserRepository;
import org.example.logistics_crm.security.service.ClientUserDetailsService;
import org.example.logistics_crm.security.service.StaffUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserRepository userRepository, ClientRepository clientRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // для Postman і REST API під час розробки
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        // відкриваємо доступ до всіх endpoint-ів
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // усе інше теж можна тимчасово відкрити
                        .anyRequest().authenticated()
                )

                // basic auth можна залишити, але зараз не обов’язково
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider staffAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(staffUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public DaoAuthenticationProvider clientAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(clientUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public ClientUserDetailsService clientUserDetailsService() {
        return new ClientUserDetailsService(clientRepository);
    }

    @Bean
    public StaffUserDetailsService staffUserDetailsService() {
        return new StaffUserDetailsService(userRepository);
    }
}
