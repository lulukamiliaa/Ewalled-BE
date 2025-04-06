package com.odp.walled.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    // @Value("${security.jwt.expiration-time}")
    // private long jwtExpiration;

    //new 
    @Value("${security.jwt.access-expiration-time}")
    private long accessTokenExpiration;

    @Value("${security.jwt.refresh-expiration-time}")
    private long refreshTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // public String generateToken(UserDetails userDetails) {
    //     return generateToken(new HashMap<>(), userDetails);
    // }

    // public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    //     return buildToken(extraClaims, userDetails, jwtExpiration);
    // }

    //new
    // Generate access token
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, accessTokenExpiration);
    }

    // Generate refresh token
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshTokenExpiration);
    }

    // public long getExpirationTime() {
    //     return jwtExpiration;
    // }

    //new
    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    //new
    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    // private String buildToken(
    //         Map<String, Object> extraClaims,
    //         UserDetails userDetails,
    //         long expiration) {
    //     return Jwts
    //             .builder()
    //             .setClaims(extraClaims)
    //             .setSubject(userDetails.getUsername())
    //             .setIssuedAt(new Date(System.currentTimeMillis()))
    //             .setExpiration(new Date(System.currentTimeMillis() + expiration))
    //             .signWith(getSignInKey(), SignatureAlgorithm.HS256)
    //             .compact();
    // }

    //new 
    // General token builder
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // Use email/username
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate access token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //new
    // Validate refresh token
    public boolean validateRefreshToken(String refreshToken) {
        try {
            return !isTokenExpired(refreshToken);
        } catch (Exception e) {
            return false;
        }
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
