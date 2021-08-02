package com.example.SimbirSoft_2021.exception;

public class ProjectNotFoundException extends  Exception{
    private static final String message = "Ошибка (Error): Проект не найден (Project not found)";
    public ProjectNotFoundException(String message) {
        super(message);
    }
    public ProjectNotFoundException() {
        super(message);
    }
}
