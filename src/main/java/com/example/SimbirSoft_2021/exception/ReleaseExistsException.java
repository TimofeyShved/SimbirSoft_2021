package com.example.SimbirSoft_2021.exception;

public class ReleaseExistsException  extends  Exception{
    private static String message = "code: RELEASE_EXISTS";
    public ReleaseExistsException(String message) {
        super(message);
    }
    public ReleaseExistsException() {
        super(message);
    }
}
