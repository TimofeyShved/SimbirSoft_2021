package com.example.SimbirSoft_2021.exception;

public class UserExistsException extends  Exception{
    private static String message = "code: USER_EXISTS";
    public UserExistsException(String message) {
        super(message);
    }
    public UserExistsException() {
        super(message);
    }
}
