package com.example.SimbirSoft_2021.exception;

public class ProjectNotFoundException extends  Exception{
    private static String message = "code: PROJECT_NOT_FOUND";
    public ProjectNotFoundException(String message) {
        super(message);
    }
    public ProjectNotFoundException() {
        super(message);
    }
}
