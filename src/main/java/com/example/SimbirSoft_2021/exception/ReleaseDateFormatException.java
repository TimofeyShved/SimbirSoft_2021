package com.example.SimbirSoft_2021.exception;

public class ReleaseDateFormatException extends Exception{
    private static final String message = "Ошибка (Error): Неправильно указан формат даты/времени в реализации, нужно: yyyy-MM-dd HH:mm:ss (Release error in date format: yyyy-MM-dd HH:mm:ss)";
    public ReleaseDateFormatException(String message) {
        super(message);
    }
    public ReleaseDateFormatException() {
        super(message);
    }
}
