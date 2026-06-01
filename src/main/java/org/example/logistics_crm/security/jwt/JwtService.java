package org.example.logistics_crm.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.logistics_crm.entity.client.Client;
import org.example.logistics_crm.entity.user.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;


public class JwtService {

    // Секретний ключ для підпису (мінімум 256 біт). У прод налаштуваннях береться з ENV
    private static final String SECRET_KEY = "9a72df6554c123456789abcdef0123456789abcdef0123456789abcdef012345";
    private static final long EXPIRATION_TIME = 86400000; // Токен діє 24 години

    public String extractUsername(String token) {
        return extractClaim(token, null);
    }

    public String extractClaim(String token, String name) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);
        Claims claim = jwtParser.parseClaimsJws(token).getBody();

        if (name == null) {
            return claim.getSubject();
        }
        return claim.get(name).toString();
    }

//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }

//    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return Jwts.builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    public String generateTokenClient(Client client) {
        return Jwts.builder()
                .setSubject(client.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .addClaims(Map.of("clientId", client.getId(), "email", client.getEmail(), "ROLE", "ROLE_CLIENT"))
                .compact();
    }

    public String generateTokenUser(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .addClaims(Map.of("userId", user.getId(), "email", user.getEmail(), "ROLE", "ROLE_USER"))
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);
        Claims claim = jwtParser.parseClaimsJws(token).getBody();
        Date expiration = claim.getExpiration();
        if (expiration.before(new Date())) {
            return true;
        }
        return false;
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
