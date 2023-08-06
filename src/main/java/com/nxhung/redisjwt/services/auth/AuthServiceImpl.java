package com.nxhung.redisjwt.services.auth;

import com.nxhung.redisjwt.constants.JwtConstant;
import com.nxhung.redisjwt.constants.enums.JwtTypeEnum;
import com.nxhung.redisjwt.services.auth.dtos.LoginRequest;
import com.nxhung.redisjwt.services.auth.dtos.LogoutRequest;
import com.nxhung.redisjwt.services.auth.dtos.RegisterRequest;
import com.nxhung.redisjwt.services.auth.dtos.SessionResponse;
import com.nxhung.redisjwt.services.jwt.JwtService;
import com.nxhung.redisjwt.services.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public SessionResponse login(LoginRequest request) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userDetailsService.loadUserByUsername(request.email());
        String refreshToken = jwtService.generateToken(generateClams(auth, JwtTypeEnum.REFRESH_TOKEN), user, Duration.ofMillis(JwtConstant.REFRESH_TOKEN));
        String accessToken = jwtService.generateToken(generateClams(auth, JwtTypeEnum.ACCESS_TOKEN), user, Duration.ofMillis(JwtConstant.ACCESS_TOKEN));
        redisTemplate.opsForValue().set(refreshToken, user.getUsername(), JwtConstant.REFRESH_TOKEN, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(accessToken, user.getUsername(), JwtConstant.ACCESS_TOKEN, TimeUnit.MILLISECONDS);
        return SessionResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    @Override
    public SessionResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public void logout(LogoutRequest request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(request.refreshToken()))) {
            redisTemplate.delete(request.refreshToken());
            redisTemplate.delete(auth.getCredentials().toString());
        }
    }

    private Map<String, Object> generateClams(Authentication authentication, JwtTypeEnum tokenType) {
        Map<String, Object> map = new HashMap<>();
        map.put(JwtConstant.AUTHORIZES, String.valueOf(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toString()));
        map.put(JwtConstant.TOKEN_TYPE, tokenType.name());
        return map;
    }
}
