package com.nxhung.redisjwt.controllers.auth;

import com.nxhung.redisjwt.constants.JwtConstant;
import com.nxhung.redisjwt.constants.enums.JwtTypeEnum;
import com.nxhung.redisjwt.services.auth.AuthService;
import com.nxhung.redisjwt.services.auth.dtos.LoginRequest;
import com.nxhung.redisjwt.services.auth.dtos.LogoutRequest;
import com.nxhung.redisjwt.services.auth.dtos.SessionResponse;
import com.nxhung.redisjwt.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login Call");
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestBody LogoutRequest logoutRequest) {
        logger.info("Logout Call");
        authService.logout(logoutRequest);
    }


}
