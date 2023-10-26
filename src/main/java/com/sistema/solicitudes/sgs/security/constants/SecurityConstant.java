package com.sistema.solicitudes.sgs.security.constants;

public class SecurityConstant {

    public static final long EXPIRATION_TIME = 86_400_000; // expiration time life of the token. Default its 5 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER  = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VIRIFIED = "The token can not be verified";
    public static final String COMPANY_NAME = "SGS_BE";
    public static final String AUTHORITIES = "Authorities";
    public static final String [ ] PUBLIC_URLS = { "**" };


}
