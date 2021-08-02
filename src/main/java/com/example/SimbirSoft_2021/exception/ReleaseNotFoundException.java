package com.example.SimbirSoft_2021.exception;

public class ReleaseNotFoundException extends  Exception{
    private static final String message = "Ошибка (Error): Реализация времени не найдена (Release not found)";
    public ReleaseNotFoundException(String message) {
        super(message);
    }
    public ReleaseNotFoundException() {
        super(message);
    }
}
