package com.example.SimbirSoft_2021.exception;

public class ReleaseExistsException  extends  Exception{
    private static final String message = "Ошибка (Error): Такая реализация времени уже существует (Release exists)";
    public ReleaseExistsException(String message) {
        super(message);
    }
    public ReleaseExistsException() {
        super(message);
    }
}
