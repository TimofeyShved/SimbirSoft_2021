package com.example.SimbirSoft_2021.exception;

public class ReleaseNotFoundException extends  Exception{
    private static String message = "code: RELEASE_NOT_FOUND";
    public ReleaseNotFoundException(String message) {
        super(message);
    }
    public ReleaseNotFoundException() {
        super(message);
    }
}
