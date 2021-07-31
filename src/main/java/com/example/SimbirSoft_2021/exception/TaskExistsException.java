package com.example.SimbirSoft_2021.exception;

public class TaskExistsException extends  Exception{
    private static String message = "code: TASK_EXISTS";
    public TaskExistsException(String message) {
        super(message);
    }
    public TaskExistsException() {
        super(message);
    }
}
