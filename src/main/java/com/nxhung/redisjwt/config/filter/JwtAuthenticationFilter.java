package com.nxhung.redisjwt.config.filter;

import com.nxhung.redisjwt.services.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final RedisTemplate<String, ?> redisTemplate;
    private final JwtService jwtService;
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null) {
            if (!StringUtils.startsWith(header, "Bearer ")) {
                throw new BadCredentialsException("Token is not valid");
            }
            String jwtToken = header.substring(7).trim();
            var username = jwtService.extractUsername(jwtToken);
            var user = userDetailsService.loadUserByUsername(username);
            if (user == null) {
                throw new BadCredentialsException("Token is not valid");
            }
            var token = new UsernamePasswordAuthenticationToken(user.getUsername(), jwtToken, user.getAuthorities());
            SecurityContext contextHolder = SecurityContextHolder.createEmptyContext();
            contextHolder.setAuthentication(token);
            SecurityContextHolder.setContext(contextHolder);
        }
        filterChain.doFilter(request, response);
    }
}
