package org.example.logistics_crm.config;

import org.example.logistics_crm.repository.ClientRepository;
import org.example.logistics_crm.repository.UserRepository;
import org.example.logistics_crm.security.jwt.JwtAuthenticationFilter;
import org.example.logistics_crm.security.service.ClientUserDetailsService;
import org.example.logistics_crm.security.service.StaffUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, UserRepository userRepository, ClientRepository clientRepository,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain clientSecurityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/v1/client/**", "/api/v1/auth/client/**")
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/client/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .authenticationProvider(clientAuthenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
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
    @Order(2)
    public SecurityFilterChain staffSecurityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/v1/user/**", "/api/v1/auth/user/**")

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/user/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .authenticationProvider(staffAuthenticationProvider())

                // Вбудовуємо той самий фільтр і для контуру стафу
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider staffAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(staffUserDetailsService());
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public StaffUserDetailsService staffUserDetailsService() {
        return new StaffUserDetailsService(userRepository);
    }
}
