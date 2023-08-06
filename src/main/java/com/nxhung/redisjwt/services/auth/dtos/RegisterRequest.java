package com.nxhung.redisjwt.services.auth.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
}
