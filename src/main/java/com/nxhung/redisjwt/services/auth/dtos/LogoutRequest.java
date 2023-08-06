package com.nxhung.redisjwt.services.auth.dtos;

public record LogoutRequest(
        String refreshToken
) {
}
