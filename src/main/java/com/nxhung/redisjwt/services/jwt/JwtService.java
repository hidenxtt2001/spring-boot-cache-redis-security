package com.nxhung.redisjwt.services.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);

    boolean isValidatedToken(String token, UserDetails userDetails);

    String generateToken(Map<String,Object> claims, UserDetails userDetails);
    String generateToken(Map<String,Object> claims, UserDetails userDetails, Duration duration);
}
