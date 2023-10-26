package com.sistema.solicitudes.sgs.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sistema.solicitudes.sgs.security.constants.SecurityConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.sistema.solicitudes.sgs.security.constants.SecurityConstant.*;


@Component
public class JwtUtils {


    @Value("${jwt.secret.key}")
    private String secretKey;


    public String create(User user) throws JWTCreationException {
        return JWT.create()
                .withIssuer(COMPANY_NAME)
                .withClaim("password", user.getPassword())
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public boolean isValidToken(String token) throws JWTVerificationException {
        return new Date().before(getVerifier().verify(token).getExpiresAt());
    }

    public String getSubject(String token) {
        return getVerifier().verify(token).getSubject();
    }

    public List<SimpleGrantedAuthority> getAuthoritiesFromClaims(String token) {
        return getVerifier().verify(token).getClaim(AUTHORITIES).asList(SimpleGrantedAuthority.class);
    }

    private JWTVerifier getVerifier() {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer(COMPANY_NAME)
                .build();
    }




}
