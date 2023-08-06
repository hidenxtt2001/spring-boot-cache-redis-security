package com.nxhung.redisjwt.controllers;

import com.nxhung.redisjwt.constants.JwtConstant;
import com.nxhung.redisjwt.constants.enums.JwtTypeEnum;
import com.nxhung.redisjwt.services.auth.AuthService;
import com.nxhung.redisjwt.services.auth.dtos.LoginRequest;
import com.nxhung.redisjwt.services.auth.dtos.LogoutRequest;
import com.nxhung.redisjwt.services.auth.dtos.SessionResponse;
import com.nxhung.redisjwt.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/login")
    public SessionResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
    }


}
