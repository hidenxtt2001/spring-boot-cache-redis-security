package com.nxhung.redisjwt.services.auth.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionResponse {
    private String accessToken;
    private String refreshToken;
}
