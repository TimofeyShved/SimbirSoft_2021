package com.example.SimbirSoft_2021.exception;

public class ReleaseDateFormatException extends Exception{
    private static String message = "code: RELEASE_ERROR_DATE_FORMAT(yyyy-MM-dd HH:mm:ss)";
    public ReleaseDateFormatException(String message) {
        super(message);
    }
    public ReleaseDateFormatException() {
        super(message);
    }
}
