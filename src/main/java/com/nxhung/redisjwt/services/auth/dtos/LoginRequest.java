package com.nxhung.redisjwt.services.auth.dtos;

public record LoginRequest(
        String email,
        String password
) {
}
