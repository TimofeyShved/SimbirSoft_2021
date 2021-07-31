package com.example.SimbirSoft_2021.exception;

public class ProjectExistsException extends  Exception{
    private static String message = "code: PROJECT_EXISTS";
    public ProjectExistsException(String message) {
        super(message);
    }
    public ProjectExistsException() {
        super(message);
    }
}
