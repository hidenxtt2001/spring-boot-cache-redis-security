package com.nxhung.redisjwt.services.auth;

import com.nxhung.redisjwt.services.auth.dtos.LoginRequest;
import com.nxhung.redisjwt.services.auth.dtos.LogoutRequest;
import com.nxhung.redisjwt.services.auth.dtos.RegisterRequest;
import com.nxhung.redisjwt.services.auth.dtos.SessionResponse;

public interface AuthService {
    SessionResponse login(LoginRequest request);

    SessionResponse register(RegisterRequest request);

    void logout(LogoutRequest request);
}
