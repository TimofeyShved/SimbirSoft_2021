package com.example.SimbirSoft_2021.exception;

public class ProjectAndDateTimeExistsException extends Exception{
    private static String message = "code: DATE_TIME_RELEASE_IN_PROJECTS_EXISTS";
    public ProjectAndDateTimeExistsException(String message) {
        super(message);
    }
    public ProjectAndDateTimeExistsException() {
        super(message);
    }
}
