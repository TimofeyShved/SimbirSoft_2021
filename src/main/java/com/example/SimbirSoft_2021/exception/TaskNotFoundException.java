package com.example.SimbirSoft_2021.exception;

public class TaskNotFoundException extends  Exception{
    private static final String message = "Ошибка (Error): Задача не найдена (Task not found)";
    public TaskNotFoundException(String message) {
        super(message);
    }
    public TaskNotFoundException() {
        super(message);
    }
}
