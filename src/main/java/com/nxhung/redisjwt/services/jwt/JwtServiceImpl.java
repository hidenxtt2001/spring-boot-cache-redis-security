package com.nxhung.redisjwt.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtServiceImpl implements JwtService {

    @Value("${JWT_SIGNING_KEY}")
    private String signingKey;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValidatedToken(String token, UserDetails userDetails) {
        return !isExpiredToken(token) && extractUsername(token).equals(userDetails.getUsername());
    }

    @Override
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return generateToken(claims, userDetails, Duration.ofMillis(60 * 24 * 1000));
    }

    @Override
    public String generateToken(Map<String, Object> claims, UserDetails userDetails, Duration duration) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + duration.toMillis()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())).signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isExpiredToken(String token) {
        var dateExpired = extractClaim(token, Claims::getExpiration);
        return dateExpired.before(new Date(System.currentTimeMillis()));

    }

    private <T> T extractClaim(String token, Function<Claims, T> extractFunction) {
        Claims claims = extractClaims(token);
        return extractFunction.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private SecretKey getSigningKey() {
        byte[] byteKey = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(byteKey);
    }

}
