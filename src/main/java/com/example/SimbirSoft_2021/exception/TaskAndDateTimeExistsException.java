package com.example.SimbirSoft_2021.exception;

public class TaskAndDateTimeExistsException extends Exception{
    private static String message = "code: TASK_TIME_RELEASE_IN_PROJECTS_EXISTS";
    public TaskAndDateTimeExistsException(String message) {
        super(message);
    }
    public TaskAndDateTimeExistsException() {
        super(message);
    }
}
