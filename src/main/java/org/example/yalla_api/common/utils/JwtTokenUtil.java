package org.example.yalla_api.common.utils;

import io.jsonwebtoken.security.Keys;
import org.example.yalla_api.common.dtos.auth.AuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    // Injecting secret keys from application properties
    @Value("${jwt.access-token.secret}")
    private String accessTokenSecret;

    @Value("${jwt.refresh-token.secret}")
    private String refreshTokenSecret;

    // Token validity in milliseconds
    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpirationTime;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpirationTime;

    // Generate an Access Token
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Include role in claims
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        return createToken(claims, userDetails.getUsername(), accessTokenExpirationTime, accessTokenSecret);
    }

    // Generate a Refresh Token
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Optionally, include information in claims
        return createToken(claims, userDetails.getUsername(), refreshTokenExpirationTime, refreshTokenSecret);
    }

    // Create a token (used by both access and refresh tokens)
    private String createToken(Map<String, Object> claims, String subject, Long expirationTime, String secret) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        // Decode the Base64-encoded secret key and create a Key object
        byte[] secretKeyBytes = Base64.getDecoder().decode(secret);
        Key signingKey = Keys.hmacShaKeyFor(secretKeyBytes);

        return Jwts.builder()
                .setClaims(claims)         // Set custom claims
                .setSubject(subject)       // Set the subject (username)
                .setIssuedAt(now)          // Set the issued date
                .setExpiration(expiryDate) // Set the expiration date
                .signWith(signingKey, SignatureAlgorithm.HS512) // Sign the token with the Key object
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token, boolean isAccessToken) {
        return extractClaim(token, Claims::getSubject, isAccessToken);
    }



    // Extract a specific claim from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, boolean isAccessToken) {
        final Claims claims = parseToken(token, isAccessToken);
        return claimsResolver.apply(claims);
    }

    // Parse the token and retrieve claims
    private Claims parseToken(String token, boolean isAccessToken) {
        try {
            // Decode the Base64-encoded secret key
            String secret = isAccessToken ? accessTokenSecret : refreshTokenSecret;
            byte[] secretKeyBytes = Base64.getDecoder().decode(secret);
            Key signingKey = Keys.hmacShaKeyFor(secretKeyBytes);

            // Use the JwtParserBuilder and set the signing key
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)    // Set the signing key
                    .build()
                    .parseClaimsJws(token)        // Parse the token
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            // Handle different exceptions (e.g., expired token, invalid token)
            throw new RuntimeException("Invalid JWT token");
        }
    }

    // Validate the token
    public boolean validateToken(String token, UserDetails userDetails, boolean isAccessToken) {
        final String username = extractUsername(token, isAccessToken);
        return (
                username.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token, isAccessToken)
        );
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token, boolean isAccessToken) {
        final Date expiration = extractClaim(token, Claims::getExpiration, isAccessToken);
        return expiration.before(new Date());
    }


    public AuthResponse createAuthResponse (UserDetails userDetails){
        return  new AuthResponse(generateAccessToken(userDetails),generateRefreshToken(userDetails));
    }

}
