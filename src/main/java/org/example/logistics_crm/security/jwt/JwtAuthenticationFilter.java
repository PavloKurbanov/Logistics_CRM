package org.example.logistics_crm.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.example.logistics_crm.security.service.ClientUserDetailsService;
import org.example.logistics_crm.security.service.StaffUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ClientUserDetailsService clientUserDetailsService;
    private final StaffUserDetailsService staffUserDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   ClientUserDetailsService clientUserDetailsService,
                                   StaffUserDetailsService staffUserDetailsService) {
        this.jwtService = jwtService;
        this.clientUserDetailsService = clientUserDetailsService;
        this.staffUserDetailsService = staffUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Якщо заголовка немає або він не починається з Bearer — пропускаємо запит далі по ланцюжку фільтрів
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        // 2. Якщо email успішно витягнуто і в поточному потоці запиту користувач ще не авторизований
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Інтелектуальний вибір сервісу: дивимося на URL, щоб не робити холостих запитів
            UserDetailsService userDetailsService = selectUserDetailsService(request);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // 3. Якщо токен математично валідний — створюємо квиток автентифікації для Spring
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Кладемо квиток у контекст потоку. Тепер користувач залогінений!
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private UserDetailsService selectUserDetailsService(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.contains("/api/v1/client/")) {
            return clientUserDetailsService;
        }
        return staffUserDetailsService;
    }
}
