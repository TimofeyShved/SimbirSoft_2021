package com.example.SimbirSoft_2021.exception;

public class ProjectExistsException extends  Exception{
    private static final String message = "Ошибка (Error): Такой проект уже существует (Project exists)";
    public ProjectExistsException(String message) {
        super(message);
    }
    public ProjectExistsException() {
        super(message);
    }
}
