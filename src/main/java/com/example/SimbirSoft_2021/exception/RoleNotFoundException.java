package com.example.SimbirSoft_2021.exception;

public class RoleNotFoundException extends  Exception{
    private static String message = "code: ROLE_NOT_FOUND";
    public RoleNotFoundException(String message) {
        super(message);
    }
    public RoleNotFoundException() {
        super(message);
    }
}
