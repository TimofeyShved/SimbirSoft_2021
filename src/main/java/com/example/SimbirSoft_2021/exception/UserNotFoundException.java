package com.example.SimbirSoft_2021.exception;

public class UserNotFoundException extends  Exception{
    private static String message = "code: USER_NOT_FOUND";
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException() {
        super(message);
    }
}
