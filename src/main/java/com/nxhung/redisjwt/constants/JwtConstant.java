package com.nxhung.redisjwt.constants;

import java.time.Duration;

public class JwtConstant {
    public static final String TOKEN_TYPE = "tokenType";
    public static final String AUTHORIZES = "Authorizes";
    public static final long ACCESS_TOKEN = 60L*24 * 1000;
    public static final long REFRESH_TOKEN = 60L *60*24*30 * 1000;

}


