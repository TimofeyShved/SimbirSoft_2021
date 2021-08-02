package com.example.SimbirSoft_2021.exception;

public class ProjectAndDateTimeExistsException extends Exception{
    private static final String message = "Ошибка (Error): Данная дата/время реализации уже существует среди проектов (Data and time release exists in project)";
    public ProjectAndDateTimeExistsException(String message) {
        super(message);
    }
    public ProjectAndDateTimeExistsException() {
        super(message);
    }
}
