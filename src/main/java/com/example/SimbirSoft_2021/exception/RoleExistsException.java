package com.example.SimbirSoft_2021.exception;

public class RoleExistsException extends  Exception{
    private static String message = "code: ROLE_EXISTS";
    public RoleExistsException(String message) {
        super(message);
    }
    public RoleExistsException() {
        super(message);
    }
}
