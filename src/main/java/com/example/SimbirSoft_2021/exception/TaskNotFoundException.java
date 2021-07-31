package com.example.SimbirSoft_2021.exception;

public class TaskNotFoundException extends  Exception{
    private static String message = "code: TASK_NOT_FOUND";
    public TaskNotFoundException(String message) {
        super(message);
    }
    public TaskNotFoundException() {
        super(message);
    }
}
