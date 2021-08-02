package com.example.SimbirSoft_2021.exception;

public class TaskAndDateTimeExistsException extends Exception{
    private static final String message = "Ошибка (Error): Данная дата/время реализации уже существует среди задач (Data and time release exists in task)";
    public TaskAndDateTimeExistsException(String message) {
        super(message);
    }
    public TaskAndDateTimeExistsException() {
        super(message);
    }
}
