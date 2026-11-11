package edu.ubb.rtgsimulator.backend.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class AuthenticationService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    private long expirationTIme = 86400000;

    public String generateToken(String identifier) {
        return Jwts
                .builder()
                .subject(identifier)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration((new Date(System.currentTimeMillis() + expirationTIme)))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, String userID) {
        final String extractedUserID = extractUserID(token);
        return (extractedUserID.equals(userID) && !isTokenExpired(token));
    }

    public String extractUserID(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getSignInKey() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("JWT Secret Key is not configured correctly in application.properties!");
        }
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}