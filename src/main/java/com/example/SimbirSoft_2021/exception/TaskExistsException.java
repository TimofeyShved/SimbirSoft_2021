package com.example.SimbirSoft_2021.exception;

public class TaskExistsException extends  Exception{
    private static final String message = "Ошибка (Error): Задача уже существует (Task exists)";
    public TaskExistsException(String message) {
        super(message);
    }
    public TaskExistsException() {
        super(message);
    }
}
