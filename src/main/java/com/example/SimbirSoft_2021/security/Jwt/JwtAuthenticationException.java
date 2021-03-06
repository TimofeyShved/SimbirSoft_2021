package com.example.SimbirSoft_2021.security.Jwt;


import org.springframework.security.core.AuthenticationException;

// Ошибки при попытки зайти
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
